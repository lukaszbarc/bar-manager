package pl.agileit.bar.employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Day {

    private long id;
    private Date date;
    private boolean workingDay;
    private int percentageBonus;

}
