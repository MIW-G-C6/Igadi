package nl.miwgroningen.se6.gardengnomes.Igadi.testing.unittesting.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *
 *     This class is used to create gardens, which are the main objects of the application. They contain patches and are
 *     managed by one garden manager.
 */

@Entity
public class Garden {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gardenId")
    private Integer gardenId;

    @Column(unique = true, nullable = false, name="gardenName")
    private String gardenName;

    @OneToMany(mappedBy = "garden")
    private List<GardenTask> gardenTasks;

    @OneToMany(mappedBy = "garden")
    private List<Patch> patches;

    @OneToMany(mappedBy = "garden")
    private List<GardenUser> gardenUsers;

    @Column(name = "location", nullable = false)
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<GardenTask> getGardenTasks() {
        return gardenTasks;
    }

    public void setGardenTasks(List<GardenTask> gardenTasks) {
        this.gardenTasks = gardenTasks;
    }

    public List<Patch> getPatches() {
        return patches;
    }

    public void setPatches(List<Patch> patches) {
        this.patches = patches;
    }

    public List<GardenUser> getGardenUsers() {
        return gardenUsers;
    }

    public void setGardenUsers(List<GardenUser> gardenUsers) {
        this.gardenUsers = gardenUsers;
    }
}

