package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

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
public class ProfileSettings {

    @GetMapping("/profileSettings")
    protected String showContactPage(Model model) {
        return "profileSettings";
    }

}
