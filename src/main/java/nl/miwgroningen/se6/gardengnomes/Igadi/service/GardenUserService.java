package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class GardenUserService {

    private GardenUserRepository gardenUserRepository;

    public GardenUserService(GardenUserRepository gardenUserRepository) {
        this.gardenUserRepository = gardenUserRepository;
    }

    public ArrayList<GardenUser> findAllGardenUsersByUserId(int userId) {
        return gardenUserRepository.findAllByuser_userId(userId);
    }

    public ArrayList<GardenUser> findAllGardenUsersByUserIdAndGardenId(int gardenId, int userId) {
        Garden garden = new Garden();
        garden.setGardenId(gardenId);
        User user = new User();
        user.setUserId(userId);
        return gardenUserRepository.findAllByGardenAndUser(garden, user);
    }

    public ArrayList<GardenUser> findAllGardenUsersByAll(int gardenId, int userId, String role) {
        Garden garden = new Garden();
        garden.setGardenId(gardenId);
        User user = new User();
        user.setUserId(userId);
        return gardenUserRepository.findAllByGardenAndUserAndRole(garden, user, role);
    }

    public void saveGardenUser(GardenUser gardenUser) {
        gardenUserRepository.save(gardenUser);
    }
}
