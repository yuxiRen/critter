package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = customerService.findCustomerById(petDTO.getOwnerId());
        Pet pet = convertPetDTOToPet(petDTO);
        pet.setCustomer(customer);
        Pet newPet = petService.save(pet);
        if (customer != null) {
            customer.addPet(newPet);
        }
        return convertPetToPetDTO(newPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findPet(petId);
        if (pet != null) {
            return convertPetToPetDTO(pet);
        } else {
            return null;
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet pet : pets) {
            petDTOs.add(convertPetToPetDTO(pet));
        }
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.findPetsByOwner(ownerId);
        List<PetDTO> petDTOs = new ArrayList<PetDTO>();
        for (Pet pet : pets) {
            petDTOs.add(convertPetToPetDTO(pet));
        }
        return petDTOs;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
