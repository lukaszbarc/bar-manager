package pl.agileit.bar.employee.dao;


import pl.agileit.bar.employee.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    long save(Employee employee);

    List<Employee> findAll();

    Employee findById(long employeeId);
}
