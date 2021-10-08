package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.TaskRepository;
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

    private GardenRepository gardenRepository;
    private PatchRepository patchRepository;
    private TaskRepository taskRepository;

    public AdminOverviewController(GardenRepository gardenRepository, PatchRepository patchRepository,
                                   TaskRepository taskRepository) {
        this.gardenRepository = gardenRepository;
        this.patchRepository = patchRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping({"/", "/overview"})
    protected String showGardenOverview(Model model) {
        model.addAttribute("allGardens", gardenRepository.findAll());
        model.addAttribute("allPatches", patchRepository.findAll());
        model.addAttribute("allTasks", taskRepository.findAll());
        return "adminOverview";
    }
}
