package org.practice.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "salaries")
public class Salary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "salary_id")
    private Long id;

    @Column
    private Company company;

    @Column
    private String level;

    @Column
    private Double bonusPercentage;

    @Column(name = "starting_salary")
    private Double startingSalary;

    @Column(name = "current_salary")
    private Double currentSalary;

    @Column
    private Boolean activeFlag;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public Salary() {
    }

    public Salary(Long id, Company company, String level, Boolean flag, Double bonusPercentage, Double startingSalary, Double currentSalary, String title) {
        this.id = id;
        this.company = company;
        this.level = level;
        this.activeFlag = flag;
        this.bonusPercentage = bonusPercentage;
        this.currentSalary = currentSalary;
        this.startingSalary = startingSalary;
        this.title = title;
    }

    public Salary(Company company, String level, Boolean flag, Double bonusPercentage, Double startingSalary, Double currentSalary, String title) {
        this.company = company;
        this.level = level;
        this.activeFlag = flag;
        this.bonusPercentage = bonusPercentage;
        this.currentSalary = currentSalary;
        this.startingSalary = startingSalary;
        this.title = title;
    }

    public Salary(Double currentSalary, Boolean activeFlag) {
        this.activeFlag = activeFlag;
        this.currentSalary = currentSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(Double bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public Double getStartingSalary() {
        return startingSalary;
    }

    public void setStartingSalary(Double startingSalary) {
        this.startingSalary = startingSalary;
    }

    public Double getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(Double currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", company=" + company.getId() +
                ", level='" + level + '\'' +
                ", bonusPercentage=" + bonusPercentage +
                ", startingSalary=" + startingSalary +
                ", currentSalary=" + currentSalary +
                ", activeFlag=" + activeFlag +
                ", title='" + title + '\'' +
                '}';
    }
}
