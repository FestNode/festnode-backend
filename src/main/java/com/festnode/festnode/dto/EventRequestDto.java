package com.festnode.festnode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDto {
    private String eventName;
    private String eventDescription;
    private Date eventDate;
    private String eventCategory;
    private boolean isActive;
    private boolean isCompleted;

}
