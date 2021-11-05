package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 *      This is an association table for Garden and User. It tracks which garden is connected to which user, and which role
 *      the user has in that connection.
 */

@Entity
public class GardenUser {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="gardenUserId", unique = true)
    private Integer gardenUserId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "garden_gardenId")
    private Garden garden;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_userId")
    private User user;

    private String role;

    public Integer getGardenUserId() {
        return gardenUserId;
    }

    public void setGardenUserId(Integer gardenUserId) {
        this.gardenUserId = gardenUserId;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
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
