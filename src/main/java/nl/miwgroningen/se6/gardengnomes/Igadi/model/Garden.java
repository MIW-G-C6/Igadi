package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    // The 'mappedBy = "garden"' attribute specifies that the 'private Garden garden;' field in Patch owns the
    // relationship (i.e. contains the foreign key for the query to find all patches for a Garden.)
    // (https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne)

//    @OneToMany(mappedBy = "garden")
//    private List<Patch> patches;

}

