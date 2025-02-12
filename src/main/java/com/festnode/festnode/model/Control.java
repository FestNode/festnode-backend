package com.festnode.festnode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Control {

    @Id
    private final long id = 1L; // Enforcing a constant ID

    private boolean active; // Corrected field name

    @PrePersist
    private void prePersist() {
        if (this.id != 1L) {
            throw new IllegalStateException("Control entity must always have ID = 1.");
        }
    }
}

