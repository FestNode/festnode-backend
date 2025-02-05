package com.festnode.festnode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.festnode.festnode.model.AppUser;
import com.festnode.festnode.model.Event;
import com.festnode.festnode.model.EventCategories;
import com.festnode.festnode.model.Image;
import com.festnode.festnode.service.CloudinaryService;
import com.festnode.festnode.service.EventService;
import com.festnode.festnode.service.ImageService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/public")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(
            @RequestPart("event") String eventJson,
            @RequestPart MultipartFile poster,
            @RequestParam List<String> coordinators) throws IOException {

        System.out.println("event : " + eventJson);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // To handle Java 8 date/time types
        Event event = objectMapper.readValue(eventJson, Event.class);

        System.out.println(coordinators);
        List<Long> coordinatorIdList = new ArrayList<>();
        for(String id : coordinators){
            Long coordinatorLong = Long.parseLong(id);
            coordinatorIdList.add(coordinatorLong);
        }


        BufferedImage buffImg = ImageIO.read(poster.getInputStream());
        Map result = cloudinaryService.upload(poster);
        Image image = new Image((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));

        imageService.saveImg(image);

        Event savedEvent = eventService.addEvent(event, image, coordinatorIdList);

        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @PostMapping("/registerEvent")
    public ResponseEntity<String> registerEvent(@RequestParam Long userId, @RequestParam Long eventId){
        String res = eventService.registerEvent(userId, eventId);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/getEvent")
    public ResponseEntity<Event> getEvent(@RequestParam Long eventId){
      return new ResponseEntity<>(eventService.getEvent(eventId), HttpStatus.OK);
    }

    @GetMapping("/getAllEventCategory")
    public ResponseEntity<Set<String>> getEventCategories(){
        return new ResponseEntity<>(EventCategories.getEventCategories(), HttpStatus.OK);
    }

    @GetMapping("/getCategoryWiseEvents")
    public ResponseEntity<List<Event>> getCategoryWiseEvents(@RequestParam EventCategories category){
        List<Event> categoryWiseEventlist = eventService.findCategoryWiseEvents(category);
        return new ResponseEntity<>(categoryWiseEventlist, HttpStatus.OK);
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> allEvents = eventService.findAllEvents();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }
}
