package ru.practicum.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;

import java.util.Comparator;

@Service
public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event event1, Event event2) {
        return event1.getViews().compareTo(event2.getViews());
    }
}