package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.PatchTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenTaskService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class PatchTaskController {

    private PatchService patchService;
    private PatchTaskService patchTaskService;

    public PatchTaskController(PatchService patchService, PatchTaskService patchTaskService) {
        this.patchService = patchService;
        this.patchTaskService = patchTaskService;
    }

    @GetMapping("/overview/details/patchTasks/new/{patchId}")
    protected String showPatchTasks(@PathVariable("patchId") int patchId, Model model) {
        PatchTask patchTask = new PatchTask();
        patchTask.setPatch(patchService.getPatchById(patchId));
        patchTask.setDone(false);
        model.addAttribute("patchTask", patchTask);
        return "patchTaskForm";
    }

    @PostMapping("/overview/details/patchTasks/new/{patchId}")
    protected String saveOrUpdatePatchTask(@PathVariable("patchId") int patchId,
                                           @ModelAttribute("patchTask") PatchTask patchTask, BindingResult result) {
        if (!result.hasErrors()) {
            patchTaskService.savePatchTask(patchTask);
        }
        return "redirect:/overview/details/patchTasks/{patchId}";
    }
}
