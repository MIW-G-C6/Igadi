package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    public GardenTaskController(GardenService gardenService, GardenTaskService gardenTaskService) {
        this.gardenService = gardenService;
        this.gardenTaskService = gardenTaskService;
    }

    @GetMapping("/overview/details/gardenTasks/{gardenId}")
        protected String showGardenTasks(@PathVariable("gardenId") int gardenId, Model model) {
            GardenDTO garden = gardenService.convertToGardenDTO(gardenService.getGardenById(gardenId));
            List<GardenTaskDTO> allGardenTasks = gardenTaskService.getAllTasksByGardenId(gardenId);
            int chosenTask = 0;
            model.addAttribute("garden", garden);
            model.addAttribute("allGardenTasks", allGardenTasks);
            model.addAttribute("chosenTask", chosenTask);
        return "gardenTasks";
    }

    @PostMapping("/overview/details/gardenTasks/delete/{taskId}")
    public String deleteGardenTaskById(@PathVariable("taskId") int taskId, @AuthenticationPrincipal User user,
                                        RedirectAttributes redirectAttributes) {
        try {
            int gardenId = gardenTaskService.getGardenTaskById(taskId).getGarden().getGardenId();
            gardenTaskService.deleteGardenTask(user.getUserId(), gardenTaskService.getGardenTaskById(taskId));
            return "redirect:/overview/details/gardenTasks/" + gardenId;
        } catch (SecurityException ex) {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }
}