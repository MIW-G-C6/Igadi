package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

public class UserDTO {

    private Integer userId;
    private String userName;
    private String userPassword;
    private String userRole;
    private Garden garden;

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

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }
}