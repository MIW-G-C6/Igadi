package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.GardenUserService;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@RestController
public class RequestAjaxController {


    private final UserService userService;
    private final GardenUserService gardenUserService;
    private final GardenService gardenService;

    public RequestAjaxController(UserService userService, GardenUserService gardenUserService,
                                  GardenService gardenService) {
        this.userService = userService;
        this.gardenUserService = gardenUserService;
        this.gardenService = gardenService;
    }

    @PostMapping("/gardens/requests")
    public ResponseEntity<?> getSearchGardenResultViaAjax(@Valid @RequestBody GardenSearchCriteriaDTO searchGardens,
                                                          Errors errors, @AuthenticationPrincipal User user) {
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        List<GardenUser> currentSubscriptions = gardenUserService.findAllGardenUsersByUserId(user.getUserId());

        if(!searchGardens.getKeywords().contains(".js")) {
            if (searchGardens.getKeywords() != null && searchGardens.getKeywords().trim().isEmpty()) {
                allGardens.clear();
            } else {
                allGardens = gardenService.findByNameContains(searchGardens.getKeywords());
            }
            for (GardenUser subscription : currentSubscriptions) {
                allGardens.removeIf(gardenDTO -> subscription.getGarden().getGardenId() == gardenDTO.getGardenId());
            }
        } else {
                allGardens.clear();
            }
        return ResponseEntity.ok(allGardens);
    }
}
