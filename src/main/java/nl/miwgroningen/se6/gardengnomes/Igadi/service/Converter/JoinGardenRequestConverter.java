package nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.JoinGardenRequestDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.JoinGardenRequest;
import org.springframework.stereotype.Component;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class JoinGardenRequestConverter {

    private final GardenConverter gardenConverter;
    private final UserConverter userConverter;

    public JoinGardenRequestConverter(GardenConverter gardenConverter, UserConverter userConverter) {
        this.gardenConverter = gardenConverter;
        this.userConverter = userConverter;
    }

    public JoinGardenRequestDTO convertToRequestDTO(JoinGardenRequest joinGardenRequest) {
        JoinGardenRequestDTO joinGardenRequestDTO = new JoinGardenRequestDTO();
        joinGardenRequestDTO.setRequestId(joinGardenRequest.getRequestId());
        joinGardenRequestDTO.setStatus(joinGardenRequestDTO.getStatus());
        joinGardenRequestDTO.setGardenDTO(gardenConverter.convertToGardenDTO(joinGardenRequest.getGarden()));
        joinGardenRequestDTO.setUserDTO(userConverter.convertToUserDTO(joinGardenRequest.getUser()));
        return joinGardenRequestDTO;
    }

    public JoinGardenRequest convertFromRequestDTO(JoinGardenRequestDTO joinGardenRequestDTO) {
        JoinGardenRequest joinGardenRequest = new JoinGardenRequest();
        joinGardenRequest.setRequestId(joinGardenRequestDTO.getRequestId());
        joinGardenRequest.setStatus(joinGardenRequestDTO.getStatus());
        joinGardenRequest.setGarden(gardenConverter.convertFromGardenDTO(joinGardenRequestDTO.getGardenDTO()));
        joinGardenRequest.setUser(userConverter.convertFromUserDTO(joinGardenRequestDTO.getUserDTO()));
        return joinGardenRequest;
    }
}