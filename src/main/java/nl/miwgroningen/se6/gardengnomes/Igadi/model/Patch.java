package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.*;

/**
 * @author Tjerk Nagel
 *
 *
 *     This class is used to create patches, which are part of a garden. They are managed by one garden manager.
 */

@Entity
@IdClass(PatchId.class)
public class Patch {

    @Id
    @GeneratedValue
    @Column(name="gardenId")
    private Integer gardenId;

    @Id
    @GeneratedValue
    @Column(name="patchId")
    private Integer patchId;


}
