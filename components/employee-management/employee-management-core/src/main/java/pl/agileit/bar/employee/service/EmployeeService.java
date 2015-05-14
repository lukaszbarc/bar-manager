package pl.agileit.bar.employee.service;

import pl.agileit.bar.employee.dto.EmployeeMonthlyReportDTO;
import pl.agileit.bar.employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    long addEmployee(Employee employee);

    Employee getEmployee(long employeeId);

    List<Employee> getAllEmployees();

    EmployeeMonthlyReportDTO createMonthlyReport(long employeeId, int month, int year);

}
