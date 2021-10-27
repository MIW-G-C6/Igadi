package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
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
    private PatchService patchService;
    private UserService userService;

    public GardenController(GardenService gardenService, UserService userService, PatchService patchService) {
        this.gardenService = gardenService;
        this.userService = userService;
        this.patchService = patchService;
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
    }

//    @PostMapping("gardens/delete")
//    protected String deleteGarden(@ModelAttribute("gardenId") Garden garden, BindingResult result,
//                                          RedirectAttributes redirectAttributes) {
//        gardenService.deleteGarden(garden);
//    return "redirect:/gardens";
//    }


    @PostMapping("gardens/delete/{gardenId}")
    public String deleteGardenById(@PathVariable("gardenId") int gardenId) {
        Garden garden = gardenService.getGardenById(gardenId);
        User user = userService.getUserByGardenId(gardenId);
        if(user != null) {
            user.setGarden(null);
            userService.saveUser(user);
        }
        ArrayList<Patch> patches = patchService.findAllPatchesByGardenId(gardenId);

        if(!patches.isEmpty()) {
            for(Patch patch : patches) {
                patchService.deleteAllPatchesWithGarden(patch);
            }
        }
        gardenService.deleteGarden(garden);

        return "redirect:/gardens";
    }
}


