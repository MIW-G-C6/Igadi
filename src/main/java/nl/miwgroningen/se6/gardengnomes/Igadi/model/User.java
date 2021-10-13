package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.*;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This user class is used for the creation of accounts. When the user creates an account, they are automatically
 *     saved with the role 'gardener'. They can get the role 'garden manager' when they create a garden.
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name="userId")
    private Integer userId;

    @Column(unique = true, name = "userName", nullable = false)
    private String userName;

    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    @Column(name = "userRole", nullable = false)
    private String userRole = "gardener";

    @ManyToOne(optional = true)
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

    public String getUserPassword() {
        return userPassword;
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
