package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 * This controller acts as the default page of homepage.
 *
 */

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    protected String showIndexPage(Model model, @AuthenticationPrincipal User user) {
        if(user != null) {
            model.addAttribute(user);
        }
        return "index";
    }
}

