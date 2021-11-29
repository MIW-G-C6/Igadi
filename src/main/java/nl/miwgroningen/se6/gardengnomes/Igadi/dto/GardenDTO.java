package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Tjerk Nagel
 * doel:
 */

public class GardenDTO {

    private Integer gardenId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String gardenName;
    private int amountOfUsers;
    private String location;
    private boolean gardenManagerStatus = false;

    public Integer getGardenId() {
        return gardenId;
    }

    public void setGardenId(Integer gardenId) {
        this.gardenId = gardenId;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public int getAmountOfUsers() {
        return amountOfUsers;
    }

    public void setAmountOfUsers(int amountOfUsers) {
        this.amountOfUsers = amountOfUsers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isGardenManagerStatus() {
        return gardenManagerStatus;
    }

    public void setGardenManagerStatus(boolean gardenManagerStatus) {
        this.gardenManagerStatus = gardenManagerStatus;
    }
}