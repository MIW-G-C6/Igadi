package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class PatchController {

    private final PatchService patchService;
    private final GardenService gardenService;
    private final AuthorizationHelper authorizationHelper;

    public PatchController(PatchService patchService, GardenService gardenService, AuthorizationHelper authorizationHelper) {
        this.patchService = patchService;
        this.gardenService = gardenService;
        this.authorizationHelper = authorizationHelper;
    }

    @GetMapping({"/overview/details/garden/patches/edit/{patchId}",
            "/overview/details/garden/patches/new/{gardenId}"})
    protected String editPatchForm(@PathVariable(value = "patchId", required = false) Integer patchId,
                                   @PathVariable(value = "gardenId", required = false) Integer gardenId, Model model,
                                   @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        PatchDTO patch;
        String buttonText;
        if(patchId == null && authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)) {
            patch = new PatchDTO();
            patch.setGardenDTO(gardenService.convertToGardenDTO(gardenService.getGardenById(gardenId)));
            buttonText = "Create patch";
        } else {
            try {
                if (authorizationHelper.isUserGardenManagerOfPatch(user.getUserId(), patchId)) {
                    patch = patchService.convertToPatchDTO(patchService.getPatchById(patchId));
                    buttonText = "Update patch";
                } else {
                    redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                    return "redirect:/error";
                }
            } catch (EntityNotFoundException ex) {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
                return "redirect:/error";
            }
        }
        model.addAttribute("patch", patch);
        model.addAttribute("buttonText", buttonText);
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