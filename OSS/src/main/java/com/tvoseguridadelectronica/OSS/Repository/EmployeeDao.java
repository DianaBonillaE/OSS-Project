package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.Employee;
import com.tvoseguridadelectronica.OSS.Domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;
    RoleDao roleDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao= roleDao;
    }

    public void addEmployee(Employee employee) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_Employee_Insert (?,?,?,?,?,?,?)}";
        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.setString(1, employee.getId());
        statement.setString(2, employee.getName());
        statement.setString(3, employee.getLastName());
        statement.setString(4, employee.getPosition());
        statement.setInt(5, employee.getRole().getId());
        statement.setString(6, employee.getUsername());
        statement.setString(7, employee.getPassword());
        statement.execute();
        statement.close();
        connection.close();
    }

    public List<Employee> find(String name) {

        String sqlProcedure = "execute OSS_Employee_Find '%" + name + "%'";
        return this.jdbcTemplate.query(sqlProcedure, new EmployeeRowMapper());
    }

    public Employee findById(int id) {

        String sqlProcedure = "execute OSS_Employee_FindById " + id;
       Employee employee = this.jdbcTemplate.queryForObject(sqlProcedure, new EmployeeRowMapper());
        return  employee;

    }

    public void updateEmployee(Employee employee) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlUpdate = "{call OSS_Employee_Update (?,?,?,?,?,?)}";
        CallableStatement statement = connection.prepareCall(sqlUpdate);
        statement.setString(1, employee.getId());
        statement.setString(2, employee.getName());
        statement.setString(3,employee.getLastName());
        statement.setString(4,employee.getPosition());
        statement.setString(5,employee.getUsername());
        statement.setString(6,employee.getPassword());
        statement.execute();
        statement.close();
        connection.close();
    }

    public List<Employee> getAll() {
        String sqlProcedure = "execute OSS_Employee_GetAll";
        return this.jdbcTemplate.query(sqlProcedure, new EmployeeRowMapper());
    }

    public void deleteEmployee(int id) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_Employee_Delete (?)}";

        CallableStatement statement = connection.prepareCall(sqlDelete);
        statement.setInt(1, id);

        statement.execute();
        statement.close();
        connection.close();
    }

    class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setId(resultSet.getString("id"));
            employee.setName(resultSet.getString("name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setPosition(resultSet.getString("position"));
            Role role = roleDao.findById(resultSet.getInt("employee_role_id"));
            employee.setRole(role);
            employee.setUsername(resultSet.getString("username"));
            employee.setPassword(resultSet.getString("password"));
            return employee;
        }
    }
}
