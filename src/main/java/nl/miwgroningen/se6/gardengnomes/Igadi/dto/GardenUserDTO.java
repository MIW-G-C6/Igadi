package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

import nl.miwgroningen.se6.gardengnomes.Igadi.model.Garden;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */
public class GardenUserDTO {

    private Integer gardenUserId;

    private GardenDTO gardenDTO;

    private User user;

    private String role;

    public Integer getGardenUserId() {
        return gardenUserId;
    }

    public void setGardenUserId(Integer gardenUserId) {
        this.gardenUserId = gardenUserId;
    }

    public GardenDTO getGardenDTO() {
        return gardenDTO;
    }

    public void setGardenDTO(GardenDTO gardenDTO) {
        this.gardenDTO = gardenDTO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
