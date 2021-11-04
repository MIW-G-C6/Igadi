package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class PatchController {

    private final PatchService patchService;
    private final GardenService gardenService;

    public PatchController(PatchService patchService, GardenService gardenService) {
        this.patchService = patchService;
        this.gardenService = gardenService;
    }

    @GetMapping("/overview/details/garden/patches/new/{gardenId}")
    protected String createNewPatch(@PathVariable("gardenId") int gardenId, Model model) {
        PatchDTO patch = new PatchDTO();
        model.addAttribute("patch", patch);
        model.addAttribute("gardenId", gardenId);
        return "patchForm";
    }

    @PostMapping ("/overview/details/garden/patches/new/{gardenId}")
    protected String saveNewPatch(@PathVariable("gardenId") int gardenId, @ModelAttribute("patch") PatchDTO patch,
                                  BindingResult result) {

        if (!result.hasErrors()) {
            patchService.savePatch(patchService.convertFromPatchDTO(patch, gardenService.getGardenById(gardenId)));
        }
       return "redirect:/overview/details/{gardenId}";
    }
}