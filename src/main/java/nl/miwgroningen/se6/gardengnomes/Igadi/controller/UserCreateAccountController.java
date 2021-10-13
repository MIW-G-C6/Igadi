package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class UserCreateAccountController {

    private UserService userService;

    public UserCreateAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    protected String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "userCreateAccountPage";
    }

    @PostMapping("/users/new")
    protected String saveOrUpdateUser(@ModelAttribute("user") User user, BindingResult result) {
        if (!result.hasErrors()) {
            userService.saveUser(user);
        }
        return "redirect:/gardens";
    }
}
