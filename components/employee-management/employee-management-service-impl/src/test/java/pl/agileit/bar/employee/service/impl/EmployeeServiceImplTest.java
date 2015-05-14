package pl.agileit.bar.employee.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agileit.bar.employee.dao.EmployeeDAO;
import pl.agileit.bar.employee.dto.EmployeeDayReportDTO;
import pl.agileit.bar.employee.dto.EmployeeMonthlyReportDTO;
import pl.agileit.bar.employee.model.Employee;
import pl.agileit.bar.employee.model.Salary;
import pl.agileit.bar.employee.model.Workload;
import pl.agileit.bar.employee.service.SalaryService;
import pl.agileit.bar.employee.service.WorkloadService;

import java.math.BigDecimal;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {

    public static final int EMPLOYEE_ID = 10;
    public static final int MONTH = 12;
    public static final int YEAR = 2015;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImplTest.class);
    private EmployeeServiceImpl objectUnderTest;

    private EmployeeDAO employeeDAO;
    private SalaryService salaryService;
    private WorkloadService workloadService;

    @Before
    public void init() {
        employeeDAO = Mockito.mock(EmployeeDAO.class);
        salaryService = Mockito.mock(SalaryService.class);
        workloadService = Mockito.mock(WorkloadService.class);
        objectUnderTest = new EmployeeServiceImpl(employeeDAO, salaryService, workloadService);
    }


    @Test
    public void testEmployeeDataValid() {
        // when
        when(employeeDAO.findById(EMPLOYEE_ID)).thenReturn(Employee.builder()
                        .name("Lukasz")
                        .surname("Barc")
                        .build()
        );
        EmployeeMonthlyReportDTO monthlyReport = objectUnderTest.createMonthlyReport(EMPLOYEE_ID, MONTH, YEAR);

        // then
        assertThat(monthlyReport.getName()).isEqualTo("Lukasz");
        assertThat(monthlyReport.getSurname()).isEqualTo("Barc");
    }

    @Test
    public void testTimeDataValid() {
        // when
        when(employeeDAO.findById(EMPLOYEE_ID)).thenReturn(Employee.builder().build());
        EmployeeMonthlyReportDTO monthlyReport = objectUnderTest.createMonthlyReport(EMPLOYEE_ID, MONTH, YEAR);

        // then
        assertThat(monthlyReport.getMonth()).isEqualTo(MONTH);
        assertThat(monthlyReport.getYear()).isEqualTo(YEAR);
    }

    @Test
    public void testSalaryDataValidForOneSalaryInMonthScenario() {
        // when
        when(employeeDAO.findById(EMPLOYEE_ID)).thenReturn(Employee.builder().build());
        when(salaryService.loadSalariesForMonth(EMPLOYEE_ID, MONTH, YEAR)).thenReturn(newArrayList(
                Salary.builder()
                        .employeeId(EMPLOYEE_ID)
                        .startDayId(1)
                        .endDayId(999)
                        .hourlyRate(new BigDecimal("5.51"))
                        .build()
        ));
        when(workloadService.loadWorkloadsForMonth(EMPLOYEE_ID, MONTH, YEAR)).thenReturn(newArrayList(
                Workload.builder()
                        .dayId(100)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(8))
                        .percentageBonus(22)
                        .build(),
                Workload.builder()
                        .dayId(101)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(8))
                        .percentageBonus(15)
                        .build(),
                Workload.builder()
                        .dayId(102)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(8))
                        .percentageBonus(15)
                        .build(),
                Workload.builder()
                        .dayId(103)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(8))
                        .percentageBonus(45)
                        .build(),
                Workload.builder()
                        .dayId(104)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(8))
                        .percentageBonus(25)
                        .build(),
                Workload.builder()
                        .dayId(105)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(8))
                        .percentageBonus(20)
                        .build(),
                Workload.builder()
                        .dayId(106)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(20))
                        .percentageBonus(14)
                        .build(),
                Workload.builder()
                        .dayId(107)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(24))
                        .percentageBonus(13)
                        .build(),
                Workload.builder()
                        .dayId(108)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(24))
                        .percentageBonus(0)
                        .build(),
                Workload.builder()
                        .dayId(109)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(12))
                        .percentageBonus(0)
                        .build(),
                Workload.builder()
                        .dayId(110)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(8))
                        .percentageBonus(13)
                        .build(),
                Workload.builder()
                        .dayId(111)
                        .accepted(true)
                        .hours(BigDecimal.valueOf(24))
                        .percentageBonus(13)
                        .build()
        ));
        EmployeeMonthlyReportDTO monthlyReport = objectUnderTest.createMonthlyReport(EMPLOYEE_ID, MONTH, YEAR);

        // when
        assertThat(monthlyReport.getHours()).isEqualTo(BigDecimal.valueOf(160));
        assertThat(monthlyReport.getSalary()).isEqualTo(new BigDecimal("999.7344"));
        assertThat(monthlyReport.getEmployeeDayReports()
                .stream()
                .map(new Function<EmployeeDayReportDTO, BigDecimal>() {
                    @Override
                    public BigDecimal apply(EmployeeDayReportDTO employeeDayReportDTO) {
                        return employeeDayReportDTO.getSalary();
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add))
                .isEqualTo(new BigDecimal("999.7344"));

        LOGGER.info(monthlyReport.toString());
    }

}