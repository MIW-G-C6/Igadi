package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @ModelAttribute("isAdmin")
    public boolean isAdmin(@AuthenticationPrincipal User user) {
        if (user != null) {
            return authorizationHelper.isAdmin(user.getUserId());
        } else {
            return false;
        }
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
            return "error/403";
        }
    }

    @PostMapping("users/delete/{userId}")
    public String deleteUserById(@PathVariable("userId") int userId, RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal User user) {
        try {
            userService.deleteUser(userService.getUserById(userId));
            return "redirect:/users";
        } catch (SecurityException ex) {
            return "error/403";
        }
    }
}