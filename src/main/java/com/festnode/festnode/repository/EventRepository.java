package com.festnode.festnode.repository;
import com.festnode.festnode.model.Event;
import com.festnode.festnode.model.EventCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByEventCategory(EventCategories category);

    List<Event> findByIsActiveTrueAndIsCompletedFalse();
}
