package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.configuration.UserRole;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.helpers.AuthorizationHelper;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.*;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenTaskRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.PatchRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Tjerk Nagel
 * doel:
 */

@Service
public class GardenService {

    private GardenRepository gardenRepository;
    private GardenUserService gardenUserService;
    private AuthorizationHelper authorizationHelper;

    public GardenService(GardenRepository gardenRepository, GardenUserService gardenUserService,
                         AuthorizationHelper authorizationHelper) {
        this.gardenRepository = gardenRepository;
        this.gardenUserService = gardenUserService;
        this.authorizationHelper = authorizationHelper;
    }

    public List<GardenDTO> getAllGardens() {
        return gardenRepository.findAll().stream().map(this::convertToGardenDTO).collect(Collectors.toList());
    }

    public GardenDTO convertToGardenDTO(Garden garden) {
        GardenDTO gardenDTO = new GardenDTO();
        gardenDTO.setGardenId(garden.getGardenId());
        gardenDTO.setGardenName(garden.getGardenName());
        return gardenDTO;
    }

    public Garden convertFromGardenDTO(GardenDTO gardenDTO) {
        Garden garden = new Garden();
        garden.setGardenId(gardenDTO.getGardenId());
        garden.setGardenName(gardenDTO.getGardenName());
        return garden;
    }

    public Garden getGardenById(int gardenId) {
        return gardenRepository.getById(gardenId);
    }

    public GardenDTO findGardenById(int gardenId) {
        Optional<Garden> garden = gardenRepository.findById(gardenId);
        if (garden.isEmpty()) {
            return new GardenDTO();
        } else {
            return convertToGardenDTO(garden.get());
        }
    }

    public void saveGarden(Garden garden) {
        gardenRepository.save(garden);
    }

    public void saveGardenAndMakeUserGardenManager(Garden garden, User user) {
        gardenRepository.save(garden);
        GardenUser gardenUser = new GardenUser();
        gardenUser.setGarden(garden);
        gardenUser.setUser(user);
        gardenUser.setRole(UserRole.GARDEN_MANAGER);
        gardenUserService.saveGardenUser(gardenUser);
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
}

