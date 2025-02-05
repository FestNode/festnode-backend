package com.festnode.festnode.service;
import com.festnode.festnode.exception.EntityNotFoundException;
import com.festnode.festnode.exception.NotPermittedException;
import com.festnode.festnode.model.*;
import com.festnode.festnode.repository.AppUserRepository;
import com.festnode.festnode.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private AppUserRepository userRepo;
    public Event addEvent(Event event, Image poster, List<Long> coordinators) {
        Event newEvent = new Event();
        newEvent.setEventName(event.getEventName());
        newEvent.setEventDescription(event.getEventDescription());
        newEvent.setEventCategory(event.getEventCategory());
        newEvent.setEventDate(event.getEventDate());
        newEvent.setParticipants(event.getParticipants());
        newEvent.setCoordinators(event.getCoordinators());
        newEvent.setActive(event.isActive());
        newEvent.setCompleted(event.isCompleted());
        newEvent.setEventPoster(poster);
        if(!coordinators.isEmpty()){
            List<AppUser> coordinatorsList = new ArrayList<>();
            for(Long id : coordinators){
                AppUser coordinator = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found!"));
                if(coordinator.getRole() == UserRole.USER){
                    coordinator.setRole(UserRole.COORDINATOR);
                    coordinator.setDesignation("coordinator");
                    userRepo.save(coordinator);
                    coordinatorsList.add(coordinator);
                }else {
                    throw new NotPermittedException("Coordinators are not allowed! Try again with other coordinators");
                }

            }
            newEvent.setCoordinators(coordinatorsList);
        }
        return eventRepo.save(newEvent);
    }

    public String registerEvent(Long userId, Long eventId) {
        String res = "";
        AppUser user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found!"));
        if(user.getRole() != UserRole.USER){
            res = "This user role is not authorized to register for an event!";
            return res;
        }else {
            Event event = eventRepo.findById(eventId).orElse(null);
            if(event != null){
                if(!event.isActive() && event.isCompleted()){
                    res = "The event is currently not available for registration! Please try again later.";
                    return res;
                }else{
                    event.getParticipants().add(user);
                    eventRepo.save(event);
                    res = "You have successfully registered for the event : " + event.getEventName();
                    return res;
                }
            }else {
                res = "Event Not Found!";
                return res;
            }

        }
    }

    public Event getEvent(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event Not Found!"));
    }

    public List<Event> findCategoryWiseEvents(EventCategories category) {
        return eventRepo.findAllByEventCategory(category);
    }

    public List<Event> findAllEvents() {
        List<Event> events = eventRepo.findByIsActiveTrueAndIsCompletedFalse();

        // Sort events alphabetically by eventName
        List<Event> sortedEvents = events.stream()
                .sorted(Comparator.comparing(Event::getEventName))
                .collect(Collectors.toList());

        return sortedEvents;
    }
}
