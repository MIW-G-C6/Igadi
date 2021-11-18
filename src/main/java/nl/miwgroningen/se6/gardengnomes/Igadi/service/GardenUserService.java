package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenUserRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.testing.componenttesting.Converter.GardenUserConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GardenUserService {

    private final GardenUserRepository gardenUserRepository;
    private final GardenUserConverter gardenUserConverter;

    public GardenUserService(GardenUserRepository gardenUserRepository, GardenUserConverter gardenUserConverter) {
        this.gardenUserRepository = gardenUserRepository;
        this.gardenUserConverter = gardenUserConverter;
    }

    public List<GardenUserDTO> getAllGardenUsers() {
        return gardenUserRepository.findAll().stream().map(gardenUserConverter::convertToGardenUserDTO)
                .collect(Collectors.toList());
    }

    public List<GardenUser> findAllGardenUsersByUserId(int userId) {
        return gardenUserRepository.findAllByuser_userId(userId);
    }

    public List<GardenUserDTO> findAllGardenUsersByGardenId(int gardenId) {
        return gardenUserRepository.findAllBygarden_gardenId(gardenId).stream()
                .map(gardenUserConverter::convertToGardenUserDTO).collect(Collectors.toList());
    }

    public List<GardenUserDTO> findAllGardenUsersByUserIdAndGardenId(int gardenId, int userId) {
        Garden garden = new Garden();
        garden.setGardenId(gardenId);
        User user = new User();
        user.setUserId(userId);
        List<GardenUser> gardenUsers = gardenUserRepository.findAllByGardenAndUser(garden, user);
        return gardenUsers.stream().map(gardenUserConverter::convertToGardenUserDTO).collect(Collectors.toList());
    }

    public List<GardenUserDTO> findAllGardenUsersByAll(int gardenId, int userId, String role) {
        Garden garden = new Garden();
        garden.setGardenId(gardenId);
        User user = new User();
        user.setUserId(userId);
        return gardenUserRepository.findAllByGardenAndUserAndRole(garden, user, role)
                .stream().map(gardenUserConverter::convertToGardenUserDTO).collect(Collectors.toList());
    }

    public void saveGardenUser(GardenUserDTO gardenUserDTO) {
        GardenUser gardenUser = gardenUserConverter.convertFromGardenUserDTO(gardenUserDTO);
        gardenUserRepository.save(gardenUser);
    }
}