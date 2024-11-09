package org.practice.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.practice.entity.Employee;
import org.practice.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

//implement CRUD operations for Employee
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EntityManager entityManager;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Employee> save(Employee employee) {

        try {
            entityManager.getTransaction().begin();
            if (employee.getId() == null) {
                entityManager.persist(employee);
            } else {
                entityManager.merge(employee);
            }
            entityManager.getTransaction().commit();

            return Optional.of(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee == null ? Optional.empty() : Optional.of(employee);
    }

    @Override
    public List<Employee> getEmployeesByExperience(Integer yearsOfExperience) {

        Query jpqlQuery = entityManager.createQuery("SELECT e FROM Employee as e WHERE e.yearsOfExperience > " + ":yearsOfExperience");
        jpqlQuery.setParameter("yearsOfExperience", yearsOfExperience);
        return jpqlQuery.getResultList();
    }

    @Override
    public List<Employee> getEmployeesByExperienceNativeQuery(Integer yearsOfExperience) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM employees  WHERE yearsOfexperience >:yearsOfExperience ORDER BY last_name", Employee.class);
        nativeQuery.setParameter("yearsOfExperience",yearsOfExperience);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<Employee> update(Employee employee) {
        return save(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        try {
            entityManager.getTransaction().begin();
            if (employee.getId() == null) {
                entityManager.persist(employee);
            } else {
                entityManager.remove(employee);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
