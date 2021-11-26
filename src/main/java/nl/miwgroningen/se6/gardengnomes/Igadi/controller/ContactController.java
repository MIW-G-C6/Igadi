package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 *
 * This page controlls the contact page
 *
 */

@Controller
public class ContactController {

    private final AuthorizationHelper authorizationHelper;

    public ContactController(AuthorizationHelper authorizationHelper) {
        this.authorizationHelper = authorizationHelper;
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(@AuthenticationPrincipal User user) {
        if (user != null) {
            return authorizationHelper.isAdmin(user.getUserId());
        } else {
            return false;
        }
    }

    @GetMapping("/contact")
    protected String showContactPage(Model model) {
        return "contact";
    }
}