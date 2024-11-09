package org.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.practice.entity.Company;
import org.practice.entity.Employee;
import org.practice.entity.Salary;
import org.practice.repositoryImpl.CompanyRepositoryImpl;
import org.practice.repositoryImpl.EmployeeRepositoryImpl;
import org.practice.repositoryImpl.SalaryRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Main {

   /* @PersistenceContext
    EntityManager entityManager;*/

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl(entityManager);
        CompanyRepositoryImpl companyRepository = new CompanyRepositoryImpl(entityManager);
        SalaryRepositoryImpl salaryRepository = new SalaryRepositoryImpl(entityManager);

        System.out.println("Don't forget to launch Postgres before running this code!");

        //test CRUD operations on Employee
        Employee employee1 = new Employee();
        employee1.setFirstName("Pragyan");
        employee1.setLastName("Prandey");
        employee1.setYearsOfExperience(2);
        Employee employee2 = new Employee();
        employee2.setFirstName("Jhon");
        employee2.setLastName("Doe");
        employee2.setYearsOfExperience(5);

        Optional<Employee> savedEmployee = employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        savedEmployee.ifPresent((e) -> System.out.println("Saved Employee is " + e.getFirstName() + " " + e.getLastName()));

        Optional<Employee> foundEmployee = employeeRepository.getEmployeeById(1L);
        foundEmployee.ifPresent((e) -> System.out.println("Found employee is " + e.getFirstName() + " " + e.getLastName()));

        employee1.setLastName("Pandey");
        Optional<Employee> updatedEmployee = employeeRepository.update(employee1);
        updatedEmployee.ifPresent((e) -> System.out.println("Employee with updated last name " + e.getFirstName() + " " + e.getLastName()));

        employeeRepository.deleteEmployee(employee2);

        //CRUD operations on Company
        Company company1 = new Company("Gurgaon", "Haryana", "122001", "India");
        Company company2 = new Company("Mumbai", "Maharashtra", "100409", "India");

        Optional<Company> savedCompany = companyRepository.saveORUpdate(company1);
        companyRepository.saveORUpdate(company2);
        savedCompany.ifPresent((c) -> System.out.println("Saved City: " + c.getCity() + " Pincode: " + c.getZip()));

        Optional<Company> foundCompany = companyRepository.findCompanyById(savedCompany.get().getId());
        foundCompany.ifPresent((c) -> System.out.println("Found City: " + c.getCity() + " Pincode: " + c.getZip()));

        company1.setZip("122003");
        Optional<Company> updatedCompany = companyRepository.saveORUpdate(company1);
        updatedCompany.ifPresent((c) -> System.out.println("Updated City: " + c.getCity() + " Pincode: " + c.getZip()));

        companyRepository.delete(company2);

        //CRUD operations on Salary
        Salary salary1 = new Salary(company2, "A1", true, 5.0, 45000.0, 46000.0, "Junior Software Engineer");
        Salary salary2 = new Salary(company2, "A2", true, 5.0, 45000.0, 60000.0, "Software Engineer");

        Optional<Salary> savedSalary = salaryRepository.saveOrUpdate(salary1);
        salaryRepository.saveOrUpdate(salary2);
        savedSalary.ifPresent((s) -> System.out.println("Saved Salary: " + s.toString()));

        Optional<Salary> foundSalary = salaryRepository.findSalaryById(savedSalary.get().getId());
        foundSalary.ifPresent((s) -> System.out.println("Found Salary: " + s.toString()));

        salary1.setTitle("Software Engineer");
        Optional<Salary> updatedSalary = salaryRepository.saveOrUpdate(salary1);
        updatedSalary.ifPresent((s) -> System.out.println("Updated Salary: " + s.toString()));

        salaryRepository.delete(salary2);

        //demo of oneToMany relationship between employee and salary
        Salary historicSalary1 = new Salary(40000.0, false);
        Salary historicSalary2 = new Salary(50000.0, true);
        List<Salary> salaryList = new ArrayList<>();
        salaryList.add(salary1);
        salaryList.add(historicSalary1);
        salaryList.add(historicSalary2);
        employee1.setSalaryList(salaryList);
        salaryRepository.saveOrUpdate(historicSalary1);
        salaryRepository.saveOrUpdate(historicSalary2);
        employeeRepository.update(employee1);

        //demo of manyToMany
        Employee employee3 = new Employee("Jhon", "Doe", 10);
        Company company3 = new Company("EPAM", "USA");
        Company company4 = new Company("HDFC", "India");
        List<Company> companyList = new ArrayList<>();
        companyList.add(company3);
        companyList.add(company4);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee3);
        companyRepository.saveORUpdate(company3);
        companyRepository.saveORUpdate(company4);
        employeeRepository.save(employee3);
        employee3.setCompanies(companyList);
        company1.setEmployees(employeeList);
        companyRepository.saveORUpdate(company1);
        employeeRepository.update(employee3);

        //demo jpql query
        List<Employee> employeesWithExperinceGreaterThan1 = employeeRepository.getEmployeesByExperience(1);
        if (employeesWithExperinceGreaterThan1 != null && !employeesWithExperinceGreaterThan1.isEmpty()) {
            for (Employee e : employeesWithExperinceGreaterThan1) {
                System.out.println(e.toString());
            }
        }

        //demo native query
        employeeRepository.getEmployeesByExperienceNativeQuery(1).stream().forEach(e -> System.out.println(e.toString()));

        entityManager.close();
        entityManagerFactory.close();
    }
}