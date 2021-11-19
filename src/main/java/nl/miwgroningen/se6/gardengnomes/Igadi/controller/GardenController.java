package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.GardenHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

/**
 * @author Tjerk Nagel
 *
 */

@Controller
public class GardenController {

    private GardenService gardenService;
    private PatchService patchService;
    private UserService userService;
    private GardenHelper gardenHelper;
    private GardenUserService gardenUserService;
    private AuthorizationHelper authorizationHelper;
    private TaskService taskService;
    private JoinGardenRequestService joinGardenRequestService;

    public GardenController(GardenService gardenService, UserService userService, PatchService patchService,
                            GardenHelper gardenHelper, GardenUserService gardenUserService,
                            JoinGardenRequestService joinGardenRequestService,
                            AuthorizationHelper authorizationHelper, TaskService taskService) {
        this.gardenService = gardenService;
        this.userService = userService;
        this.patchService = patchService;
        this.gardenHelper = gardenHelper;
        this.gardenUserService = gardenUserService;
        this.authorizationHelper = authorizationHelper;
        this.joinGardenRequestService = joinGardenRequestService;
        this.taskService = taskService;
    }

    @GetMapping("/gardens")
    protected String showGardens(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("allGardens", gardenService.findAllGardensByUserId(user.getUserId()));
        return "gardens";
    }

    @GetMapping("/gardens/new")
    protected String showGardenForm(Model model, @ModelAttribute("message") ArrayList<String> message) {
        model.addAttribute("garden", new GardenDTO());
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "gardenForm";
    }

    @PostMapping("gardens/new")
    protected String createOrUpdateGarden(@ModelAttribute("garden") GardenDTO gardenDTO, BindingResult result,
                                          RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        String message = "Something went wrong.";
        if (!result.hasErrors()) {
            try {
                gardenService.saveGardenAndMakeUserGardenManager(gardenDTO, user);
                return "redirect:/gardens";
            } catch (Exception ex) {
                if (gardenHelper.IsGardenNameDuplicate(ex)) {
                    message = "That name already exists.";
                }
            }
        }
        redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        return "redirect:/gardens/new";
    }

    @PostMapping("gardens/delete/{gardenId}")
    public String deleteGardenById(@PathVariable("gardenId") int gardenId, RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal User user) {
        try {
            gardenService.userDeleteGarden(user.getUserId(), gardenId);
            taskService.deleteUnreferencedEntries();
            return "redirect:/gardens";
        } catch (SecurityException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }

    @GetMapping("/overview/details/{gardenId}")
    protected String showGardenDetails(@PathVariable("gardenId") int gardenId, Model model,
                                       @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenMember(user.getUserId(), gardenId)) {
            GardenDTO garden = gardenService.getGardenById(gardenId);
            List<PatchDTO> allPatches = patchService.findAllPatchesByGardenId(gardenId);
            model.addAttribute("garden", garden);
            model.addAttribute("allPatches", allPatches);
            model.addAttribute("isUserGardenManager",
                    authorizationHelper.isUserGardenManager(user.getUserId(), gardenId));
            return "gardenDetails";
        } else {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }

    @GetMapping("/overview/details/{gardenId}/gardeners")
    protected String showPotentialGardeners(@PathVariable("gardenId") int gardenId, Model model,
                                  @AuthenticationPrincipal User user) {
        ArrayList<UserDTO> currentGardeners = new ArrayList<>();
        List<GardenUserDTO> alreadyAddedUser = gardenUserService.findAllGardenUsersByGardenId(gardenId);
        List<JoinGardenRequestDTO> pendingRequests = joinGardenRequestService.findAllRequestsByGardenId(gardenId);
        for(GardenUserDTO userSubscription : alreadyAddedUser) {
            if(userSubscription.getGardenDTO().getGardenId() == gardenId) {
                UserDTO userToAdd = userService.getUserById(userSubscription.getUserDTO().getUserId());
                userToAdd.setUserRole(userSubscription.getRole());
                currentGardeners.add(userToAdd);
            }
        }
        GardenDTO garden = gardenService.getGardenById(gardenId);
        model.addAttribute("gardenId", gardenId);
        model.addAttribute("garden", garden);
        model.addAttribute("allRequests", pendingRequests);
        model.addAttribute("currentGardeners", currentGardeners);
        model.addAttribute("isUserGardenManager", authorizationHelper
                .isUserGardenManager(user.getUserId(), gardenId));
        return "gardeners";
    }

    @PostMapping("/overview/details/{gardenId}/gardeners/{requestId}")
    protected String postPotentialGardeners(@PathVariable("gardenId") int gardenId,
                                            @PathVariable("requestId") int requestId, @AuthenticationPrincipal User user,
                                            RedirectAttributes redirectAttributes) {
        if(authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)){
            try {
                GardenDTO garden = gardenService.getGardenById(gardenId);
                UserDTO userDTO = userService.getUserById(requestId);
                userDTO.setGarden(garden);
                GardenUserDTO gardenUserDTO = new GardenUserDTO();
                gardenUserDTO.setGardenDTO(garden);
                gardenUserDTO.setUserDTO(userDTO);
                gardenUserDTO.setRole("gardener");
                joinGardenRequestService.checkIfRequestsRemain(gardenId, userDTO.getUserId());
                gardenUserService.saveGardenUser(gardenUserDTO);
            } catch (SecurityException ex) {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        }
        return "redirect:/overview/details/{gardenId}/gardeners";
    }
}