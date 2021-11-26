package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Annemarleen Bosma <makeItWokr2021@annemarleenbosma.nl>
 *
 * This page controlls the profile page, especially supporting the option to change settings
 *
 */

@Controller
public class ProfileSettingsController {

    private UserService userService;

    public ProfileSettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profileSettings")
    protected String showProfileSettings(Model model, @AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.getUserById(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("buttonText", "Save changes");
        return "profileSettings";
    }
}