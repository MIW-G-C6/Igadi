package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Tjerk Nagel
 * doel:
 */

@Controller
public class GardenController {

    private GardenService gardenService;

    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

    @GetMapping("/gardens")
    protected String showGardens(Model model) {
        model.addAttribute("allGardens", gardenService.getAllGardens());
        return "gardens";
    }

    @GetMapping("/gardens/new")
    protected String showGardenForm(Model model, String errorMessage, String errorMessageColor) {
        model.addAttribute("garden", new Garden());
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorMessageColor", errorMessageColor);
        }
        return "gardenForm";
    }
    // TODO sort by id before returning?

    @PostMapping("gardens/new")
    protected String createOrUpdateGarden(@ModelAttribute("garden") Garden garden, BindingResult result,
                                          RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            try {
                gardenService.saveGarden(garden);
            } catch (DataIntegrityViolationException ex) {
                String errorMessage = "That name already exists.";
                redirectAttributes.addAttribute("errorMessage", errorMessage);
                redirectAttributes.addAttribute("errorMessageColor", "redErrorMessage");
                System.out.println(ex);
                return "redirect:/gardens/new";
            } catch (Exception ex) {
                String errorMessage = "Something went wrong.";
                redirectAttributes.addAttribute("errorMessage", errorMessage);
                redirectAttributes.addAttribute("errorMessageColor", "redErrorMessage");
                System.out.println(ex);
                return "redirect:/gardens/new";
            }
        }
        return "redirect:/gardens";
    }
    // TODO saving a new garden gives me an error (duplicate primary key no. 3) when there's already data in the database
    // TODO return a message to let the user know whether the create/update was successful
}