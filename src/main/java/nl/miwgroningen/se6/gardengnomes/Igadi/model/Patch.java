package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;

/**
 * @author Tjerk Nagel
 *
 *
 *     This class is used to create patches, which are part of a garden. They are managed by one garden manager.
 */

@Entity
public class Patch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patchId")
    private Integer patchId;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "garden_gardenId")
    private Garden garden;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "crop", nullable = true)
    private String crop;

    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String cropInGarden() {
        if(!this.crop.equals("")) {
            String crop = this.crop.substring(0, 1).toUpperCase() + this.crop.substring(1).toLowerCase();
            String taskTitle = crop + " in " + garden.getGardenName();
            return taskTitle;
        } else {
            String taskTitle = showWhatIsGrowing();
            return taskTitle;
        }
    }

    public String showWhatIsGrowing() {
        if(this.crop.equals("")) {
            return "Empty";
        } else {
            return this.crop;
        }
    }
}