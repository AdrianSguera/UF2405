package com.ceica.firstspringcomplete.repository;

import com.ceica.firstspringcomplete.model.TimeBand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeBandRepository extends JpaRepository<TimeBand, Integer> {

    TimeBand findByName(String name);
}
