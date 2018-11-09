package com.tvoseguridadelectronica.OSS.RestController;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvoseguridadelectronica.OSS.Domain.Device;
import com.tvoseguridadelectronica.OSS.Repository.DeviceDao;





@RestController
@RequestMapping({"/api/device"})
public class DeviceRestController {
	
	@Autowired
	private DeviceDao deviceDao;
	
	@GetMapping
	public ResponseEntity<List<Device>> getAllDevices() throws SQLException {

		List<Device> list= deviceDao.devicesAll();
		return new ResponseEntity<List<Device>>(list, HttpStatus.OK);

	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Device> createDevice(@RequestBody final Device device) throws SQLException {

		deviceDao.saveDevice(device);		
		return new ResponseEntity<Device>(device, HttpStatus.OK);

	}
	
	@PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Device> updateDevice(@RequestBody final Device device) throws SQLException {

		deviceDao.updateDevice(device);
		return new ResponseEntity<Device>(device, HttpStatus.NO_CONTENT);

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Device> findDeviceById(@PathVariable("id") final String id ) throws SQLException {

		Device device = deviceDao.findDeviceById(id);
		return new ResponseEntity<Device>(device, HttpStatus.OK);

	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Device> delete(@PathVariable("id") final String id ) throws SQLException {

		deviceDao.deleteDevice(id);
		return new ResponseEntity<Device>(HttpStatus.NO_CONTENT);

	}
	
	

}
