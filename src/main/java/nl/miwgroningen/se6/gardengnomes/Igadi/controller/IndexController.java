package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 * This controller acts as the default page of homepage.
 *
 */

@Controller
public class IndexController {

    private GardenUserService gardenUserService;
    private GardenService gardenService;

    public IndexController(GardenUserService gardenUserService, GardenService gardenService) {
        this.gardenUserService = gardenUserService;
        this.gardenService = gardenService;
    }

    @GetMapping({"/", "/index"})
    protected String showIndexPage(Model model, @AuthenticationPrincipal User user) {
        if(user != null) {
            user.setGardenUsers(gardenUserService.findAllGardenUsersByUserId(user.getUserId()));

            ArrayList<GardenDTO> gardens = new ArrayList<>();
            for (GardenUser gardenUser : user.getGardenUsers()) {
                GardenDTO newGarden = gardenService.getGardenById(gardenUser.getGarden().getGardenId());
                gardens.add(newGarden);
            }

            ArrayList<String> roles = new ArrayList<>();
            for (GardenDTO garden : gardens) {
                int gardenId1 = garden.getGardenId();
                for (GardenUser gardenUser : user.getGardenUsers()) {
                    int gardenId2 = gardenUser.getGarden().getGardenId();
                    if (gardenId1 == gardenId2) {
                        roles.add(gardenUser.getRole());
                    }
                }
            }
            model.addAttribute("user", user);
            model.addAttribute("gardens", gardens);
            model.addAttribute("roles", roles);
        } else {
            user = new User();
            user.setUserName("admin");
            model.addAttribute(user);
        }
        return "index";
    }

    @GetMapping("/about")
    protected String showAboutPage(Model model) {
        return "about";
    }
}