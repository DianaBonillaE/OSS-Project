package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.MeasurementUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

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

    public List<MeasurementUnit> find(String name){

        String sqlProcedure = "execute OSS_MeasurementUnit_Find '%"+name+"%'";
        return this.jdbcTemplate.query(sqlProcedure,new MeasurementUnitRowMapper());
    }

    public MeasurementUnit findById(int id){

        String sqlProcedure = "execute OSS_MeasurementUnit_FindById "+id;
        MeasurementUnit measurementUnit= this.jdbcTemplate.queryForObject(sqlProcedure,new MeasurementUnitRowMapper());
        return measurementUnit;

    }

    public void updateMeasurementUnit(int id, String name)throws SQLException{

        Connection connection = dataSource.getConnection();
        String sqlUpdate = "{call OSS_MeasurementUnit_Update (?,?)}";
        CallableStatement statement = connection.prepareCall(sqlUpdate);
        statement.setInt(1, id);
        statement.setString(2, name);

        statement.execute();
        statement.close();
        connection.close();
    }

    public List<MeasurementUnit> getAll(){
        String sqlProcedure = "execute OSS_MeasurementUnit_GetAll";
        return this.jdbcTemplate.query(sqlProcedure,new MeasurementUnitRowMapper());
    }

    public void deleteMeasurementUnit(int id)throws SQLException{

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_MeasurementUnit_Delete (?)}";

        CallableStatement statement =connection.prepareCall(sqlDelete);
        statement.setInt(1, id);

        statement.execute();
        statement.close();
        connection.close();
    }

    class MeasurementUnitRowMapper implements RowMapper<MeasurementUnit> {

        @Override
        public MeasurementUnit mapRow(ResultSet resultSet, int i) throws SQLException {
            MeasurementUnit measurementUnit = new MeasurementUnit();
            measurementUnit.setId(resultSet.getInt("id"));
            measurementUnit.setName(resultSet.getString("name"));
          return measurementUnit;
        }
    }


}
