package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tjerk Nagel
 *
 *
 *     This class is used to create patches, which are part of a garden. They are managed by one garden manager.
 */

@Entity
public class Patch<Patchid> {


    // Fields
    @EmbeddedId
    @GeneratedValue
    @Column(name="patchId")
    private PatchId patchId;

    @Embeddable
    class PatchId implements Serializable {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(gardenId = "gardenId")
        private Garden garden;
        private Integer patchId;
    }
}
