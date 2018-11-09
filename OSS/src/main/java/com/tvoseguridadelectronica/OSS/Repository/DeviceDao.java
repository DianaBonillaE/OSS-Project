package com.tvoseguridadelectronica.OSS.Repository;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tvoseguridadelectronica.OSS.Domain.Device;

@Repository
public class DeviceDao {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCall=new SimpleJdbcCall(dataSource);
	}
	
	
	public Device save(Device device) throws SQLException{

		SqlParameterSource parameterSource= new MapSqlParameterSource().addValue("serial_number",device.getSerialNumber())
				.addValue("name",device.getName())
				.addValue("description", device.getDescription())
				.addValue("quantity", device.getQuantity())
				.addValue("manufacturerModel",device.getManufacturerModel())
				.addValue("model_brand_id", device.getBrand().getId())
				.addValue("inventory_category_id",device.getCategory().getId())
				.addValue("measurement_unit_id", device.getUnit().getId())
				.addValue("device_state_id", device.getState().getId());		
		
		simpleJdbcCall.setProcedureName("Oss_Device_Insert");
		Map<String, Object> outParameters= simpleJdbcCall.execute(parameterSource);
		
		
		return device;
	}

}
