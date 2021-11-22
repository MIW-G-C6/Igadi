package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */
public class JoinGardenRequestDTO {

    private Integer requestId;
    private GardenDTO gardenDTO;
    private UserDTO userDTO;
    private String status;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public GardenDTO getGardenDTO() {
        return gardenDTO;
    }

    public void setGardenDTO(GardenDTO gardenDTO) {
        this.gardenDTO = gardenDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}