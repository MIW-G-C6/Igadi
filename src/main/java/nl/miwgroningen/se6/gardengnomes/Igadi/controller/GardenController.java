package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.security.access.annotation.Secured;
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
    protected String showGardenForm(Model model, @ModelAttribute("message") ArrayList<String> message) {
        model.addAttribute("garden", new Garden());
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
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
        String message = "";
        if (!result.hasErrors()) {
            message = gardenService.saveGarden(garden);
            if (message.equals("")) {
                if (user != null) {
                    user.setGarden(garden);
                    user.setUserRole("garden manager");
                    userService.saveUser(user);
                }
                return "redirect:/gardens";
            }
        } else {
            message = "Something went wrong.";
        }
        redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
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
        /*Garden garden = gardenService.getGardenById(gardenId);
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
        gardenService.deleteGarden(garden);*/


        gardenService.deleteGardenById(gardenId);

        return "redirect:/gardens";
    }
}


