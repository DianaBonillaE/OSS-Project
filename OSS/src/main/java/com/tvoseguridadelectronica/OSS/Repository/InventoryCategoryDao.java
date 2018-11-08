package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.InventoryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InventoryCategoryDao {

    private SimpleJdbcCall simpleJdbcCall;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    class CategoryRowMapper implements RowMapper<InventoryCategory> {

        @Override
        public InventoryCategory mapRow(ResultSet rs, int i) throws SQLException {

            InventoryCategory category = new InventoryCategory();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));

            return category;
        }
    }

    public InventoryCategory insert(InventoryCategory productCategory)throws SQLException{

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_Category_Insert (?,?)}";

        CallableStatement statement =connection.prepareCall(sqlInsert);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, productCategory.getName());

        statement.execute();
        statement.close();
        connection.close();

        return productCategory;
    }

    public List<InventoryCategory> findAll(){
        String consulta ="select * from Inventory_category";
        return this.jdbcTemplate.query(consulta, new CategoryRowMapper());

    }

    public InventoryCategory findByName(int inventoryCategoryName){

        String sqlProcedure = "{call OSS_Inventory_Category_FindByName (?)}";

        InventoryCategory inventoryCategory= this.jdbcTemplate.queryForObject(sqlProcedure,new CategoryRowMapper(),inventoryCategoryName);

        return inventoryCategory;
    }

    public void update (InventoryCategory inventoryCategory) {

        simpleJdbcCall.withProcedureName("OSS_Inventory_Category_Update");
        Map<String,Object> inParamMap = new HashMap<>();
        inParamMap.put("id", inventoryCategory.getId());
        inParamMap.put("name", inventoryCategory.getName());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        simpleJdbcCall.execute(in);

    }

    public void delete(int inventoryCategoryId)throws SQLException{

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_Inventory_Category_Delete (?)}";

        CallableStatement statement =connection.prepareCall(sqlDelete);
        statement.setInt(1, inventoryCategoryId);

        statement.execute();
        statement.close();
        connection.close();
    }

}
