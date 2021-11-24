package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 *
 * This controller acts as the default page when not logged in.
 *
 * When logged in, the user will get to see the right profile page.
 *
 */

@Controller
public class IndexController {

    private GardenUserService gardenUserService;
    private GardenService gardenService;
    private AuthorizationHelper authorizationHelper;
    private UserService userService;

    public IndexController(GardenUserService gardenUserService, GardenService gardenService,
                           AuthorizationHelper authorizationHelper, UserService userService) {
        this.gardenUserService = gardenUserService;
        this.gardenService = gardenService;
        this.authorizationHelper = authorizationHelper;
        this.userService = userService;
    }

    @GetMapping({"/", "/index"})
    protected String showIndexPage(Model model, @AuthenticationPrincipal User user,
                                   @ModelAttribute("message") ArrayList<String> message) {
        if(user != null) {
            model.addAttribute("isAdmin", authorizationHelper.isAdmin(user.getUserId()));
            List<GardenUser> gardenUsers = gardenUserService.findAllGardenUsersByUserId(user.getUserId());

            ArrayList<GardenDTO> gardens = new ArrayList<>();
            for (GardenUser gardenUser : gardenUsers) {
                GardenDTO newGarden = gardenService.getGardenById(gardenUser.getGarden().getGardenId());
                gardens.add(newGarden);
            }

            ArrayList<String> roles = new ArrayList<>();
            for (GardenDTO garden : gardens) {
                int gardenId1 = garden.getGardenId();
                for (GardenUser gardenUser : gardenUsers) {
                    int gardenId2 = gardenUser.getGarden().getGardenId();
                    if (gardenId1 == gardenId2) {
                        roles.add(gardenUser.getRole());
                    }
                }
            }
            if(!message.isEmpty()) {
                model.addAttribute("message", message);
            }
            Collections.replaceAll(roles, "gardenManager", "garden manager");
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