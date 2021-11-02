package nl.miwgroningen.se6.gardengnomes.Igadi.model;

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
    @JoinColumn(name = "garden_gardenId")
    private Garden garden;

    @ManyToOne
    @JoinColumn(name = "user_userId")
    private User user;

    private String role;
}
