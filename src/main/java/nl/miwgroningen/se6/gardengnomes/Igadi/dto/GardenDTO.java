package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Tjerk Nagel
 * doel:
 */

public class GardenDTO {

    private Integer gardenId;
    private String gardenName;
    private int amountOfUsers;
    private String location;

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
}
