package pl.agileit.bar.employee.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agileit.bar.employee.dao.EmployeeDAO;
import pl.agileit.bar.employee.dto.EmployeeDayReportDTO;
import pl.agileit.bar.employee.dto.EmployeeMonthlyReportDTO;
import pl.agileit.bar.employee.model.Employee;
import pl.agileit.bar.employee.model.Salary;
import pl.agileit.bar.employee.model.Workload;
import pl.agileit.bar.employee.service.EmployeeService;
import pl.agileit.bar.employee.service.SalaryService;
import pl.agileit.bar.employee.service.WorkloadService;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeDAO employeeDAO;
    private SalaryService salaryService;
    private WorkloadService workloadService;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO, SalaryService salaryService, WorkloadService workloadService) {
        this.employeeDAO = employeeDAO;
        this.salaryService = salaryService;
        this.workloadService = workloadService;
    }

    @Override
    public long addEmployee(Employee employee) {
        return employeeDAO.save(employee);
    }

    @Override
    public Employee getEmployee(long employeeId) {
        return employeeDAO.findById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @Override
    public EmployeeMonthlyReportDTO createMonthlyReport(long employeeId, int month, int year) {

        Employee employee = employeeDAO.findById(employeeId);

        List<Workload> workloads = workloadService.loadWorkloadsForMonth(employeeId, month, year);

        List<Salary> salaries = salaryService.loadSalariesForMonth(employeeId, month, year);

        return EmployeeMonthlyReportDTO.builder()
                .name(employee.getName())
                .surname(employee.getSurname())
                .month(month)
                .year(year)
                .hours(sumHours(workloads))
                .salary(sumSalary(workloads, salaries))
                .employeeDayReports(fillDayReports(workloads, salaries))
                .build();

    }

    private List<EmployeeDayReportDTO> fillDayReports(List<Workload> workloads, List<Salary> salaries) {
        Function<Workload, EmployeeDayReportDTO> dayReportMapper = workload -> EmployeeDayReportDTO.builder()
                .day(workload.getDayId())
                .hours(workload.getHours())
                .percentageBonus(workload.getPercentageBonus())
                .hourlyRate(findSalary(workload.getDayId(), salaries))
                .salary(findSalary(workload.getDayId(), salaries)
                        .multiply(workload.getHours())
                        .multiply(BigDecimal.valueOf(100 + workload.getPercentageBonus()))
                        .divide(BigDecimal.valueOf(100)))
                .build();
        return workloads.stream()
                .map(dayReportMapper)
                .collect(Collectors.toList());
    }

    private BigDecimal sumSalary(List<Workload> workloads, List<Salary> salaries) {
        Function<Workload, BigDecimal> salaryMapper = workload ->
                workload.getHours()
                        .multiply(findSalary(workload.getDayId(), salaries))
                        .multiply(BigDecimal.valueOf(100 + workload.getPercentageBonus()))
                        .divide(BigDecimal.valueOf(100));
        return workloads.stream()
                .map(salaryMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal findSalary(long dayId, List<Salary> salaries) {
        return salaries.stream()
                .filter(s -> dayId > s.getStartDayId() && dayId < s.getEndDayId())
                .limit(1)
                .findFirst().get().getHourlyRate();
    }


    private BigDecimal sumHours(List<Workload> workloads) {
        Function<Workload, BigDecimal> hoursMapper = workload -> workload.getHours();
        return workloads.stream()
                .map(hoursMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
