package com.tvoseguridadelectronica.OSS.RestController;


import com.tvoseguridadelectronica.OSS.Domain.Client;
import com.tvoseguridadelectronica.OSS.Repository.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/client")
public class ClientRestController {

    private ClientDao clientDao;

    @Autowired
    public ClientRestController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Client> insertClient(@RequestBody final Client client){

        Client clientCreate = null;

        try{
            clientDao.insertClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Client>(clientCreate, HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") final String id, @RequestBody final Client client){

        Client clientActual = clientDao.findById(id);
        clientActual.setId(id);
        clientActual.setName(client.getName());
        clientActual.setContactName(client.getContactName());

        clientDao.updateClient(clientActual);

        return new ResponseEntity<Client>( client,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") final String id){

        try {
         clientDao.deleteClient(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }

}
