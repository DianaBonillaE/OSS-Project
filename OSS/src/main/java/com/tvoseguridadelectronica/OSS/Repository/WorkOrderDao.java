package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.Client;
import com.tvoseguridadelectronica.OSS.Domain.WorkOrder;
import com.tvoseguridadelectronica.OSS.Domain.WorkOrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class WorkOrderDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;
    ClientDao clientDao;
    WorkOrderTypeDao workOrderTypeDao;

    @Autowired
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
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

        String sqlProcedure = "execute OSS_MeasurementUnit_FindById " + id;
        WorkOrder workOrder= this.jdbcTemplate.queryForObject(sqlProcedure, new WorkOrderRowMapper());
        return workOrder;

    }

    public void updateWorkOrder(int id, String description) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlUpdate = "{call OSS_WorkOrder_Update (?,?)}";
        CallableStatement statement = connection.prepareCall(sqlUpdate);
        statement.setInt(1, id);
        statement.setString(2, description);
        statement.execute();
        statement.close();
        connection.close();
    }

    public List<WorkOrder> getAll() {
        String sqlProcedure = "execute OSS_WorkOrder_GetAll";
        return this.jdbcTemplate.query(sqlProcedure, new WorkOrderRowMapper());
    }

    public void deleteWorkOrder(int id) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_WorkOrder_Delete (?)}";

        CallableStatement statement = connection.prepareCall(sqlDelete);
        statement.setInt(1, id);

        statement.execute();
        statement.close();
        connection.close();
    }

    class WorkOrderRowMapper implements RowMapper<WorkOrder> {

        @Override
        public WorkOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            WorkOrder workOrder = new WorkOrder();
            workOrder.setId(resultSet.getInt("id"));
            workOrder.setDescription(resultSet.getString("description"));
            Client client = clientDao.findById(resultSet.getString("client_id"));
            workOrder.setClient(client);
            WorkOrderType workOrderType = workOrderTypeDao.findById(resultSet.getInt("work_order_type_id"));
            workOrder.setWorkOrderType(workOrderType);
            return workOrder;
        }
    }
}
