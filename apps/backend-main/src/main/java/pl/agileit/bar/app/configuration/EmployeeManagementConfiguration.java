package pl.agileit.bar.app.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agileit.bar.employee.dao.EmployeeDAO;
import pl.agileit.bar.employee.dao.impl.EmployeeDAOImpl;
import pl.agileit.bar.employee.rest.EmployeeResource;
import pl.agileit.bar.employee.rest.impl.EmployeeResourceImpl;
import pl.agileit.bar.employee.service.EmployeeService;
import pl.agileit.bar.employee.service.SalaryService;
import pl.agileit.bar.employee.service.WorkloadService;
import pl.agileit.bar.employee.service.impl.EmployeeServiceImpl;
import pl.agileit.bar.employee.service.impl.SalaryServiceImpl;
import pl.agileit.bar.employee.service.impl.WorkloadServiceImpl;

@Configuration
public class EmployeeManagementConfiguration {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private WorkloadService workloadService;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeResource employeeResource;

    @Bean
    public SalaryService createSalaryService() {
        return new SalaryServiceImpl();
    }

    @Bean
    public WorkloadService createWorkloadService() {
        return new WorkloadServiceImpl();
    }

    @Bean
    public EmployeeDAO createEmployeeDAO() {
        return new EmployeeDAOImpl();
    }

    @Bean
    public EmployeeService createEmployeeService() {
        return new EmployeeServiceImpl(employeeDAO, salaryService, workloadService);
    }

    @Bean
    public EmployeeResource createResource() {
        return new EmployeeResourceImpl(employeeService);
    }

    @Bean
    public ResourceConfig createJerseyConfig() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(employeeResource);
        return resourceConfig;
    }
}
