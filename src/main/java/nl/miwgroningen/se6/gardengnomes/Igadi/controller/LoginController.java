package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 *
 *     Endpoint for the loginpage.
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