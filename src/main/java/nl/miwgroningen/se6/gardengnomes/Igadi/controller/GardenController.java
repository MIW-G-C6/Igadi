package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class GardenController {

    private GardenRepository gardenRepository;

    public GardenController(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    @GetMapping({"/", "/gardens"})
    protected String showGardenOverview(Model model) {
        model.addAttribute("allGardens", gardenRepository.findAll());
        return "gardenOverview";
    }

}
