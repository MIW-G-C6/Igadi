package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenerSearchCriteriaDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     Complexity is rather high on the methods here, because it involves a ajax-request.
 */

@RestController
public class GardenerAjaxController {

    private final UserService userService;
    private final GardenUserService gardenUserService;
    private final GardenService gardenService;

    public GardenerAjaxController(UserService userService, GardenUserService gardenUserService,
                                  GardenService gardenService) {
        this.userService = userService;
        this.gardenUserService = gardenUserService;
        this.gardenService = gardenService;
    }

    @PostMapping("/overview/details/{gardenId}/gardeners")
    public ResponseEntity<?> getSearchResultViaAjax(@PathVariable("gardenId") int gardenId,
                                                    @Valid @RequestBody GardenerSearchCriteriaDTO searchGardeners,
                                                    Errors errors) {
        List<UserDTO> users = userService.getAllUsers();
        List<GardenUserDTO> alreadyAddedUser = gardenUserService.findAllGardenUsersByGardenId(gardenId);

        if(!searchGardeners.getKeywords().contains(".js")) {
            if (searchGardeners.getKeywords() != null && searchGardeners.getKeywords().trim().isEmpty()) {
                users.clear();
            } else {
                users = userService.findByNameContains(searchGardeners.getKeywords());
            }
            for (GardenUserDTO gardenUserDTO : alreadyAddedUser) {
                users.removeIf(realUsers -> gardenUserDTO.getUserDTO().getUserId() == realUsers.getUserId());
            }
        } else {
            users.clear();
        }
        return ResponseEntity.ok(users);
    }
}