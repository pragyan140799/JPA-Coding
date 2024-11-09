package org.practice.repository;

import org.practice.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> save(Employee employee);

    Optional<Employee> getEmployeeById(Long id);

    List<Employee> getEmployeesByExperience(Integer yearsOfExperience);

    List<Employee> getEmployeesByExperienceNativeQuery(Integer yearsOfExperience);

    Optional<Employee> update(Employee employee);

    void deleteEmployee(Employee employee);


}
