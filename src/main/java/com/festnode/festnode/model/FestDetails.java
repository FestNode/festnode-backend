package com.festnode.festnode.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FestDetails {
    @Id
    private Long id;
    private String festName;
    private String festDescription;
    @OneToOne
    @JoinColumn(name = "logo")
    private Image logo;
}


