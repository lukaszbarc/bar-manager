package pl.agileit.bar.employee.rest;

import pl.agileit.bar.employee.model.Employee;

import javax.ws.rs.*;
import java.util.List;

@Path("/employee")
@Produces("application/json")
public interface EmployeeResource {

    @GET
    @Path("/{identity}")
    public Employee getEmployee(@PathParam("identity") final long identity);

    @GET
    public List<Employee> getAllEmployees();

    @POST
    public long addEmployee(Employee employee);
}
