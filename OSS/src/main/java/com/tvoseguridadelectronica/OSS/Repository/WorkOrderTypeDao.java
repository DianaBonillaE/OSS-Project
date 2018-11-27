package com.tvoseguridadelectronica.OSS.Repository;

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
public class WorkOrderTypeDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int addWorkOrderType(WorkOrderType workOrderType) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_WorkOrderType_Insert (?,?)}";
        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, workOrderType.getName());
        statement.execute();
        int id = statement.getInt("id");
        statement.close();
        connection.close();
        return id;
    }

    public List<WorkOrderType> find(String name) {
        String sqlProcedure = "execute OSS_WorkOrderType_Find '%" + name + "%'";
        return this.jdbcTemplate.query(sqlProcedure, new WorkOrderTypeRowMapper());
    }

    public WorkOrderType findById(int id) {
        String sqlProcedure = "execute OSS_WorkOrderType_FindById " + id;
        WorkOrderType workOrderType = this.jdbcTemplate.queryForObject(sqlProcedure, new WorkOrderTypeRowMapper());
        return workOrderType;
    }

    public void updateWorkOrderType(int id, String name) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlUpdate = "{call OSS_WorkOrderType_Update (?,?)}";
        CallableStatement statement = connection.prepareCall(sqlUpdate);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.execute();
        statement.close();
        connection.close();
    }

    public List<WorkOrderType> getAll() {
        String sqlProcedure = "execute OSS_WorkOrderType_GetAll";
        return this.jdbcTemplate.query(sqlProcedure, new WorkOrderTypeRowMapper());
    }

    public void deleteWorkOrderType(int id) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_WorkOrderType_Delete (?)}";

        CallableStatement statement = connection.prepareCall(sqlDelete);
        statement.setInt(1, id);
        statement.execute();
        statement.close();
        connection.close();
    }

    class WorkOrderTypeRowMapper implements RowMapper<WorkOrderType> {

        @Override
        public WorkOrderType mapRow(ResultSet resultSet, int i) throws SQLException {
           WorkOrderType workOrderType = new WorkOrderType();
            workOrderType.setId(resultSet.getInt("id"));
            workOrderType.setName(resultSet.getString("name"));
            return workOrderType;
        }
    }
}
