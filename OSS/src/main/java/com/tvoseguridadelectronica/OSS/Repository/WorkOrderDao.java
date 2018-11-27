package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.Client;
import com.tvoseguridadelectronica.OSS.Domain.Employee;
import com.tvoseguridadelectronica.OSS.Domain.Role;
import com.tvoseguridadelectronica.OSS.Domain.WorkOrder;
import com.tvoseguridadelectronica.OSS.Domain.WorkOrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class WorkOrderDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;
    ClientDao clientDao;
    WorkOrderTypeDao workOrderTypeDao;
    RoleDao roleDao;


    @Autowired
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    @Autowired
    public void setRoleDao(RoleDao roleDao){
    	this.roleDao=roleDao;
    }
    
    @Autowired
    public void setWorkOrderTypeDao(WorkOrderTypeDao workOrderTypeDao) {
        this.workOrderTypeDao = workOrderTypeDao;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int addWorkOrder(WorkOrder workOrder) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_WorkOrder_Insert (?,?,?,?)}";
        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, workOrder.getDescription());
        statement.setString(3, workOrder.getClient().getId());
        statement.setInt(4, workOrder.getWorkOrderType().getId());
        statement.execute();
        int id = statement.getInt("id");
        statement.close();
        connection.close();
        return id;
    }

    public void addEmployees(int id,String idEmployee) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_WorkOrderEmployee (?,?)}";
        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.setInt(1,id);
        statement.setString(2,idEmployee);
        statement.execute();
        statement.close();
        connection.close();
    }

    public WorkOrder findById(int id) {

        String sqlProcedure = "execute OSS_WorkOrder_FindById " + id;
        WorkOrder workOrder= this.jdbcTemplate.queryForObject(sqlProcedure, new WorkOrderRowMapper());
        return workOrder;

    }

   // @Transactional
    public void updateWorkOrder(int id, WorkOrder workOrder) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlUpdate = "{call OSS_WorkOrder_Update (?,?)}";
        CallableStatement statement = connection.prepareCall(sqlUpdate);
        statement.setInt(1, id);
        statement.setString(2, workOrder.getDescription());
        statement.execute();
        statement.close();
        connection.close();        
       changesVerify(workOrder.getId(),workOrder.getEmployees());
        
        
        
    }
    
    private boolean changesVerify(int idWorkOrder,List<Employee> employees) throws SQLException{
    	List<Employee> employeesbd=getEmployeesWorkOrder(idWorkOrder);    
    	
    	boolean bandera=false;
    	if(!employees.toString().equals(employeesbd.toString())){
    		bandera=true;    		
    	}
    	
    	if(bandera){
    		  System.err.println("entro");              
    		//primero elimino todos los empleados de esa orden de trabajo
    		Connection connection = dataSource.getConnection();
            String sqlUpdate = "{call OSS_WorkOrder_Employee_Delete_All (?)}";
            CallableStatement statement = connection.prepareCall(sqlUpdate);
            statement.setInt(1, idWorkOrder);
            statement.execute();
            statement.close();
            
            //Luego agrego los nuevos                        
            String sqlUpdate2 = "{call OSS_WorkOrder_Employee_Insert (?,?)}";
            for (Employee employee : employees) {
            	CallableStatement statement2 = connection.prepareCall(sqlUpdate2);
                statement2.setInt(1, idWorkOrder);
                statement2.setString(2, employee.getId());
                statement2.execute();
                statement2.close();
			}
          
    	}
    	    	    	
    	return bandera;
    }

    public List<WorkOrder> getAll() {
        String sqlProcedure = "execute OSS_WorkOrder_GetAll";
        return this.jdbcTemplate.query(sqlProcedure, new WorkOrderRowMapper());
    }

    public void deleteWorkOrder(int id) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_Work_Order_Delete (?)}";

        CallableStatement statement = connection.prepareCall(sqlDelete);
        statement.setInt(1, id);

        statement.execute();
        statement.close();
        connection.close();
    }
    
   
    

    public List<Employee> getEmployeesWorkOrder(int id) throws SQLException {

          String sqlProcedure = "execute OSS_WorkOrder_GetALLEmployees "+id;
          return this.jdbcTemplate.query(sqlProcedure, new EmployeeRowMapper());
      }


class WorkOrderRowMapper implements RowMapper<WorkOrder> {

        @Override
        public WorkOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            WorkOrder workOrder = new WorkOrder();
             workOrder.setId(resultSet.getInt("id"));
            List<Employee> employees= getEmployeesWorkOrder(workOrder.getId());
            workOrder.setEmployees(employees);
            workOrder.setDescription(resultSet.getString("description"));
            Client client = clientDao.findById(resultSet.getString("client_id"));
            workOrder.setClient(client);
            WorkOrderType workOrderType = workOrderTypeDao.findById(resultSet.getInt("work_order_type_id"));
            workOrder.setWorkOrderType(workOrderType);
            return workOrder;
        }
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
            //employee.setUsername(resultSet.getString("username"));
            //employee.setPassword(resultSet.getString("password"));
            return employee;
        }
    }
}
