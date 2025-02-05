package com.festnode.festnode.repository;

import com.festnode.festnode.model.FestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestDetailsRepository extends JpaRepository<FestDetails, Long> {
}
