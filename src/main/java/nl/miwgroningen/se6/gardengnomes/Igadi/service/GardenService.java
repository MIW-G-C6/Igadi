package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.UserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.GardenConverter;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.GardenUserConverter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GardenService {

    private final GardenRepository gardenRepository;
    private final GardenUserService gardenUserService;
    private final AuthorizationHelper authorizationHelper;
    private final GardenConverter gardenConverter;
    private final GardenUserConverter gardenUserConverter;

    public GardenService(GardenRepository gardenRepository, GardenUserService gardenUserService,
                         AuthorizationHelper authorizationHelper, GardenConverter gardenConverter,
                         GardenUserConverter gardenUserConverter) {
        this.gardenRepository = gardenRepository;
        this.gardenUserService = gardenUserService;
        this.authorizationHelper = authorizationHelper;
        this.gardenConverter = gardenConverter;
        this.gardenUserConverter = gardenUserConverter;
    }

    public List<GardenDTO> getAllGardens() {
        return gardenRepository.findAll().stream().map(gardenConverter::convertToGardenDTO).collect(Collectors.toList());
    }

    public GardenDTO getGardenById(int gardenId) {
        return gardenConverter.convertToGardenDTO(gardenRepository.getById(gardenId));
    }

    public void saveGarden(GardenDTO garden) {
        gardenRepository.save(gardenConverter.convertFromGardenDTO(garden));
    }

    public void saveGardenAndMakeUserGardenManager(GardenDTO gardenDTO, User user) {
        gardenRepository.save(gardenConverter.convertFromGardenDTO(gardenDTO));
        GardenUserDTO gardenUserDTO = new GardenUserDTO();
        gardenUserDTO.setGardenDTO(gardenDTO);
        gardenUserDTO.setUser(user);
        gardenUserDTO.setRole(UserRole.GARDEN_MANAGER);
        gardenUserService.saveGardenUser(gardenUserDTO);
    }

    public void userDeleteGarden(int userId, int gardenId) {
        if (authorizationHelper.isUserGardenManager(userId, gardenId)) {
            deleteGardenById(gardenId);
        } else {
            throw new SecurityException("You are not allowed to delete this garden.");
        }
    }

    public void deleteGardenById(int gardenId) {
        gardenRepository.deleteById(gardenId);
    }

    public Garden temporaryConvertForSeeder(GardenDTO gardenDTO) {
        return gardenConverter.convertFromGardenDTO(gardenDTO);
    }
}