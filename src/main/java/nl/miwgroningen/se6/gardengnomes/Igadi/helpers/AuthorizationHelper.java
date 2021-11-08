package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
public class AuthorizationHelper {

    private GardenUserService gardenUserService;

    public AuthorizationHelper(GardenUserService gardenUserService) {
        this.gardenUserService = gardenUserService;
    }

    public boolean isUserGardenManager(int userId, int gardenId) {
        return !gardenUserService.findAllGardenUsersByAll(gardenId, userId, UserRole.GARDEN_MANAGER).isEmpty();
    }
}
