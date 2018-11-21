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
import com.tvoseguridadelectronica.OSS.Domain.DeviceState;


@Repository
public class DeviceStateDao {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCall=new SimpleJdbcCall(dataSource);
	}
	
	
	public DeviceState saveDeviceState(DeviceState deviceState) throws SQLException{

		SqlParameterSource parameterSource= new MapSqlParameterSource()
				.addValue("device_state_id", deviceState.getState());		
		
		simpleJdbcCall.setProcedureName("Oss_Device_State_Insert");
		Map<String, Object> outParameters= simpleJdbcCall.execute(parameterSource);
		
		
		return deviceState;
	}
	
	public DeviceState updateDeviceState(DeviceState deviceState) throws SQLException{

		SqlParameterSource parameterSource= new MapSqlParameterSource()
				.addValue("description", deviceState.getState());	
		
		simpleJdbcCall.setProcedureName("Oss_Device_State_Update");
		Map<String, Object> outParameters= simpleJdbcCall.execute(parameterSource);
		
		
		return deviceState;
	}
	
	public DeviceState findDeviceStateById(int id) throws SQLException{

		String procedure = "execute Oss_Device_State_GetById @id='"+id+"'";
		return jdbcTemplate.query(procedure, new ListDeviceStateDAOExtractor()).get(0);
	}
	
	public void deleteDeviceState(int id) throws SQLException{

	
		SqlParameterSource parameterSource= new MapSqlParameterSource()
				.addValue("id",id);			
		
		simpleJdbcCall.setProcedureName("Oss_Device_State_Delete");
		simpleJdbcCall.execute(parameterSource);
		
	}
	
	
	public List<DeviceState> deviceStatesAll() {

		String procedure = "execute Oss_Device_State_GetAll";
		return jdbcTemplate.query(procedure, new ListDeviceStateDAOExtractor());
	}
	
	
	
	
	private static final class ListDeviceStateDAOExtractor implements ResultSetExtractor<List<DeviceState>> {
		@Override
		public List<DeviceState> extractData(ResultSet rs) throws SQLException, DataAccessResourceFailureException {
			Map<Integer, DeviceState> map = new HashMap<Integer, DeviceState>();
			DeviceState deviceState = null;
			while (rs.next()) {
				int ID = rs.getInt("id");
				deviceState = map.get(ID);
				if (deviceState == null) {
					deviceState = new DeviceState();
					deviceState.setId(rs.getInt("id"));
					deviceState.setState(rs.getString("state"));					
					map.put(ID, deviceState);
				}
			}
			return new ArrayList<DeviceState>(map.values());
		}
	}

}
