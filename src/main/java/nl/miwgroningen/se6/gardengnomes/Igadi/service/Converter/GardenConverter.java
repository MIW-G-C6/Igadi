package nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import org.springframework.stereotype.Component;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class GardenConverter {

    public GardenDTO convertToGardenDTO(Garden garden) {
        GardenDTO gardenDTO = new GardenDTO();
        gardenDTO.setGardenId(garden.getGardenId());
        gardenDTO.setGardenName(garden.getGardenName());
        gardenDTO.setLocation(garden.getLocation());
        return gardenDTO;
    }

    public Garden convertFromGardenDTO(GardenDTO gardenDTO) {
        Garden garden = new Garden();
        garden.setGardenId(gardenDTO.getGardenId());
        garden.setGardenName(gardenDTO.getGardenName());
        garden.setLocation(gardenDTO.getLocation());
        return garden;
    }
}