package nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.GardenUserDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.GardenUser;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.stereotype.Component;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class GardenUserConverter {

    private final GardenConverter gardenConverter;
    private final UserConverter userConverter;

    public GardenUserConverter(GardenConverter gardenConverter, UserConverter userConverter) {
        this.gardenConverter = gardenConverter;
        this.userConverter = userConverter;
    }

    public GardenUserDTO convertToGardenUserDTO(GardenUser gardenUser) {
        GardenUserDTO gardenUserDTO = new GardenUserDTO();
        gardenUserDTO.setGardenUserId(gardenUser.getGardenUserId());
        gardenUserDTO.setRole(gardenUser.getRole());
        gardenUserDTO.setGardenDTO(gardenConverter.convertToGardenDTO(gardenUser.getGarden()));
        gardenUserDTO.setUser(gardenUser.getUser());
        return gardenUserDTO;
    }

    public GardenUser convertFromGardenUserDTO(GardenUserDTO gardenUserDTO) {
        GardenUser gardenUser = new GardenUser();
        gardenUser.setGardenUserId(gardenUserDTO.getGardenUserId());
        gardenUser.setRole(gardenUserDTO.getRole());
        gardenUser.setGarden(gardenConverter.convertFromGardenDTO(gardenUserDTO.getGardenDTO()));
        gardenUser.setUser(gardenUserDTO.getUser());
        return gardenUser;
    }

}
