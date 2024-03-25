package com.ceica.firstspringcomplete.repository;

import com.ceica.firstspringcomplete.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

}
