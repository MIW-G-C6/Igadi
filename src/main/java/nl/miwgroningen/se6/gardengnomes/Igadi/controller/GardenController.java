package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
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
public class GardenController {

    private GardenRepository gardenRepository;

    public GardenController(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    @GetMapping({"/", "/gardens"})
    protected String showGardenOverview(Model model) {
        model.addAttribute("allGardens", gardenRepository.findAll());
        System.out.println("yess");
        return "gardenOverview";
    }

}
