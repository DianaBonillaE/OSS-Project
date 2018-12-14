package com.tvoseguridadelectronica.OSS.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tvoseguridadelectronica.OSS.Domain.Employee;
import com.tvoseguridadelectronica.OSS.Repository.EmployeeDao;
import java.util.Arrays;
import java.util.List;

@Service(value = "userService")
public class EmployeeServiceImpl implements UserDetailsService, EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String loginEmployee) throws UsernameNotFoundException {
		Employee employee = employeeDao.findByLogin(loginEmployee);
		
		return new org.springframework.security.core.userdetails.User(employee.getUsername(), employee.getPassword(),
				getAuthority(employee));
		
	}

	private List<SimpleGrantedAuthority> getAuthority(Employee employee ) {
				
		return Arrays.asList(new SimpleGrantedAuthority(employee.getRole().getName()));
	}

	@Override
	public Employee findOne(String loginEmployee) {
		return employeeDao.findByLogin(loginEmployee);
	}	


	
}
