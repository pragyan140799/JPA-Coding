package org.practice.repository;

import org.practice.entity.Salary;

import java.util.Optional;

public interface SalaryRepository {

    Optional<Salary> saveOrUpdate(Salary salary);

    Optional<Salary> findSalaryById(Long id);

    void delete(Salary salary);
}
