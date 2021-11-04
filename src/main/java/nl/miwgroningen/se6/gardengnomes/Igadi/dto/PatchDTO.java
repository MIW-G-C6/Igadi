package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

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
        if(!this.crop.equals("")) {
            String crop = this.crop.substring(0, 1).toUpperCase() + this.crop.substring(1).toLowerCase();
            String taskTitle = crop + " in " + garden.getGardenName();
            return taskTitle;
        } else {
            String taskTitle = showWhatIsGrowing();
            return taskTitle;
        }
    }

    public String showWhatIsGrowing() {
        if(this.crop.equals("")) {
            return "Nothing is growing here at this moment";
        } else {
            return this.crop;
        }
    }
}