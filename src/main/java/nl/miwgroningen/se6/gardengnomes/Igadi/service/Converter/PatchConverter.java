package nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.PatchDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.model.Patch;
import org.springframework.stereotype.Component;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class PatchConverter {

    private final GardenConverter gardenConverter;

    public PatchConverter(GardenConverter gardenConverter) {
        this.gardenConverter = gardenConverter;
    }

    public PatchDTO convertToPatchDTO(Patch patch) {
        PatchDTO patchDTO = new PatchDTO();
        patchDTO.setPatchId(patch.getPatchId());
        patchDTO.setName(patch.getName());
        patchDTO.setCrop(patch.getCrop());
        patchDTO.setGardenDTO(gardenConverter.convertToGardenDTO(patch.getGarden()));
        return patchDTO;
    }

    public Patch convertFromPatchDTO(PatchDTO patchDTO) {
        Patch patch  = new Patch();
        patch.setPatchId(patchDTO.getPatchId());
        patch.setName(patchDTO.getName());
        patch.setCrop(patchDTO.getCrop());
        patch.setGarden(gardenConverter.convertFromGardenDTO(patchDTO.getGardenDTO()));
        return patch;
    }
}
