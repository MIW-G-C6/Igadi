package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHelper {

    private final GardenUserService gardenUserService;
    private final UserService userService;

    public AuthorizationHelper(GardenUserService gardenUserService, UserService userService) {
        this.gardenUserService = gardenUserService;
        this.userService = userService;
    }

    public boolean isUserGardenManager(int userId, int gardenId) {
        if (isAdmin(userId)) {
            return true;
        } else {
            return !gardenUserService.findAllGardenUsersByAll(gardenId, userId, UserRole.GARDEN_MANAGER).isEmpty();
        }
    }

    public boolean isUserGardenMember(int userId, int gardenId) {
        if (isAdmin(userId)) {
            return true;
        } else {
            return !gardenUserService.findAllGardenUsersByUserIdAndGardenId(gardenId, userId).isEmpty();
        }
    }

    public boolean isAdmin(int userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return userDTO.getUserEmail().equals("admin@admin.com");
    }
}