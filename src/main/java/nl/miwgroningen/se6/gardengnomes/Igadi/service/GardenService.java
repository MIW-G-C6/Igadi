package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.GardenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
}