package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This DTO only targets tasks that are coupled to a garden.
 */
public class GardenTaskDTO extends TaskDTO {

    private GardenDTO gardenDTO;

    public GardenDTO getGardenDTO() {
        return gardenDTO;
    }

    public void setGardenDTO(GardenDTO gardenDTO) {
        this.gardenDTO = gardenDTO;
    }
}
