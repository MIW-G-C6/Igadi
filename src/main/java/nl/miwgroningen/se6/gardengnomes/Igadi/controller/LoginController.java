package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 *
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    protected String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
}