package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenTaskService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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
    private GardenTaskService gardenTaskService;
    private PatchTaskService patchTaskService;

    public AdminOverviewController(GardenService gardenService, PatchService patchService,
                                   GardenTaskService gardenTaskService, PatchTaskService patchTaskService) {
        this.gardenService = gardenService;
        this.patchService = patchService;
        this.gardenTaskService = gardenTaskService;
        this.patchTaskService = patchTaskService;
    }

    @GetMapping({ "/overview"})
    protected String showGardenOverview(Model model) {
        model.addAttribute("allGardens", gardenService.getAllGardens());
        model.addAttribute("allPatches", patchService.getAllPatches());
        return "adminOverview";
    }

    @GetMapping("/overview/details/{gardenId}")
    protected String showGardenDetails(@PathVariable("gardenId") int gardenId, Model model) {
        GardenDTO garden = gardenService.convertToGardenDTO(gardenService.getGardenById(gardenId));
        List<PatchDTO> allPatches = patchService.getAllPatchesByGardenId(gardenId);
        model.addAttribute("garden", garden);
        model.addAttribute("allPatches", allPatches);
        return "gardenDetails";
    }

    @GetMapping("/overview/details/gardenTasks/{gardenId}")
    protected String showGardenTasks(@PathVariable("gardenId") int gardenId, Model model) {
        GardenDTO garden = gardenService.convertToGardenDTO(gardenService.getGardenById(gardenId));
        List<GardenTaskDTO> allGardenTasks = gardenTaskService.getAllTasksByGardenId(gardenId);
        model.addAttribute("garden", garden);
        model.addAttribute("allGardenTasks", allGardenTasks);
        return "gardenTasks";
    }


}