package org.practice.repositoryImpl;

import jakarta.persistence.EntityManager;
import org.practice.entity.Salary;
import org.practice.repository.SalaryRepository;

import java.util.Optional;

public class SalaryRepositoryImpl implements SalaryRepository {

    private EntityManager entityManager;

    public SalaryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Salary> saveOrUpdate(Salary salary) {
        try {
            entityManager.getTransaction().begin();
            if (salary.getId() == null) {
                entityManager.persist(salary);
            } else {
                entityManager.merge(salary);
            }
            entityManager.getTransaction().commit();

            return Optional.of(salary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Salary> findSalaryById(Long id) {
        Salary salary = entityManager.find(Salary.class, id);
        return salary == null ? Optional.empty() : Optional.of(salary);
    }

    @Override
    public void delete(Salary salary) {
        try {
            entityManager.getTransaction().begin();
            if (salary.getId() == null) {
                entityManager.persist(salary);
            } else {
                entityManager.remove(salary);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
