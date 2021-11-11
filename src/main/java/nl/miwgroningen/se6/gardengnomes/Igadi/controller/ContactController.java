package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 *
 *
 *
 */

@Controller
public class ContactController {

    @GetMapping("/contact")
    protected String showContactPage(Model model) {
        return "contact";
    }
}