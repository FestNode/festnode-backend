package com.festnode.festnode.model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum EventCategories {
    COMPETITION("COMPETITION"),
    CULTURAL_EVENT("CULTURAL_EVENT"),
    TECHNICAL_EVENT("TECHNICAL_EVENT"),
    WORKSHOP("WORKSHOP"),
    GUEST_LECTURE("GUEST_LECTURE"),
    EXHIBITION("EXHIBITION");


    private final String categories;
EventCategories(String categories) {
    this.categories = categories;
}

public static Set<String> getEventCategories(){
    Set<String> eventCategories=new HashSet<>(0);

    for(EventCategories category: EventCategories.values()) {
        eventCategories.add(category.categories);
    }
    return eventCategories;
}
}
