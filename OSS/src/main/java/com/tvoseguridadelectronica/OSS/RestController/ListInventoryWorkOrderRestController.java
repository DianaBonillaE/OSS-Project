package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.ListInventoryWorkOrder;
import com.tvoseguridadelectronica.OSS.Repository.ListInventoryWorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/ListInventoryWorkOrder")
public class ListInventoryWorkOrderRestController {

    @Autowired
    private ListInventoryWorkOrderDao listInventoryWorkOrderDao;

    @GetMapping("/")
    public ResponseEntity<List<ListInventoryWorkOrder>> listAllListInventory(){
        List<ListInventoryWorkOrder> listsWO = listInventoryWorkOrderDao.findAll();
        return new ResponseEntity<List<ListInventoryWorkOrder>>(listsWO, HttpStatus.OK);
    }

    @PostMapping(value="/asignar/{idWorkOrder}/{idListInventory}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListInventoryWorkOrder> createInventoryCategory(@PathVariable ("idWorkOrder") final Integer idWorkOrder,@PathVariable ("idListInventory") final Integer idListInventory ){

        ListInventoryWorkOrder listInventoryWorkOrderCreate= null;

        try {
            listInventoryWorkOrderDao.asignarLista(idWorkOrder, idListInventory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ListInventoryWorkOrder>(listInventoryWorkOrderCreate, HttpStatus.CREATED);
    }


}
