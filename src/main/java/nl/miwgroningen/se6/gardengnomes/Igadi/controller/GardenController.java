package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GardenController {

    private GardenService gardenService;
    private UserService userService;

    public GardenController(GardenService gardenService, UserService userService) {
        this.gardenService = gardenService;
        this.userService = userService;
    }

    @GetMapping("/gardens")
    protected String showGardens(Model model) {
        model.addAttribute("allGardens", gardenService.getAllGardens());
        return "gardens";
    }

    // TODO sort by id before returning?
    @GetMapping("/gardens/new")
    protected String showGardenForm(Model model, @ModelAttribute("errorMessage") ArrayList<String> errorMessage) {
        model.addAttribute("garden", new Garden());
        if (!errorMessage.isEmpty()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "gardenForm";
    }

    // TODO put info for developer in logging
    // TODO saving a new garden gives me an error (duplicate primary key no. 3) when there's already data in the database
    @PostMapping("gardens/new")
    protected String createOrUpdateGarden(@ModelAttribute("garden") Garden garden, BindingResult result,
                                          RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        String errorMessage = "";
        if (!result.hasErrors()) {
            errorMessage = gardenService.saveGarden(garden);
            if (errorMessage.equals("")) {
                user.setGarden(garden);
                user.setUserRole("garden manager");
                userService.saveUser(user);
                return "redirect:/gardens";
            }
        } else {
            errorMessage = "Something went wrong.";
        }
        redirectAttributes.addAttribute("errorMessage", List.of(errorMessage, "redErrorMessage"));
        return "redirect:/gardens/new";
        // TODO don't renew form, for both create and update
    }

}