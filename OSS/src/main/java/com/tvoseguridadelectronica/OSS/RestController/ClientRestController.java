package com.tvoseguridadelectronica.OSS.RestController;


import com.tvoseguridadelectronica.OSS.Domain.Client;
import com.tvoseguridadelectronica.OSS.Repository.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientRestController {

    private ClientDao clientDao;

    @Autowired
    public ClientRestController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClients(){
        List<Client> clients = clientDao.findAllClients();
        return new ResponseEntity<List<Client>>(clients,HttpStatus.OK);
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

    @PutMapping(value="/updateClient")
    public ResponseEntity<Client> updateClient(@RequestBody final Client client){

        Client clientActual = null;
        clientDao.updateClient(client);
        return new ResponseEntity<Client>( clientActual,HttpStatus.OK);
    }

    /*public ResponseEntity<ModelBrand> updateModelBrand(@RequestBody  final ModelBrand modelBrand){
        ModelBrand modelBrandUpdate= null;
        try {
            modelBrandDao.update(modelBrand);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ModelBrand>( modelBrandUpdate,HttpStatus.OK);
    }*/

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
