package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class GardenTaskController {

    private final GardenService gardenService;
    private final GardenTaskService gardenTaskService;

    public GardenTaskController(GardenService gardenService, GardenTaskService gardenTaskService) {
        this.gardenService = gardenService;
        this.gardenTaskService = gardenTaskService;
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