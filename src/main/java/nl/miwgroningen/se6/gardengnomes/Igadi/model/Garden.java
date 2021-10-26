package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.*;

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

    // TODO add a list of patches here, instead of refering to Garden from Patch

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
}
