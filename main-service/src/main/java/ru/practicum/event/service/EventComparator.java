package ru.practicum.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;

import java.util.Comparator;

@Service
public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event event1, Event event2) {
        if (event1.getViews() > event2.getViews()) {
            return 1;
        } else if (event1.getViews() < event2.getViews()) {
            return -1;
        } else {
            return 0;
        }
    }
}