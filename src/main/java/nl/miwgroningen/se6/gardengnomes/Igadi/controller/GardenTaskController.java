package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenTaskService;
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
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class GardenTaskController {

    private final GardenService gardenService;
    private final GardenTaskService gardenTaskService;
    private final AuthorizationHelper authorizationHelper;

    public GardenTaskController(GardenService gardenService, GardenTaskService gardenTaskService,
                                AuthorizationHelper authorizationHelper) {
        this.gardenService = gardenService;
        this.gardenTaskService = gardenTaskService;
        this.authorizationHelper = authorizationHelper;
    }

    @GetMapping("/overview/details/gardenTasks/{gardenId}")
    protected String showGardenTasks(@PathVariable("gardenId") int gardenId, Model model, @AuthenticationPrincipal User user,
                                         RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isUserGardenMember(user.getUserId(), gardenId)) {
            GardenDTO gardenDTO = gardenService.getGardenById(gardenId);
            List<GardenTaskDTO> allGardenTasks = gardenTaskService.getAllTasksByGardenId(gardenId);
            model.addAttribute("garden", gardenDTO);
            model.addAttribute("allGardenTasks", allGardenTasks);
            model.addAttribute("isUserGardenManager",
                    authorizationHelper.isUserGardenManager(user.getUserId(), gardenId));
            return "gardenTasks";
        } else {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }

    @PostMapping("/overview/details/gardenTasks/delete/{taskId}")
    public String deleteGardenTaskById(@PathVariable("taskId") int taskId, @AuthenticationPrincipal User user,
                                        RedirectAttributes redirectAttributes) {
        try {
            int gardenId = gardenTaskService.getGardenTaskById(taskId).getGarden().getGardenId();
            gardenTaskService.userDeleteGardenTask(user.getUserId(), gardenTaskService.getGardenTaskById(taskId));
            return "redirect:/overview/details/gardenTasks/" + gardenId;
        } catch (SecurityException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }

    @GetMapping("/overview/details/gardenTasks/new/{gardenId}")
    protected String showGardenTaskForm(@PathVariable("gardenId") int gardenId, Model model,
                                       @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        try {
            if (authorizationHelper.isUserGardenManager(user.getUserId(), gardenId)) {
                GardenTaskDTO gardenTaskDTO = new GardenTaskDTO();
                gardenTaskDTO.setGardenDTO(gardenService.getGardenById(gardenId));
                gardenTaskDTO.setDone(false);
                model.addAttribute("gardenTask", gardenTaskDTO);
                return "gardenTaskForm";
            } else {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        }  catch (NullPointerException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
            return "redirect:/error";
        }
    }

    @PostMapping("/overview/details/gardenTasks/new/{gardenId}")
    protected String saveOrUpdateGardenTask(@PathVariable("gardenId") int gardenId,
                                           @ModelAttribute("patchTask") GardenTaskDTO gardenTaskDTO,
                                           BindingResult result, @AuthenticationPrincipal User user,
                                           RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            try {
                gardenTaskDTO.setGardenDTO(gardenService.getGardenById(gardenId));
                gardenTaskService.userSaveGardenTask(gardenTaskDTO, user.getUserId(), gardenId);
                return "redirect:/overview/details/gardenTasks/{gardenId}";
            }
            catch (SecurityException ex) {
                redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
                return "redirect:/error";
            }
        } else {
            return "redirect:/overview/details/gardenTasks/new/{gardenId}";
        }
    }
}