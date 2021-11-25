package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.JoinGardenRequestService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@RestController
public class AdminAjaxController {

    private final AuthorizationHelper authorizationHelper;
    private final UserService userService;

    public AdminAjaxController(AuthorizationHelper authorizationHelper, UserService userService) {
        this.authorizationHelper = authorizationHelper;
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> getSearchUserResultViaAjax(@Valid @RequestBody UserSearchCriteriaDTO searchUsers,
                                                        Errors errors, @AuthenticationPrincipal User user) {
        List<UserDTO> allUsers = userService.getAllUsers();

        if(!searchUsers.getKeywords().contains(".js")) {
            if (searchUsers.getKeywords() != null && searchUsers.getKeywords().trim().isEmpty()) {
                allUsers.clear();
            } else {
                allUsers = userService.findByNameContains(searchUsers.getKeywords());
                allUsers.removeIf(userAdmin -> userAdmin.getUserId() == user.getUserId());
            }
        } else {
            allUsers.clear();
        }
        return ResponseEntity.ok(allUsers);
    }
}