package nl.miwgroningen.se6.gardengnomes.Igadi.service;

import nl.miwgroningen.se6.gardengnomes.Igadi.dto.JoinGardenRequestDTO;
import nl.miwgroningen.se6.gardengnomes.Igadi.repository.JoinGardenRequestRepository;
import nl.miwgroningen.se6.gardengnomes.Igadi.service.Converter.JoinGardenRequestConverter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void saveRequest(JoinGardenRequestDTO joinGardenRequestDTO) {
        joinGardenRequestRepository.save(joinGardenRequestConverter.convertFromRequestDTO(joinGardenRequestDTO));
    }

    public List<JoinGardenRequestDTO> findAllRequestsByUserId(int userId) {
        return joinGardenRequestRepository.findAllByuser_userId(userId).stream()
                .map(joinGardenRequestConverter::convertToRequestDTO).collect(Collectors.toList());
    }

    public List<JoinGardenRequestDTO> findAllRequestsByGardenId(int gardenId) {
        return joinGardenRequestRepository.findAllBygarden_gardenId(gardenId).stream()
                .map(joinGardenRequestConverter::convertToRequestDTO).collect(Collectors.toList());
    }

    public JoinGardenRequestDTO getRequestById(int requestId) {
        return joinGardenRequestConverter.convertToRequestDTO(joinGardenRequestRepository.getById(requestId));
    }

    public void deleteRequest(JoinGardenRequestDTO joinGardenRequestDTO) {
            joinGardenRequestRepository.delete(joinGardenRequestConverter
                    .convertFromRequestDTO(joinGardenRequestDTO));
    }

    public List<JoinGardenRequestDTO> getAllRequests() {
        return joinGardenRequestRepository.findAll().stream().map(joinGardenRequestConverter::convertToRequestDTO)
                .collect(Collectors.toList());
    }

    public void checkIfRequestsRemain(int gardenId, int userId) {
        List<JoinGardenRequestDTO> requests = findAllRequestsByGardenId(gardenId);
        for(JoinGardenRequestDTO requestDTO : requests) {
            if(requestDTO.getUserDTO().getUserId() == userId) {
                joinGardenRequestRepository.delete(joinGardenRequestConverter.convertFromRequestDTO(requestDTO));
            }
        }
    }
}