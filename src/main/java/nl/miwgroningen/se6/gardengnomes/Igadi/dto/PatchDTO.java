package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Tjerk Nagel
 * doel:
 */

public class PatchDTO {

    private Integer patchId;
    private GardenDTO gardenDTO;

    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }

    public GardenDTO getGardenDTO() {
        return gardenDTO;
    }

    public void setGardenDTO(GardenDTO gardenDTO) {
        this.gardenDTO = gardenDTO;
    }
}
