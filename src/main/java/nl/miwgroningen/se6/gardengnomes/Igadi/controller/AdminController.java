package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.JoinGardenRequestDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Controller
public class AdminController {

    private final AuthorizationHelper authorizationHelper;
    private final UserService userService;

    public AdminController(AuthorizationHelper authorizationHelper, UserService userService) {
        this.authorizationHelper = authorizationHelper;
        this.userService = userService;
    }

    @GetMapping("/users")
    protected String showAllUsers(Model model, @AuthenticationPrincipal User user,
                                  RedirectAttributes redirectAttributes) {
        if (authorizationHelper.isAdmin(user.getUserId())) {
            List<UserDTO> allUsers = userService.getAllUsers();
            allUsers.removeIf(userAdmin -> userAdmin.getUserId() == user.getUserId());

            model.addAttribute("allUsers", allUsers);
            return "users";
        } else {
            redirectAttributes.addAttribute("httpStatus", HttpStatus.FORBIDDEN);
            return "redirect:/error";
        }
    }
}
