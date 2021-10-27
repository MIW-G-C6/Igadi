package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class UserCreateAccountController {

    private UserService userService;
    private GardenService gardenService;
    PasswordEncoder passwordEncoder;

    public UserCreateAccountController(UserService userService, GardenService gardenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.gardenService = gardenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/new")
    protected String showUserForm(Model model, @ModelAttribute("message") ArrayList<String> message) {
        model.addAttribute("user", new User());
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "userCreateAccountPage";
    }

    @PostMapping("/users/new")
    protected String saveOrUpdateUser(@ModelAttribute("user") User user, BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        String message = "";
        if (!result.hasErrors()) {
            boolean duplicateEmail = userService.checkIfUserEmailExists(user.getUserEmail());
            if (duplicateEmail) {
                message = "This email address has already been used!";
                redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
            } else {
                user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
                userService.saveUser(user);
                message = "Account was successfully created.";
                redirectAttributes.addAttribute("message", List.of(message, "greenMessage"));
            }
        } else {
            message = "Something went wrong.";
            redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        }
        return "redirect:/users/new";
    }
}