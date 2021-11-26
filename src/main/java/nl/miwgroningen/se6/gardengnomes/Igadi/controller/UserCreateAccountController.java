package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class UserCreateAccountController {

    private final UserService userService;
    private final GardenService gardenService;
    private final PasswordEncoder passwordEncoder;

    public UserCreateAccountController(UserService userService, GardenService gardenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.gardenService = gardenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users/new")
    protected String showUserForm(Model model, @ModelAttribute("message") ArrayList<String> message) {
        model.addAttribute("user", new UserDTO());
        if (!message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "userCreateAccountPage";
    }

    @PostMapping("/users/new")
    protected String saveOrUpdateUser(@ModelAttribute("user") UserDTO userDTO, BindingResult result,
                                      RedirectAttributes redirectAttributes) throws InterruptedException {
        String message = "";
        if (!result.hasErrors()) {
            boolean duplicateEmail = userService.checkIfUserEmailExists(userDTO.getUserEmail());
            if (duplicateEmail) {
                message = "This email address is already being used!";
                redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
            } else if (!userDTO.getPassword1().equals(userDTO.getPassword2())) {
                message = "Passwords are not the same!";
                redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
            } else if(userDTO.getUserEmail().equals("") || userDTO.getUserName().equals("") ||
                    userDTO.getPassword1().equals("") || userDTO.getPassword2().equals("")) {
                message = "Please fill in all fields.";
                redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
            } else if(userDTO.getUserName().trim().equals("") || userDTO.getPassword1().trim().equals("")
                    || userDTO.getPassword2().trim().equals("")) {
                message = "Please do not leave whitespace in your password.";
                redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
            } else {
                userDTO.setPassword1(passwordEncoder.encode(userDTO.getPassword1()));
                userService.saveUser(userDTO);
                message = "Welcome & please login!";
                redirectAttributes.addAttribute("message", List.of(message, "greenMessage"));
                return "redirect:/login";
            }
        } else {
            message = "Something went wrong.";
            redirectAttributes.addAttribute("message", List.of(message, "redMessage"));
        }
        return "redirect:/users/new";
    }
}