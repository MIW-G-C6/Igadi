package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenTaskDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenTask;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
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

    private final GardenRepository gardenRepository;
    private final GardenTaskRepository gardenTaskRepository;
    private final UserService userService;

    public GardenService(GardenRepository gardenRepository, GardenTaskRepository gardenTaskRepository,
                         UserService userService) {
        this.gardenRepository = gardenRepository;
        this.gardenTaskRepository = gardenTaskRepository;
        this.userService = userService;
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

    public Garden getGardenById(int gardenId) {
        Garden garden = gardenRepository.getById(gardenId);
        return garden;
    }

    public GardenDTO findGardenById(int gardenId) {
        Optional<Garden> garden = gardenRepository.findById(gardenId);
        if (garden.isEmpty()) {
            return new GardenDTO();
        } else {
            return convertToGardenDTO(garden.get());
        }
    }

    public String saveGarden(Garden garden) {
        String errorMessage = "";
        try {
            gardenRepository.save(garden);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException &&
                    ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException &&
                    ex.getCause().getCause().getMessage().contains("Duplicate entry")) {
                errorMessage = "That name already exists.";
            } else {
                errorMessage = "Something went wrong.";
            }
        } catch (Exception ex) {
            errorMessage = "Something went wrong.";
        }
        return errorMessage;
    }

    @Transactional
    public void deleteGarden(Garden garden){
        List<GardenTask> tasks = gardenTaskRepository.findAllBygarden_gardenId(garden.getGardenId());
        for(GardenTask task : tasks) {
            task.setGarden(null);
            gardenTaskRepository.delete(task);
        }
        gardenRepository.delete(garden);
    }

    public void deleteGardenById(int gardenId) {
        List<User> users = userService.findAllUsersByGardenId(gardenId);
        for (User user: users) {
            user.setGarden(null);
            userService.saveUser(user);
        }
        gardenRepository.deleteById(gardenId);
    }
}

