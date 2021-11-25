package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

public class UserDTO {

    private Integer userId;
    private String userName;
    private String userEmail;
    private String userRole;
    private String password1;
    private String password2;
    private GardenDTO gardenDTO;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public GardenDTO getGardenDTO() {
        return gardenDTO;
    }

    public void setGarden(GardenDTO gardenDTO) {
        this.gardenDTO = gardenDTO;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}