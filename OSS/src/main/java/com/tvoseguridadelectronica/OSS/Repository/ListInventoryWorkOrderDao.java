package com.tvoseguridadelectronica.OSS.Repository;

import com.tvoseguridadelectronica.OSS.Domain.InventoryCategory;
import com.tvoseguridadelectronica.OSS.Domain.ListInventoryWorkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class ListInventoryWorkOrderDao {

    private SimpleJdbcCall simpleJdbcCall;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    class ListInventoryWorkOrderRowMapper implements RowMapper<ListInventoryWorkOrder> {

        @Override
        public ListInventoryWorkOrder mapRow(ResultSet rs, int i) throws SQLException {

            ListInventoryWorkOrder listInventoryWorkOrder = new ListInventoryWorkOrder();
            listInventoryWorkOrder.setId(rs.getInt("id"));
            listInventoryWorkOrder.setName(rs.getString("name"));

            return listInventoryWorkOrder;
        }
    }

    public ListInventoryWorkOrder insertarLista(ListInventoryWorkOrder listaWO)throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_ListWorkOrder_Insert (?,?)}";

        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, listaWO.getName());

        statement.execute();
        statement.close();
        connection.close();

        return listaWO;
    }

    public void asignarLista(int idWorkOrder, int idListInventoryWorkOrder)throws SQLException {

        Connection connection = dataSource.getConnection();
        String sqlInsert = "{call OSS_ListWorkOrder_Asignar (?,?)}";

        CallableStatement statement = connection.prepareCall(sqlInsert);
        statement.setInt(1, idWorkOrder);
        statement.setInt(2,idListInventoryWorkOrder );

        statement.execute();
        statement.close();
        connection.close();

    }

    public List<ListInventoryWorkOrder> findAll(){
        String consulta ="select * from List_work_order";
        return this.jdbcTemplate.query(consulta, new ListInventoryWorkOrderDao.ListInventoryWorkOrderRowMapper());

    }

}
