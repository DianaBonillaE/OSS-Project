package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.Role;
import com.tvoseguridadelectronica.OSS.Repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/role"})
public class RoleController {

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @PostMapping(value = "/addRole")
    public ResponseEntity<Role> createRole(
            @RequestBody final Role role) {

        Role roleCreate = role;

        int id;
        String confirm;
        try {
            id = roleDao.addRole(roleCreate);
        } catch (Exception e) {
            return null;
        }

        if (id > 0) {
            confirm = "Unidad de Medida añadida con éxito";
        } else {
            confirm = "La unidad de Medida no fue añadida";
        }
        return new ResponseEntity<Role>(roleCreate, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Role>> searchByName(@PathVariable("name") final String name) {
        List<Role> roles = roleDao.find(name);
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Role> searchById(@PathVariable("id") final int id) {
        Role role = roleDao.findById(id);
        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getAll() {
        List<Role> roles = roleDao.getAll();
        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Role> updateRole(
            @PathVariable("id") final int id,
            @RequestBody final Role role) throws SQLException {

        roleDao.updateRole(id, role.getName(),role.getType());

        Role roleNew = (Role) roleDao.findById(id);

        return new ResponseEntity<Role>(roleNew, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable("id") final int id) {

        try {
            roleDao.deleteRole(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
    }
}
