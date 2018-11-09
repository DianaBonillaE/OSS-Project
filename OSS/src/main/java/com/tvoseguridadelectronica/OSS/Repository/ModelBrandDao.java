package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.ModelBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class ModelBrandDao {

    private SimpleJdbcCall simpleJdbcCall;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    class ModelBrandRowMapper implements RowMapper<ModelBrand> {

        @Override
        public ModelBrand mapRow(ResultSet rs, int i) throws SQLException {

            ModelBrand modelBrand = new ModelBrand();
            modelBrand.setId(rs.getInt("id"));
            modelBrand.setModel(rs.getString("model"));
            modelBrand.setBrand(rs.getString("brand"));

            return modelBrand;
        }
    }

    public List<ModelBrand> findAll(){
        String consulta ="select * from Model_Brand";
        return this.jdbcTemplate.query(consulta, new ModelBrandRowMapper());

    }

    public ModelBrand insert(ModelBrand modelBrand)throws SQLException{

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_Model_Brand_Insert (?,?)}";

        CallableStatement statement = connection.prepareCall(sqlInsert);
        //statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(1, modelBrand.getModel());
        statement.setString(2, modelBrand.getBrand());

        statement.execute();
        statement.close();
        connection.close();

        return modelBrand;
    }

  /*  public ProductCategory findById(int productCategoryId){

        String sqlProcedure = "{call Panaris_Product_Category_FindById (?)}";

        ProductCategory productCategory= this.jdbcTemplate.queryForObject(sqlProcedure,new CategoryRowMapper(),productCategoryId);

        return productCategory;
    }*/

    public void update (ModelBrand modelBrand) {

        simpleJdbcCall.withProcedureName("OSS_Model_Brand_Update");
        Map<String,Object> inParamMap = new HashMap<String,Object>();
        inParamMap.put("id", modelBrand.getId());
        inParamMap.put("model", modelBrand.getModel());
        inParamMap.put("brand", modelBrand.getBrand());
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        simpleJdbcCall.execute(in);

    }

    public void delete(int modelBrandId)throws SQLException{
        System.out.println("entrooooo");

        Connection connection = dataSource.getConnection();
        String sqlDelete = "{call OSS_Model_Brand_Delete (?)}";

        CallableStatement statement =connection.prepareCall(sqlDelete);
        statement.setInt(1, modelBrandId);

        statement.execute();
        statement.close();
        connection.close();
    }

}
