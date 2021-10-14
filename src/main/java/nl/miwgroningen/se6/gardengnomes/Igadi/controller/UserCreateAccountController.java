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
    protected String showUserForm(Model model, @ModelAttribute("errorMessage") String errorMessage) {
        System.out.println(errorMessage);
        model.addAttribute("user", new User());
        model.addAttribute("allGardens", gardenService.getAllGardens());
        return "userCreateAccountPage";
    }

    @PostMapping("/users/new")
    protected String saveOrUpdateUser(@ModelAttribute("user") User user, BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        boolean duplicateUserName = userService.checkIfUserNameExists(user.getUserName());
        if(duplicateUserName) {
            String errorMessage = "This name is already taken!";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/users/new";
        }
        if (!result.hasErrors()) {
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            userService.saveUser(user);
        }
        return "redirect:/users/new";
    }
}