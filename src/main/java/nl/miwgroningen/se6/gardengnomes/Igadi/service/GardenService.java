package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public GardenService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
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

    public GardenDTO getGardenById(int gardenId) {
        Garden garden = gardenRepository.getById(gardenId);
        return convertToGardenDTO(garden);
    }

    public GardenDTO findGardenById(int gardenId) {
        Optional<Garden> garden = gardenRepository.findById(gardenId);
        if (garden.isEmpty()) {
            return new GardenDTO(); // TODO what do we want to return here?
        } else {
            return convertToGardenDTO(garden.get());
        }
    }

    public String saveGarden(Garden garden) {
        String errorMessage = "";
        try {
            gardenRepository.save(garden);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) { // TODO make this more specific
                errorMessage = "That name already exists.";
            } else {
                errorMessage = "Something went wrong.";
            }
        } catch (Exception ex) {
            errorMessage = "Something went wrong.";
        }
        return errorMessage;
    }
}
