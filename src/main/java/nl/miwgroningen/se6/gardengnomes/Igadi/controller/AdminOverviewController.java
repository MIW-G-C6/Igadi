package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.TaskRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/overview/details/{gardenId}")
    protected String showGardenDetails(@PathVariable("gardenId") int gardenId, Model model) {
        GardenDTO garden = gardenService.getGardenById(gardenId);
        List<PatchDTO> allPatches = patchService.getAllPatchesByGardenId(gardenId);
        model.addAttribute("garden", garden);
        model.addAttribute("allPatches", allPatches);
        return "gardenDetails";
    }

}
