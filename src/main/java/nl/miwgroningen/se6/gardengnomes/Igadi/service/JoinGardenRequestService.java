package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.JoinGardenRequestDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.JoinGardenRequestRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.JoinGardenRequestConverter;
import org.springframework.stereotype.Service;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Service
public class JoinGardenRequestService {

    private final JoinGardenRequestRepository joinGardenRequestRepository;
    private final JoinGardenRequestConverter joinGardenRequestConverter;

    public JoinGardenRequestService(JoinGardenRequestRepository joinGardenRequestRepository,
                                    JoinGardenRequestConverter joinGardenRequestConverter) {
        this.joinGardenRequestRepository = joinGardenRequestRepository;
        this.joinGardenRequestConverter = joinGardenRequestConverter;
    }

    public void saveRequest(JoinGardenRequestDTO joinGardenRequestDTO) {
        joinGardenRequestRepository.save(joinGardenRequestConverter.convertFromRequestDTO(joinGardenRequestDTO));
    }
}