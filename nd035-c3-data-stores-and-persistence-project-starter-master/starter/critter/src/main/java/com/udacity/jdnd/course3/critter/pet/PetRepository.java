package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<List<Pet>> getPetsByCustomer_Id(Long customer_id);
}
