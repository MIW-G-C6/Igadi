package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.JoinGardenRequestDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
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

    public RequestController(GardenService gardenService, GardenUserService gardenUserService, UserService userService,
                             JoinGardenRequestService joinGardenRequestService) {
        this.gardenService = gardenService;
        this.gardenUserService = gardenUserService;
        this.userService = userService;
        this.joinGardenRequestService = joinGardenRequestService;
    }


    @GetMapping("/gardens/requests")
    protected String showGardensForRequests(Model model, @AuthenticationPrincipal User user) {
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        List<GardenUser> currentSubscriptions = gardenUserService.findAllGardenUsersByUserId(user.getUserId());
        for(GardenUser subscription : currentSubscriptions) {
            allGardens.removeIf(gardenDTO -> subscription.getGarden().getGardenId() == gardenDTO.getGardenId());
        }
        model.addAttribute("allGardens", allGardens);
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
            UserDTO userDTO = userService.getUserById(user.getUserId());
            joinGardenRequestDTO = new JoinGardenRequestDTO();
            joinGardenRequestDTO.setGardenDTO(gardenDTO);
            joinGardenRequestDTO.setUserDTO(userDTO);
            joinGardenRequestDTO.setStatus("Pending");
            joinGardenRequestService.saveRequest(joinGardenRequestDTO);
            return "redirect:/gardens/requests";
        }
        redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        return "redirect:/gardens/requests";
    }
}
