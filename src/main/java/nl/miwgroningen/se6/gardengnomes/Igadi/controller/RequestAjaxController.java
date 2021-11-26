package nl.miwgroningen.se6.gardengnomes.Igadi.controller;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.*;
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
public class RequestAjaxController {

    private final UserService userService;
    private final GardenUserService gardenUserService;
    private final GardenService gardenService;
    private final JoinGardenRequestService joinGardenRequestService;

    public RequestAjaxController(UserService userService, GardenUserService gardenUserService,
                                  GardenService gardenService, JoinGardenRequestService joinGardenRequestService) {
        this.userService = userService;
        this.gardenUserService = gardenUserService;
        this.gardenService = gardenService;
        this.joinGardenRequestService = joinGardenRequestService;
    }

    @PostMapping("/gardens/requests")
    public ResponseEntity<?> getSearchGardenResultViaAjax(@Valid @RequestBody GardenSearchCriteriaDTO searchGardens,
                                                          Errors errors, @AuthenticationPrincipal User user) {
        List<GardenDTO> allGardens = gardenService.getAllGardens();
        List<GardenUser> currentSubscriptions = gardenUserService.findAllGardenUsersByUserId(user.getUserId());
        List<JoinGardenRequestDTO> allActiveGardenRequests = joinGardenRequestService
                .findAllRequestsByUserId(user.getUserId());

        if(!searchGardens.getKeywords().contains(".js")) {
            if (searchGardens.getKeywords() != null && searchGardens.getKeywords().trim().isEmpty()) {
                allGardens.clear();
            } else {
                allGardens = gardenService.findByNameContains(searchGardens.getKeywords());
            }
            for (GardenUser subscription : currentSubscriptions) {
                allGardens.removeIf(gardenDTO -> subscription.getGarden().getGardenId() == gardenDTO.getGardenId());
            }
            for(JoinGardenRequestDTO request : allActiveGardenRequests) {
                allGardens.removeIf(gardenDTO -> request.getGardenDTO().getGardenId() == gardenDTO.getGardenId());
            }
        } else {
                allGardens.clear();
            }
        return ResponseEntity.ok(allGardens);
    }
}