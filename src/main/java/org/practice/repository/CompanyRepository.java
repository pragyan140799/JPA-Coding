package org.practice.repository;

import org.practice.entity.Company;

import java.util.Optional;

public interface CompanyRepository {

    Optional<Company> saveORUpdate(Company company);
    Optional<Company> findCompanyById(Long id);
    void delete(Company company);
}
