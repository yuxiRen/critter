package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }
    public Pet findPet(Long id) {
        return petRepository.findById(id).orElse(null);
    }
    public List<Pet> findPetsByOwner(Long id) {
        return petRepository.getPetsByCustomer_Id(id).orElse(null);
    }
}
