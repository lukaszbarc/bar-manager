package pl.agileit.bar.employee.service;

import pl.agileit.bar.employee.model.Salary;

import java.util.List;

public interface SalaryService {
    List<Salary> loadSalariesForMonth(long employeeId, int month, int year);
}
