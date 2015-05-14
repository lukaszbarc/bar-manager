package pl.agileit.bar.employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    private long id;
    private long userId;
    private String name;
    private String surname;
    private Date employedFrom;
    private Date employedTo;
    private List<Salary> salaries;
    private List<Workload> workloads;
}
