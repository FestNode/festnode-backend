package com.festnode.festnode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "imgId")
    private Long imgId;

    private String imageName;

    private String imageUrl;

    private String imageId;

    public Image(String imageName, String imageUrl, String imageId) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
    }
}



