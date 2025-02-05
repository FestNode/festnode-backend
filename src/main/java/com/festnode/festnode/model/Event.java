package com.festnode.festnode.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    @OneToOne
    @JoinColumn(name = "eventPoster")
    private Image eventPoster;

    private String eventName;

    private String eventDescription;

    private Date eventDate;

    @ManyToMany
    private List<AppUser> participants;

    @OneToMany
    private List<AppUser> coordinators;

    @Enumerated(EnumType.STRING)
    private EventCategories eventCategory;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("isCompleted")
    private boolean isCompleted;
}
