package pl.agileit.bar.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDayReportDTO {

    private long day;
    private int percentageBonus;
    private BigDecimal hours;
    private BigDecimal hourlyRate;
    private BigDecimal salary;
}
