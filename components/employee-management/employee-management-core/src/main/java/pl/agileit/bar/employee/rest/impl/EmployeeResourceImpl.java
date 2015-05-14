package pl.agileit.bar.employee.rest.impl;

import pl.agileit.bar.employee.model.Employee;
import pl.agileit.bar.employee.rest.EmployeeResource;
import pl.agileit.bar.employee.service.EmployeeService;

import java.util.List;


public class EmployeeResourceImpl implements EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResourceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee getEmployee(final long identity) {
        return employeeService.getEmployee(identity);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Override
    public long addEmployee(Employee employee) {
        return employeeService.addEmployee(employee);
    }
}
