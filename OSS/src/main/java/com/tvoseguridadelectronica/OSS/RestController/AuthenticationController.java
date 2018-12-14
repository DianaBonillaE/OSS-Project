package com.tvoseguridadelectronica.OSS.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.tvoseguridadelectronica.OSS.Config.JwtTokenUtil;
import com.tvoseguridadelectronica.OSS.Domain.Employee;
import com.tvoseguridadelectronica.OSS.Domain.AuthToken;
import com.tvoseguridadelectronica.OSS.Service.EmployeeService;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmployeeService employeeService;
    
    
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody Employee employee) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		employee.getUsername(),
                		employee.getPassword()
                )
        );
       
        SecurityContextHolder.getContext().setAuthentication(authentication);
      final Employee employeeFinal = employeeService.findOne(employee.getUsername());
  
        final String token = jwtTokenUtil.generateToken(employeeFinal);
        return ResponseEntity.ok(new AuthToken(token));
    }

}
