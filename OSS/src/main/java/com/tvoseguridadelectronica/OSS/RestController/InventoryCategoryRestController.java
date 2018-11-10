package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.InventoryCategory;
import com.tvoseguridadelectronica.OSS.Repository.InventoryCategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/inventoryCategory")
public class InventoryCategoryRestController {

	@Autowired
    private InventoryCategoryDao inventorycategoryDao;

   

    @GetMapping("/")
    public ResponseEntity<List<InventoryCategory>> listAllCategories(){
        List<InventoryCategory> categories = inventorycategoryDao.findAll();
        return new ResponseEntity<List<InventoryCategory>>(categories,HttpStatus.OK);
    }

    @PostMapping(value="/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryCategory> createInventoryCategory(@RequestBody  InventoryCategory inventorycategory){

        InventoryCategory inventorycategoryCreate= null;

        try {
            inventorycategoryDao.insert(inventorycategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<InventoryCategory>(inventorycategoryCreate, HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<InventoryCategory> updateInventoryCategory(@PathVariable ("id") final Integer id, @RequestBody  final InventoryCategory inventorycategory){

        InventoryCategory inventorycategoryActual = inventorycategoryDao.findById(id);
        inventorycategoryActual.setName(inventorycategory.getName());
        inventorycategoryDao.update(inventorycategoryActual);
        return new ResponseEntity<InventoryCategory>(inventorycategory,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<InventoryCategory> deleteInventoryCategory (@PathVariable("id") final Integer id){
        System.out.println(id);
        try {
            inventorycategoryDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<InventoryCategory>(HttpStatus.NO_CONTENT);
    }


}
