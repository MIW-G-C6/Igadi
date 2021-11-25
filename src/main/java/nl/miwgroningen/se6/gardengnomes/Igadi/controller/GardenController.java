package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
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
    private PatchTaskService patchTaskService;

    public GardenController(GardenService gardenService, UserService userService, PatchService patchService,
                            GardenHelper gardenHelper, GardenUserService gardenUserService,
                            JoinGardenRequestService joinGardenRequestService,
                            AuthorizationHelper authorizationHelper, TaskService taskService,
                            PatchTaskService patchTaskService) {
        this.gardenService = gardenService;
        this.userService = userService;
        this.patchService = patchService;
        this.gardenHelper = gardenHelper;
        this.gardenUserService = gardenUserService;
        this.authorizationHelper = authorizationHelper;
        this.joinGardenRequestService = joinGardenRequestService;
        this.taskService = taskService;
        this.patchTaskService = patchTaskService;
    }

    @GetMapping("/gardens")
    protected String showGardens(Model model, @AuthenticationPrincipal User user) {
        List<GardenDTO> allGardens = gardenService.findAllGardensByUserId(user.getUserId());
        if(authorizationHelper.isAdmin(user.getUserId())) {
            allGardens = gardenService.getAllGardens();
        }
        for(GardenDTO gardenDTO : allGardens) {
            if(authorizationHelper.isUserGardenManager(user.getUserId(), gardenDTO.getGardenId())) {
                gardenDTO.setGardenManagerStatus(true);
            }
        }
        model.addAttribute("allGardens", allGardens);
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
    protected String createGarden(@ModelAttribute("garden") GardenDTO gardenDTO, BindingResult result,
                                          RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        String message = "Something went wrong.";
        if (!result.hasErrors()) {
            try {
                if (gardenDTO.getGardenId() == null) {
                    int newGardenId = gardenService.saveGarden(gardenDTO);
                    GardenUserDTO gardenUser = new GardenUserDTO();
                    gardenDTO.setGardenId(newGardenId);
                    gardenUser.setGardenDTO(gardenDTO);
                    gardenUser.setUser(user);
                    gardenUser.setRole(UserRole.GARDEN_MANAGER);
                    gardenUserService.saveGardenUser(gardenUser);
                    return "redirect:/gardens";
                } else {
                    gardenService.userSaveGarden(user.getUserId(), gardenDTO);
                    return "redirect:/overview/details/" + gardenDTO.getGardenId();
                }
            } catch (Exception ex) {
                if (gardenHelper.IsGardenNameDuplicate(ex)) {
                    message = "That name already exists.";
                }
            }
        }
        redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        return "redirect:/gardens/new";
    }

    /*@PostMapping("gardens/edit")
    protected String editGarden(@ModelAttribute("garden") GardenDTO gardenDTO, BindingResult result,
                                RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        String message = "Something went wrong.";
        if (!result.hasErrors()) {
            try {
                gardenService.saveGarden(gardenDTO);
                return "redirect:/overview/details/" + gardenDTO.getGardenId();
            } catch (Exception ex) {
                if (gardenHelper.IsGardenNameDuplicate(ex)) {
                    message = "That name already exists.";
                }
            }
        }
        redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        return "redirect:/overview/details/" + gardenDTO.getGardenId() + "/edit";
    }*/

    @PostMapping("gardens/delete/{gardenId}")
    public String deleteGardenById(@PathVariable("gardenId") int gardenId, RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal User user) {
        try {
            gardenService.userDeleteGarden(user.getUserId(), gardenId);
            taskService.deleteUnreferencedEntries();
            return "redirect:/gardens";
        } catch (SecurityException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            redirectAttributes.addAttribute("message", ex.getMessage());
            return "redirect:/error";
        }
    }

    @GetMapping("/overview/details/{gardenId}")
    protected String showGardenDetails(@PathVariable("gardenId") int gardenId, Model model,
                                       @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenMember(user.getUserId(), gardenId)) {
            GardenDTO garden = gardenService.getGardenById(gardenId);
            List<PatchDTO> allPatches = patchService.findAllPatchesByGardenId(gardenId);
            for (PatchDTO patch : allPatches) {
                List<PatchTaskDTO> patchTasks = patchTaskService.getAllTasksByPatchId(patch.getPatchId());
                patch.setNumberOfOpenTasks((int)patchTaskService.getAllTasksByPatchId(patch.getPatchId())
                        .stream().filter(x -> !x.isDone()).count());
            }
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
                                            @AuthenticationPrincipal User user,
                                            @ModelAttribute("message") ArrayList<String> message,
                                            RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)) {
                if (!message.isEmpty()) {
                    model.addAttribute("message", message);
                }
                ArrayList<UserDTO> currentGardeners = new ArrayList<>();
                List<GardenUserDTO> alreadyAddedUser = gardenUserService.findAllGardenUsersByGardenId(gardenId);
                List<JoinGardenRequestDTO> pendingRequests = joinGardenRequestService.findAllRequestsByGardenId(gardenId);
                for (GardenUserDTO userSubscription : alreadyAddedUser) {
                    if (userSubscription.getGardenDTO().getGardenId() == gardenId) {
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
            } else {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
    }

    @PostMapping("/overview/details/{gardenId}/gardeners/{requestId}")
    protected String postPotentialGardeners(@PathVariable("gardenId") int gardenId,
                                            @PathVariable("requestId") int requestId,
                                            @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)) {
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

    @PostMapping("/overview/details/{gardenId}/leave")
    protected String leaveGarden(@ModelAttribute("garden") GardenDTO gardenDTO, BindingResult result,
                                 RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        String message = "Something went wrong.";
        if (!result.hasErrors()) {
            GardenUserDTO gardenUserDTO = gardenUserService.findOneGardenUserByUserIdAndGardenId(gardenDTO
                    .getGardenId(), user.getUserId());
            message = "You have succesfully left " +
                    gardenService.getGardenById(gardenDTO.getGardenId()).getGardenName() + "!";
            redirectAttributes.addAttribute("message", List.of(message, "greenMessage text-center"));
            gardenUserService.deleteGardenUser(gardenUserDTO);
            return "redirect:/index";
        }
        redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        return "redirect:/gardens/new";
    }

    @GetMapping("/overview/details/{gardenId}/edit")
    protected String editGarden(Model model, @ModelAttribute("message") ArrayList<String> message,
                                @PathVariable("gardenId") int gardenId, @ModelAttribute("garden") GardenDTO gardenDTO,
                                @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)) {
            gardenDTO = gardenService.getGardenById(gardenId);
            model.addAttribute("garden", gardenDTO);
            if (!message.isEmpty()) {
                model.addAttribute("message", message);
            }
            return "gardenForm";
        } else {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }
}