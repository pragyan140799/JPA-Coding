package org.practice.repositoryImpl;

import jakarta.persistence.EntityManager;
import org.practice.entity.Company;
import org.practice.repository.CompanyRepository;

import java.util.Optional;

public class CompanyRepositoryImpl implements CompanyRepository {

    private EntityManager entityManager;

    public CompanyRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Company> saveORUpdate(Company company) {
        try {
            entityManager.getTransaction().begin();
            if (company.getId() == null) {
                entityManager.persist(company);
            } else {
                entityManager.merge(company);
            }
            entityManager.getTransaction().commit();

            return Optional.of(company);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Company> findCompanyById(Long id) {
        Company company = entityManager.find(Company.class, id);
        return company == null ? Optional.empty() : Optional.of(company);
    }

    @Override
    public void delete(Company company) {
        try {
            entityManager.getTransaction().begin();
            if (company.getId() == null) {
                entityManager.persist(company);
            } else {
                entityManager.remove(company);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
