package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 * This controller handles the login page.
 * A user can log in with his or her useraccount.
 *
 */

@Controller
public class UserLoginPageController {

    @GetMapping({"/", "templates/userLoginPage.html"})
    protected String showIndexPage() {
        return "userLoginPage";
    }
}


