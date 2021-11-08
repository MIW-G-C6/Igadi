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

    @GetMapping("/overview/details/garden/patches/new/{gardenId}")
    protected String newPatchForm(@PathVariable(value = "gardenId") Integer gardenId, Model model,
                                  @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)) {
            PatchDTO patch = new PatchDTO();
            patch.setGardenDTO(gardenService.convertToGardenDTO(gardenService.getGardenById(gardenId)));
            model.addAttribute("patch", patch);
            model.addAttribute("buttonText", "Create patch");
            return "patchForm";
        } else {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }

    @GetMapping("/overview/details/garden/patches/edit/{patchId}")
    protected String editPatchForm(@PathVariable(value = "patchId") Integer patchId, Model model,
                                   @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        try {
            if (authorizationHelper.isUserGardenManager(user.getUserId(), patchService.findGardenIdByPatchId(patchId))) {
                PatchDTO patch = patchService.convertToPatchDTO(patchService.getPatchById(patchId));
                model.addAttribute("patch", patch);
                model.addAttribute("buttonText", "Update patch");
                return "patchForm";
            } else {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
            return "redirect:/error";
        }
    }

    @PostMapping ("/overview/details/garden/patches/new/{gardenId}")
    protected String saveNewPatch(@PathVariable("gardenId") int gardenId, @ModelAttribute("patch") PatchDTO patch,
                                  BindingResult result, @AuthenticationPrincipal User user,
                                  RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            try {
                patchService.userSavePatch(patchService.convertFromPatchDTO(patch, gardenService.getGardenById(gardenId)),
                        user.getUserId(), gardenId);
                return "redirect:/overview/details/{gardenId}";
            }
            catch (SecurityException ex) {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        } else {
            return "redirect:/overview/details/garden/patches/new/{gardenId}";
        }
    }
}