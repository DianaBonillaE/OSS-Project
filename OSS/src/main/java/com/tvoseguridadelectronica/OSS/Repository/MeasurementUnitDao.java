package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.MeasurementUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class MeasurementUnitDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int addMeasurementUnit(MeasurementUnit measurementUnit) throws SQLException {
        System.out.println("hello " + measurementUnit.getName());
        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_MeasurementUnit_Insert (?,?)}";
        CallableStatement statement =connection.prepareCall(sqlInsert);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2,measurementUnit.getName());
        statement.execute();
        int id=statement.getInt("measurement_unit_id");
        statement.close();
        connection.close();
        return id;
    }
}
