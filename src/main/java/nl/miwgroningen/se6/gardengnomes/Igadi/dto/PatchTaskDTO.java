package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This taskDTO is designed for tasks that are connected to a specific patch, which in turn is connected to a
 *     specific garden.
 */

public class PatchTaskDTO extends TaskDTO {

    private PatchDTO patchDTO;

    public void setPatchDTO(PatchDTO patchDTO) {
        this.patchDTO = patchDTO;
    }

    public PatchDTO getPatchDTO() {
        return patchDTO;
    }
}