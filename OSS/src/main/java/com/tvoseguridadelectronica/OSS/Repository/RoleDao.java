package com.tvoseguridadelectronica.OSS.Repository;

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
public class RoleDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbCall;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int addRole (Role role) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_Role_Insert (?,?,?)}";
        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, role.getName());
        statement.setString(3, role.getType());
        statement.execute();
        int id = statement.getInt("role_id");
        statement.close();
        connection.close();
        return id;
    }

    public List<Role> find(String name) {

        String sqlProcedure = "execute OSS_Role_Find '%" + name + "%'";
        return this.jdbcTemplate.query(sqlProcedure, new RoleRowMapper());
    }

    public Role findById(int id) {

        String sqlProcedure = "execute OSS_Role_FindById " + id;
        Role role = this.jdbcTemplate.queryForObject(sqlProcedure, new RoleRowMapper());
        return role;

    }

    public void updateRole(int id, String name, String type) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlUpdate = "{call OSS_Role_Update (?,?,?)}";
        CallableStatement statement = connection.prepareCall(sqlUpdate);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setString(3,type);
        statement.execute();
        statement.close();
        connection.close();
    }

    public List<Role> getAll() {
        String sqlProcedure = "execute OSS_Role_GetAll";
        return this.jdbcTemplate.query(sqlProcedure, new RoleRowMapper());
    }

    public void deleteRole(int id) throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_Role_Delete (?)}";

        CallableStatement statement = connection.prepareCall(sqlDelete);
        statement.setInt(1, id);
        statement.execute();
        statement.close();
        connection.close();
    }

    class RoleRowMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet resultSet, int i) throws SQLException {
            Role role = new Role();
            role.setId(resultSet.getInt("id"));
            role.setName(resultSet.getString("name"));
            role.setType(resultSet.getString("type"));
            return role;
        }
    }

}
