package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenUserRepository;
import org.springframework.stereotype.Service;

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

    public ArrayList<GardenUser> findAllGardenUsersByUserIdAndGardenId(int userId, int gardenId) {
        return gardenUserRepository.findAllByuser_userIdAndgarden_gardenId(userId, gardenId);
    }
}
