package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * @author Tjerk Nagel
 *
 */

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

    @GetMapping("/gardens/new")
    protected String showGardenForm(Model model, @ModelAttribute("errorMessage") ArrayList<String> errorMessage) {
        model.addAttribute("garden", new Garden());
        if (!errorMessage.isEmpty()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "gardenForm";
    }

    @GetMapping("/gardens/delete")
    protected String showGardenForm(Model model) {
        model.addAttribute("allGardens", gardenService.getAllGardens());
        return "gardenDeleteForm";
    }

//    @GetMapping("/gardens/delete/{gardenId}")
//    protected String showGardenForm(@PathVariable("gardenId") Integer gardenId, Model model) {
//        Optional<GardenDTO> garden = Optional.ofNullable(gardenService.getGardenById(gardenId));
//        if (garden.isPresent()) {
//            model.addAttribute("garden", garden.get());
//            return "gardenDeleteForm";
//        }
//        return "redirect:/gardens";
//    }

    @PostMapping("gardens/new")
    protected String createOrUpdateGarden(@ModelAttribute("garden") Garden garden, BindingResult result,
                                          RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        String errorMessage = "";
        if (!result.hasErrors()) {
            errorMessage = gardenService.saveGarden(garden);
            if (errorMessage.equals("")) {
                user.setGarden(garden); // TODO this gives a nullpointerexception if the user is admin
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

//    @PostMapping("gardens/delete")
//    protected String deleteGarden(@ModelAttribute("gardenId") Garden garden, BindingResult result,
//                                          RedirectAttributes redirectAttributes) {
//        gardenService.deleteGarden(garden);
//    return "redirect:/gardens";
//    }
    // TODO saving a new garden gives me an error (duplicate primary key no. 3) when there's already data in the database
    // TODO return a message to let the user know whether the create/update was successful


    @PostMapping("gardens/delete/{gardenId}")
    public String deleteGardenById(@PathVariable("gardenId") int gardenId) {
        gardenService.deleteGarden(gardenId);

        return "redirect:/gardens";
    }
}


