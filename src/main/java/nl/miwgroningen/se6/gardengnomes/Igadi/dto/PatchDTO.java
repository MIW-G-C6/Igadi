package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Lukas de Ruiter
 *
 * This is the DTO for patches. The cropInGarden method has been added to display text to the front-end when viewing
 * what is growing in the patch. If this is empty, it returns "empty" as a string.
 */

public class PatchDTO {

    private Integer patchId;
    private GardenDTO garden;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            String taskTitle = crop;
            return taskTitle;
        } else {
            String taskTitle = showWhatIsGrowing();
            return taskTitle;
        }
    }

    public String showWhatIsGrowing() {
        if(this.crop.equals("")) {
            return "Empty";
        } else {
            return this.crop;
        }
    }
}