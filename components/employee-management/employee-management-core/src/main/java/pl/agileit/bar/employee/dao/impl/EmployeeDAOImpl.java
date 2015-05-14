package pl.agileit.bar.employee.dao.impl;

import pl.agileit.bar.employee.dao.EmployeeDAO;
import pl.agileit.bar.employee.model.Employee;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public long save(Employee employee) {
        return 0;
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public Employee findById(long employeeId) {
        return Employee.builder().name("Lukasz").surname("Barc").build();
    }
}
