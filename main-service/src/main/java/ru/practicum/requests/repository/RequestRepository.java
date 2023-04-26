package ru.practicum.requests.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.requests.model.ParticipationRequest;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Integer> {

    Collection<ParticipationRequest> getParticipationRequestByRequesterIs(Integer userId, PageRequest pageRequest);

    Collection<ParticipationRequest> getParticipationRequestsByRequesterIsAndEventIs(Integer userId, Integer eventId);

    Boolean existsByEventIsAndRequesterIs(Integer eventId, Integer requesterId);
}
