package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
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

<<<<<<< HEAD
    public AdminOverviewController(GardenRepository gardenRepository, PatchRepository patchrepository) {
        this.patchRepository = patchrepository;
=======
    public AdminOverviewController(GardenRepository gardenRepository, PatchRepository patchRepository) {
>>>>>>> ea0b31fe94e272ea6e15dcb457e94a6e99f0c694
        this.gardenRepository = gardenRepository;
        this.patchRepository = patchRepository;
    }

    @GetMapping({"/", "/overview"})
    protected String showGardenOverview(Model model) {
        model.addAttribute("allGardens", gardenRepository.findAll());
        model.addAttribute("allPatches", patchRepository.findAll());
        return "adminOverview";
    }

}
