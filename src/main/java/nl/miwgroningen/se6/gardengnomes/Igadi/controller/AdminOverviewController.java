package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.TaskRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     In this controller, we created functions for interaction between the database and parts of our application where
 *     gardens are involved. This includes the gardenOverview page.
 */

@Controller
public class AdminOverviewController {

    private GardenService gardenService;
    private PatchService patchService;
    private TaskService taskService;

    public AdminOverviewController(GardenService gardenService, PatchService patchService, TaskService taskService) {
        this.gardenService = gardenService;
        this.patchService = patchService;
        this.taskService = taskService;
    }

    @GetMapping({"/", "/overview"})
    protected String showGardenOverview(Model model) {
        model.addAttribute("allGardens", gardenService.getAllGardens());
        model.addAttribute("allPatches", patchService.getAllPatches());
        return "adminOverview";
    }
}
