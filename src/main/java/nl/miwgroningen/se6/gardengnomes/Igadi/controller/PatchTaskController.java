package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchTaskService;
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
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class PatchTaskController {

    private PatchService patchService;
    private PatchTaskService patchTaskService;
    private AuthorizationHelper authorizationHelper;

    public PatchTaskController(PatchService patchService, PatchTaskService patchTaskService,
                               AuthorizationHelper authorizationHelper) {
        this.patchService = patchService;
        this.patchTaskService = patchTaskService;
        this.authorizationHelper = authorizationHelper;
    }

    @GetMapping("/overview/details/patchTasks/{patchId}")
    protected String showPatchTasks(@PathVariable("patchId") int patchId, Model model, @AuthenticationPrincipal User user,
                                    RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("isUserGardenManager",
                    authorizationHelper.isUserGardenManager(user.getUserId(), patchService.findGardenIdByPatchId(patchId)));
            PatchDTO patch = patchService.convertToPatchDTO(patchService.getPatchById(patchId));
            List<PatchTaskDTO> allPatchTasks = patchTaskService.getAllTasksByPatchId(patchId);
            model.addAttribute("patch", patch);
            model.addAttribute("allPatchTasks", allPatchTasks);
            return "patchTasks";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
            return "redirect:/error";
        }
    }

    @GetMapping("/overview/details/patchTasks/new/{patchId}")
    protected String showPatchTaskForm(@PathVariable("patchId") int patchId, Model model, @AuthenticationPrincipal User user,
                                       RedirectAttributes redirectAttributes) {
        try {
            if (authorizationHelper.isUserGardenManager(user.getUserId(), patchService.findGardenIdByPatchId(patchId))) {
                PatchTask patchTask = new PatchTask();
                patchTask.setPatch(patchService.getPatchById(patchId));
                patchTask.setDone(false);
                model.addAttribute("patchTask", patchTask);
                return "patchTaskForm";
            } else {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        }  catch (EntityNotFoundException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
            return "redirect:/error";
        }
    }

    @PostMapping("/overview/details/patchTasks/new/{patchId}")
    protected String saveOrUpdatePatchTask(@PathVariable("patchId") int patchId, @ModelAttribute("patchTask") PatchTask patchTask,
                                           BindingResult result, @AuthenticationPrincipal User user,
                                           RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            try {
                patchTaskService.userSavePatchTask(patchTask, user.getUserId(), patchService.findGardenIdByPatchId(patchId));
                return "redirect:/overview/details/patchTasks/{patchId}";
            }
            catch (SecurityException ex) {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        } else {
            return "redirect:/overview/details/patchTasks/new/{patchId}";
        }
    }

    @PostMapping("/overview/details/patchTasks/delete/{taskId}")
    public String deletePatchTaskById(@PathVariable("taskId") int taskId, @AuthenticationPrincipal User user,
                                       RedirectAttributes redirectAttributes) {
        try {
            int patchId = patchTaskService.getPatchTaskById(taskId).getPatch().getPatchId();
            patchTaskService.deletePatchTask(user.getUserId(), patchTaskService.getPatchTaskById(taskId));
            return "redirect:/overview/details/patchTasks/" + patchId;
        } catch (SecurityException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }
}