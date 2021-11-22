package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 *
 *     Endpoint for the loginpage.
 *
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    protected String showLoginPage(Model model, @ModelAttribute("message") ArrayList<String> message) {

        model.addAttribute("user", new User());

        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "login";
    }
}