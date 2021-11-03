package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

import org.apache.catalina.valves.rewrite.InternalRewriteMap;

import java.util.Locale;

/**
 * @author Tjerk Nagel
 * doel:
 */

public class PatchDTO {

    private Integer patchId;
    private GardenDTO garden;
    private String crop;

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

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String cropInGarden() {
        String crop = this.crop.substring(0,1).toUpperCase() + this.crop.substring(1).toLowerCase();
        String taskTitle = crop + " in " + garden.getGardenName();
        return taskTitle;
    }
}
