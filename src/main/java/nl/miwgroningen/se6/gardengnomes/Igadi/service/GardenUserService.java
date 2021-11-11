package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenUserRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.GardenUserConverter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GardenUserService {

    private GardenUserRepository gardenUserRepository;
    private GardenUserConverter gardenUserConverter;

    public GardenUserService(GardenUserRepository gardenUserRepository, GardenUserConverter gardenUserConverter) {
        this.gardenUserRepository = gardenUserRepository;
        this.gardenUserConverter = gardenUserConverter;
    }

    public List<GardenUserDTO> getAllGardenUsers() {
        return gardenUserRepository.findAll().stream().map(gardenUserConverter::convertToGardenUserDTO).collect(Collectors.toList());
    }

    public List<GardenUser> findAllGardenUsersByUserId(int userId) {
        return gardenUserRepository.findAllByuser_userId(userId);
    }

    public List<GardenUser> findAllGardenUsersByUserIdAndGardenId(int gardenId, int userId) {
        Garden garden = new Garden();
        garden.setGardenId(gardenId);
        User user = new User();
        user.setUserId(userId);
        return gardenUserRepository.findAllByGardenAndUser(garden, user);
    }

    public List<GardenUser> findAllGardenUsersByAll(int gardenId, int userId, String role) {
        Garden garden = new Garden();
        garden.setGardenId(gardenId);
        User user = new User();
        user.setUserId(userId);
        return gardenUserRepository.findAllByGardenAndUserAndRole(garden, user, role);
    }

    public void saveGardenUser(GardenUserDTO gardenUserDTO) {
        GardenUser gardenUser = gardenUserConverter.convertFromGardenUserDTO(gardenUserDTO);
        gardenUserRepository.save(gardenUser);
    }
}
