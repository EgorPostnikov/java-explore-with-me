package ru.practicum.requests;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private static final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);
    UserRepository userRepository;
    RequestRepository repository;

    @Override
    public Collection<ParticipationRequestDto> getUserRequests(Integer userId, PageRequest pageRequest) {
        Collection<ParticipationRequest> entities = repository.getParticipationRequestByRequesterIs(userId, pageRequest);
        log.info("Requests for user id #{} get, events qty is {}", userId, entities.size());
        if (entities.isEmpty()) {
            entities = Collections.emptyList();
        }
        return RequestMapper.INSTANCE.toParticipationRequestDtos(entities);
    }


    @Override
    public ParticipationRequestDto cancellRequest(Integer userId, Integer requestId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));
        ParticipationRequest entity = repository.findById(requestId).
                orElseThrow(() -> new NoSuchElementException("Request with id="+requestId+" was not found"));
        if(entity.getRequester()==userId){
            entity.setStatus("CANCELED");
        } else {
            throw new RuntimeException("User have not roots");
        }

        ParticipationRequest createdEntity = repository.save(entity);
        log.info("Request with id #{} cancelled", createdEntity.getId());
        return RequestMapper.INSTANCE.toParticipationRequestDto(createdEntity);
    }

    @Override
    public ParticipationRequestDto createRequest(ParticipationRequestDto request) {
        User user = userRepository.findById(request.getRequester()).
                orElseThrow(() -> new RuntimeException("User not found"));
        ParticipationRequest entity = RequestMapper.INSTANCE.toParticipationRequest(request);
        ParticipationRequest createdEntity = repository.save(entity);
        log.info("Request with id #{} saved", createdEntity.getId());
        return RequestMapper.INSTANCE.toParticipationRequestDto(createdEntity);
    }
}
