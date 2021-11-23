package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.JoinGardenRequestDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.JoinGardenRequestService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class RequestController {

    private final GardenService gardenService;
    private final GardenUserService gardenUserService;
    private final UserService userService;
    private final JoinGardenRequestService joinGardenRequestService;
    private final AuthorizationHelper authorizationHelper;

    public RequestController(GardenService gardenService, GardenUserService gardenUserService, UserService userService,
                             JoinGardenRequestService joinGardenRequestService,
                             AuthorizationHelper authorizationHelper) {
        this.gardenService = gardenService;
        this.gardenUserService = gardenUserService;
        this.userService = userService;
        this.joinGardenRequestService = joinGardenRequestService;
        this.authorizationHelper = authorizationHelper;
    }

    @GetMapping("/gardens/requests")
    protected String showGardensForRequests(Model model, @AuthenticationPrincipal User user,
                                            @ModelAttribute("message") ArrayList<String> message) {
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        List<GardenUser> currentSubscriptions = gardenUserService.findAllGardenUsersByUserId(user.getUserId());
        List<JoinGardenRequestDTO> allActiveGardenRequests = joinGardenRequestService
                .findAllRequestsByUserId(user.getUserId());
        for(GardenUser subscription : currentSubscriptions) {
            allGardens.removeIf(gardenDTO -> subscription.getGarden().getGardenId() == gardenDTO.getGardenId());
        }
        for(JoinGardenRequestDTO request : allActiveGardenRequests) {
            allGardens.removeIf(gardenDTO -> request.getGardenDTO().getGardenId() == gardenDTO.getGardenId());
        }
        model.addAttribute("allGardens", allGardens);
        if(!message.isEmpty()) {
            model.addAttribute("message", message);
        }
        model.addAttribute("allRequests", allActiveGardenRequests);
        return "requestForm";
    }

    @PostMapping("/gardens/requests/{gardenId}")
    protected String postGardenRequest(@PathVariable("gardenId") int gardenId, @AuthenticationPrincipal User user,
                                       RedirectAttributes redirectAttributes,
                                       @ModelAttribute("joinGardenRequestDTO") JoinGardenRequestDTO joinGardenRequestDTO,
                                       BindingResult result) {
        String message = "Something went wrong.";
        if (!result.hasErrors()) {
            GardenDTO gardenDTO = gardenService.getGardenById(gardenId);
            message = "A new request for " + gardenDTO.getGardenName()  + " has been sent!";
            UserDTO userDTO = userService.getUserById(user.getUserId());
            joinGardenRequestDTO = new JoinGardenRequestDTO();
            joinGardenRequestDTO.setGardenDTO(gardenDTO);
            joinGardenRequestDTO.setUserDTO(userDTO);
            joinGardenRequestDTO.setStatus("Pending");
            joinGardenRequestService.saveRequest(joinGardenRequestDTO);
            redirectAttributes.addAttribute("message", List.of(message, "greenMessage"));
            return "redirect:/gardens/requests";
        }
        redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        return "redirect:/gardens/requests";
    }

    @PostMapping("/overview/details/{gardenId}/gardeners/accept/{requestId}")
    protected String postNewGardener(@PathVariable("gardenId") int gardenId,
                                            @PathVariable("requestId") int requestId,
                                     @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        String message = "Something went wrong";
        if(authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)){
            try {
                GardenDTO garden = gardenService.getGardenById(gardenId);
                JoinGardenRequestDTO joinGardenRequestDTO = joinGardenRequestService.getRequestById(requestId);
                UserDTO userDTO = userService.getUserById(joinGardenRequestDTO.getUserDTO().getUserId());
                userDTO.setGarden(garden);
                GardenUserDTO gardenUserDTO = new GardenUserDTO();
                gardenUserDTO.setGardenDTO(garden);
                gardenUserDTO.setUserDTO(userDTO);
                gardenUserDTO.setRole("gardener");
                gardenUserService.saveGardenUser(gardenUserDTO);
                message = userDTO.getUserName() + " has been added to the garden!";
                redirectAttributes.addAttribute("message", List.of(message, "greenMessage"));
                joinGardenRequestService.deleteRequest(joinGardenRequestDTO);
            } catch (SecurityException ex) {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        }
        return "redirect:/overview/details/{gardenId}/gardeners";
    }

    @PostMapping("/overview/details/{gardenId}/gardeners/decline/{requestId}")
    protected String deleteRequest(@PathVariable("gardenId") int gardenId,
                                     @PathVariable("requestId") int requestId, @AuthenticationPrincipal User user,
                                     RedirectAttributes redirectAttributes) {
        String message = "Something went wrong";
        if(authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)){
            try {
                JoinGardenRequestDTO joinGardenRequestDTO = joinGardenRequestService.getRequestById(requestId);
                message = joinGardenRequestDTO.getUserDTO().getUserName() + "'s request has been removed!";
                redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
                joinGardenRequestService.deleteRequest(joinGardenRequestDTO);
            } catch (SecurityException ex) {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        }
        return "redirect:/overview/details/{gardenId}/gardeners";
    }
}