package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    public boolean setDaysAvailable(Set<DayOfWeek> availableDays, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            return false;
        }
        employee.setDaysAvailable(availableDays);
        employeeRepository.save(employee);
        return true;
    }
}
