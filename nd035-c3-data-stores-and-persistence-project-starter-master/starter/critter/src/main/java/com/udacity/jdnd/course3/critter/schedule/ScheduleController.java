package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        scheduleService.save(convertScheduleDTOToSchedule(scheduleDTO));
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId){
        List<Schedule> schedules = scheduleService.getSchedulesByPet(petService.findPet(petId));
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesByEmployee(employeeService.findEmployee(employeeId));
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Pet> pets = customerService.findCustomerById(customerId).getPets();
        List<Schedule> schedules = new ArrayList<>();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Pet pet : pets) {
            schedules.addAll(scheduleService.getSchedulesByPet(pet));
        }
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        List<Pet> pets = schedule.getPets();
        List<Employee> employees = schedule.getEmployees();
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if(pets != null){
            List<Long> petIds = new ArrayList<Long>();
            for (Pet pet : pets) {
                petIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(petIds);
        }

        if(employees != null){
            List<Long> employeeIds = new ArrayList<Long>();
            for (Employee employee : employees) {
                employeeIds.add(employee.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if(petIds != null){
            List<Pet> pets = new ArrayList<Pet>();
            for (Long petId : petIds) {
                pets.add(petService.findPet(petId));
            }
            schedule.setPets(pets);
        }

        if(employeeIds != null){
            List<Employee> employees = new ArrayList<Employee>();
            for (Long employeeId : employeeIds) {
                employees.add(employeeService.findEmployee(employeeId));
            }
            schedule.setEmployees(employees);
        }

        return schedule;
    }
}
