package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.ReportWoDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReportsDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ReportWoDate> searchWoDate(String name, String date){

        Connection connection = null;
        String sqlProcedure = null;


            String consulta ="execute OSS_searchWoDate '"+name+"', '"+date+"'";


        return this.jdbcTemplate.query(consulta, new ReportWoDateRowMapper());
    }

    class ReportWoDateRowMapper implements RowMapper<ReportWoDate> {

        @Override
        public ReportWoDate mapRow(ResultSet resultSet, int i) throws SQLException {
            ReportWoDate reportWoDate = new ReportWoDate();
            reportWoDate.setNameClient(resultSet.getString("name"));
            reportWoDate.setDate(resultSet.getString("date"));
            reportWoDate.setDescription(resultSet.getString("description"));

            return reportWoDate;
        }
    }

}
