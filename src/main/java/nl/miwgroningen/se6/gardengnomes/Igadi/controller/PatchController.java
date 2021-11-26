package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.TaskService;
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

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class PatchController {

    private final PatchService patchService;
    private final GardenService gardenService;
    private final TaskService taskService;
    private final AuthorizationHelper authorizationHelper;

    public PatchController(PatchService patchService, GardenService gardenService,
                           AuthorizationHelper authorizationHelper, TaskService taskService) {
        this.patchService = patchService;
        this.gardenService = gardenService;
        this.authorizationHelper = authorizationHelper;
        this.taskService = taskService;
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(@AuthenticationPrincipal User user) {
        if (user != null) {
            return authorizationHelper.isAdmin(user.getUserId());
        } else {
            return false;
        }
    }

    @GetMapping("/overview/details/garden/patches/new/{gardenId}")
    protected String newPatchForm(@PathVariable(value = "gardenId") Integer gardenId, Model model,
                                  @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)) {
            PatchDTO patch = new PatchDTO();
            patch.setGardenDTO(gardenService.getGardenById(gardenId));
            model.addAttribute("patch", patch);
            model.addAttribute("titleText", "Add a new patch");
            return "patchForm";
        } else {
            return "error/403";
        }
    }

    @GetMapping("/overview/details/garden/patches/edit/{patchId}")
    protected String editPatchForm(@PathVariable(value = "patchId") Integer patchId, Model model,
                                   @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        try {
            if (authorizationHelper.isUserGardenManager(user.getUserId(), patchService.findGardenIdByPatchId(patchId))) {
                PatchDTO patch = patchService.getPatchById(patchId);
                model.addAttribute("patch", patch);
                model.addAttribute("titleText", "Edit patch");
                return "patchForm";
            } else {
                return "error/403";
            }
        } catch (NullPointerException ex) {
            return "error/404";
        }
    }

    @PostMapping ("/overview/details/garden/patches/new/{gardenId}")
    protected String saveNewPatch(@PathVariable("gardenId") int gardenId, @ModelAttribute("patch") PatchDTO patch,
                                  BindingResult result, @AuthenticationPrincipal User user,
                                  RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            try {
                // if we edit and change the crop, we want to delete all tasks. In all other cases, keep things the same
                /*if (patch.getPatchId() != null && )*/
                patch.setGardenDTO(gardenService.getGardenById(gardenId));
                patch.setPatchId(patchService.userSavePatch(patch, user.getUserId(), gardenId));
                return "redirect:/overview/details/patchTasks/" + patch.getPatchId();
            }
            catch (SecurityException ex) {
                return "error/403";
            }
        } else {
            return "redirect:/overview/details/patchTasks/" + patch.getPatchId();
        }
    }

    @PostMapping("/overview/details/patch/delete/{patchId}")
    public String deletePatchById(@PathVariable("patchId") int patchId, @AuthenticationPrincipal User user,
                                      RedirectAttributes redirectAttributes) {
        try {
            int gardenId = patchService.getPatchById(patchId).getGardenDTO().getGardenId();
            patchService.userDeletePatch(user.getUserId(), patchService.getPatchById(patchId));
            taskService.deleteUnreferencedEntries();
            return "redirect:/overview/details/" + gardenId;
        } catch (SecurityException ex) {
            return "error/403";
        }
    }
}