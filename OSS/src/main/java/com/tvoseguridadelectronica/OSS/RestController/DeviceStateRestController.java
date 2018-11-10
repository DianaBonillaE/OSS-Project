package com.tvoseguridadelectronica.OSS.RestController;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tvoseguridadelectronica.OSS.Domain.DeviceState;
import com.tvoseguridadelectronica.OSS.Repository.DeviceStateDao;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping({"/api/deviceState"})
public class DeviceStateRestController {
	
	@Autowired
	private DeviceStateDao deviceStateDao;
	
	@GetMapping
	public ResponseEntity<List<DeviceState>> getAllDeviceStates() throws SQLException {

		List<DeviceState> list= deviceStateDao.deviceStatesAll();
		return new ResponseEntity<List<DeviceState>>(list, HttpStatus.OK);

	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeviceState> createDeviceState(@RequestBody final DeviceState deviceState) throws SQLException {

		deviceStateDao.saveDeviceState(deviceState);		
		return new ResponseEntity<DeviceState>(deviceState, HttpStatus.OK);

	}
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeviceState> updateDeviceState(@RequestBody final DeviceState deviceState) throws SQLException {

		deviceStateDao.updateDeviceState(deviceState);
		return new ResponseEntity<DeviceState>(deviceState, HttpStatus.NO_CONTENT);

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DeviceState> findDeviceStateById(@PathVariable("id") final int id ) throws SQLException {

		DeviceState deviceState = deviceStateDao.findDeviceStateById(id);
		return new ResponseEntity<DeviceState>(deviceState, HttpStatus.OK);

	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeviceState> deleteDeviceState(@PathVariable("id") final int id ) throws SQLException {

		deviceStateDao.deleteDeviceState(id);
		return new ResponseEntity<DeviceState>(HttpStatus.NO_CONTENT);

	}

}
