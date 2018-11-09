package com.tvoseguridadelectronica.OSS.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
	
	
	public Device saveDevice(Device device) throws SQLException{

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
	
	public Device updateDevice(Device device) throws SQLException{

		SqlParameterSource parameterSource= new MapSqlParameterSource().addValue("serial_number",device.getSerialNumber())
				.addValue("name",device.getName())
				.addValue("description", device.getDescription())
				.addValue("quantity", device.getQuantity())
				.addValue("manufacturerModel",device.getManufacturerModel())
				.addValue("model_brand_id", device.getBrand().getId())
				.addValue("inventory_category_id",device.getCategory().getId())
				.addValue("measurement_unit_id", device.getUnit().getId())
				.addValue("device_state_id", device.getState().getId());		
		
		simpleJdbcCall.setProcedureName("Oss_Device_Update");
		Map<String, Object> outParameters= simpleJdbcCall.execute(parameterSource);
		
		
		return device;
	}
	
	public Device findDeviceById(String id) throws SQLException{

		String procedure = "execute Oss_Device_GetById @id='"+id+"'";
		return jdbcTemplate.query(procedure, new ListDeviceDAOExtractor()).get(0);
	}
	
	public void deleteDevice(String id) throws SQLException{

		String procedure = "execute Oss_Device_Delete @id='"+id+"'";
		
	}
	
	
	public List<Device> devicesAll() {

		String procedure = "execute Oss_Device_GetAll";
		return jdbcTemplate.query(procedure, new ListDeviceDAOExtractor());
	}
	
	
	
	
	private static final class ListDeviceDAOExtractor implements ResultSetExtractor<List<Device>> {
		@Override
		public List<Device> extractData(ResultSet rs) throws SQLException, DataAccessResourceFailureException {
			Map<String, Device> map = new HashMap<String, Device>();
			Device device = null;
			while (rs.next()) {
				String ID = rs.getString("serial_number");
				device = map.get(ID);
				if (device == null) {
					device = new Device();
					device.setSerialNumber(rs.getString("serial_number"));
					device.setName(rs.getString("name"));
					device.setDescription(rs.getString("description"));
					device.setQuantity(rs.getInt("quantity"));
					device.setManufacturerModel(rs.getString("manufacture_model"));
					device.getState().setId(rs.getInt("id_state"));
					device.getState().setState("state");
					device.getCategory().setId(rs.getInt("id_category"));
					device.getCategory().setName(rs.getString("name_category"));
					device.getUnit().setId(rs.getInt("id_unit"));
					device.getUnit().setName(rs.getString("name_unit"));
					device.getBrand().setId(rs.getInt("id_brand"));
					device.getBrand().setModel(rs.getString("model"));
					device.getBrand().setBrand(rs.getString("brand"));
					
					
					
					map.put(ID, device);
				}
			}
			return new ArrayList<Device>(map.values());
		}
	}

}
