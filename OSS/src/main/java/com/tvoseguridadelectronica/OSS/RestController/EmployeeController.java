package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.Employee;
import com.tvoseguridadelectronica.OSS.Domain.Role;
import com.tvoseguridadelectronica.OSS.Repository.EmployeeDao;
import com.tvoseguridadelectronica.OSS.Repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/employee"})
public class EmployeeController {

    private EmployeeDao employeeDao;
    private RoleDao roleDao;

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao= roleDao;
    }

    @PostMapping(value = "/addEmployee")
    public ResponseEntity<Employee> createEmployee(
            @RequestBody final Employee employee) {

        Role role = roleDao.findById(employee.getRole().getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(hashedPassword);

        Employee employeeCreate = new Employee(employee.getId(),employee.getName(),
                employee.getLastName(),employee.getPosition(),role,employee.getUsername(),
                employee.getPassword());
        try {
            System.out.println(employeeCreate +" empl");
             employeeDao.addEmployee(employeeCreate);
        } catch (Exception e) {
            return null;
        }
        return new ResponseEntity<Employee>(employeeCreate, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Employee>> searchByName(@PathVariable("name") final String name) {
        List<Employee> employees = employeeDao.find(name);
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> searchById(@PathVariable("id") final int id) {
        Employee employee = employeeDao.findById(id);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeDao.getAll();
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable("id") final int id,
            @RequestBody final Employee employee) throws SQLException {
    	
    	Role role = roleDao.findById(employee.getRole().getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(hashedPassword);

        employeeDao.updateEmployee(employee);

        Employee employeeNew = (Employee) employeeDao.findById(id);

        return new ResponseEntity<Employee>(employeeNew, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") final int id) {

        try {
            employeeDao.deleteEmployee(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
}
