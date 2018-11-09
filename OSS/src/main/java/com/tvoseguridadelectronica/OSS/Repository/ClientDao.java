package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.Client;
import com.tvoseguridadelectronica.OSS.Domain.Telephone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Client> findAllClients(){
        String sql = " select c.id, c.name, c.contactName from Client c";
        return jdbcTemplate.query(sql, new ClientRowMapper());
    }

    public Client findById(String id){

        String sqlProcedure = "{call OSS_CLIENT_FINDBYID (?)}";
        Client client = this.jdbcTemplate.queryForObject(sqlProcedure,new ClientRowMapper(),id);

        return client;
    }

    public Client insertClient(Client client) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_CLIENT_INSERTCLIENT (?,?,?)}";

        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.setString(1, client.getId());
        statement.setString(2, client.getName());
        statement.setString(3, client.getContactName());

        statement.execute();
        statement.close();
        connection.close();

        return client;
    }

    public void updateClient(Client client){

        simpleJdbCall.withProcedureName("OSS_CLIENT_UPDATECLIENT");
        Map<String,Object> inParamMap = new HashMap<String,Object>();
        inParamMap.put("id", client.getId());
        inParamMap.put("name", client.getName());
        inParamMap.put("contactName", client.getContactName());

        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        simpleJdbCall.execute(in);
    }


    public void deleteClient(String id) throws SQLException{

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_CLIENT_DELETECLIENT (?)}";

        CallableStatement statement =connection.prepareCall(sqlDelete);
        statement.setString(1, id);

        statement.execute();
        statement.close();
        connection.close();
    }

    class ClientRowMapper implements RowMapper<Client>{

        @Nullable
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {

            Client client = new Client();

            client.setId(rs.getString("id"));
            client.setName(rs.getString("name"));
            client.setContactName(rs.getString("contactName"));

            return client;
        }
    }
}
