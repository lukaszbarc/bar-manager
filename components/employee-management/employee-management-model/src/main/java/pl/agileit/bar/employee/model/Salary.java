package pl.agileit.bar.employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salary {

    private long id;
    private long employeeId;
    private BigDecimal hourlyRate;
    private long startDayId;
    private long endDayId;
    private String additionalInfo;
}
