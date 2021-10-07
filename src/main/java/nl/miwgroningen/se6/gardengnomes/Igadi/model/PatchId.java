package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import java.io.Serializable;

/**
 * @author Tjerk Nagel
 * doel:
 */

public class PatchId implements Serializable {

    private Integer gardenId;
    private Integer patchId;

    public PatchId(Integer gardenId, Integer patchId) {
        this.gardenId = gardenId;
        this.patchId = patchId;
    }
}
