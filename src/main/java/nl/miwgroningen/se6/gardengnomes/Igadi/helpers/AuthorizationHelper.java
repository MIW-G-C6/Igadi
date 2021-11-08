package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.PatchService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorizationHelper {

    private GardenUserService gardenUserService;
    private PatchService patchService;

    public AuthorizationHelper(GardenUserService gardenUserService, PatchService patchService) {
        this.gardenUserService = gardenUserService;
        this.patchService = patchService;
    }

    public boolean isUserGardenManager(int userId, int gardenId) {
        return !gardenUserService.findAllGardenUsersByAll(gardenId, userId, UserRole.GARDEN_MANAGER).isEmpty();
    }

    public boolean isUserGardenManagerOfPatch(int userId, int patchId) {
        int gardenId = patchService.findGardenIdByPatchId(patchId).orElseThrow(() ->
                new NullPointerException("No garden was found for this patch."));
        return isUserGardenManager(userId, gardenId);
    }
}
