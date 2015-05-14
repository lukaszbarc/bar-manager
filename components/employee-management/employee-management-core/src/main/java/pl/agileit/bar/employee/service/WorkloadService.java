package pl.agileit.bar.employee.service;

import pl.agileit.bar.employee.model.Workload;

import java.util.List;

public interface WorkloadService {
    List<Workload> loadWorkloadsForMonth(long employeeId, int month, int year);
}
