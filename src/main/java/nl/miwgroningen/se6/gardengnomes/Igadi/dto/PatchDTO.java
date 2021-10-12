package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Tjerk Nagel
 * doel:
 */

public class PatchDTO {

    private Integer patchId;
    private GardenDTO garden;

    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }

    public GardenDTO getGardenDTO() {
        return garden;
    }

    public void setGardenDTO(GardenDTO gardenDTO) {
        this.garden = gardenDTO;
    }
}
