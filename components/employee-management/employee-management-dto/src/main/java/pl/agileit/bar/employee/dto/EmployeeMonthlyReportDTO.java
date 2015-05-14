package pl.agileit.bar.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeMonthlyReportDTO {

    private String name;
    private String surname;
    private int month;
    private int year;
    private BigDecimal hours;
    private BigDecimal salary;
    private List<EmployeeDayReportDTO> employeeDayReports;
}
