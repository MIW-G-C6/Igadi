package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Annemarleen Bosma <makeItWork2021@annemarleenbosma.nl>
 * This controller acts as the default page of homepage.
 *
 */

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    protected String showIndexPage() {
        return "index";
    }
}

