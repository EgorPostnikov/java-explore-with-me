package ru.practicum.requests;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.Event;
import ru.practicum.user.User;

import java.util.Collection;
import java.util.HashSet;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Integer> {

    Collection<ParticipationRequest> getParticipationRequestByRequesterIs(Integer userId, PageRequest pageRequest);

    Collection<ParticipationRequest> getParticipationRequestsByRequesterIsAndEventIs(Integer userId, Integer eventId);

    Boolean existsByEventIsAndRequesterIs(Integer eventId, Integer requesterId);
}
