package com.festnode.festnode.util;

import com.festnode.festnode.model.Control;
import com.festnode.festnode.repository.ControlRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ControlInitializer {

    @Bean
    CommandLineRunner initializeControl(ControlRepository controlRepository) {
        return args -> {
            if (controlRepository.findById(1L).isEmpty()) {
                Control control = new Control();
                controlRepository.save(control);
                System.out.println("Control entity initialized with ID = 1 and active = null.");
            }
        };
    }
}
