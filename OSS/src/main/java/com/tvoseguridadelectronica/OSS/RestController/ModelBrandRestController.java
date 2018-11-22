package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.ModelBrand;
import com.tvoseguridadelectronica.OSS.Repository.ModelBrandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping("/api/modelbrand")
public class ModelBrandRestController {
    private ModelBrandDao modelBrandDao;

    @Autowired
    public void setModelBrandDao(ModelBrandDao modelBrandDao) { this.modelBrandDao = modelBrandDao; }

    @GetMapping("/")
    public ResponseEntity<List<ModelBrand>> listAllModelBrand(){
        List<ModelBrand> modelBrand = modelBrandDao.findAll();
        return new ResponseEntity<List<ModelBrand>>(modelBrand, HttpStatus.OK);
    }


    @GetMapping("/{modelBrandId}")
    public ResponseEntity<ModelBrand> modelBrandGetById(@PathVariable("modelBrandId") final Integer id){
        ModelBrand modelBrand = modelBrandDao.findById(id);
        return new ResponseEntity<ModelBrand>(modelBrand, HttpStatus.OK);
    }

    @PostMapping(value="/insertModelBrand")
    public ResponseEntity<ModelBrand> createModelBrand(@RequestBody final ModelBrand modelBrand){

        ModelBrand modelBrandCreate= null;

        try {
            modelBrandDao.insert(modelBrand);
            System.out.println(modelBrandCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ModelBrand>(modelBrandCreate,HttpStatus.CREATED);
    }

    @PutMapping(value="/updateModelBrand")
    public ResponseEntity<ModelBrand> updateModelBrand(@RequestBody  final ModelBrand modelBrand){
        ModelBrand modelBrandUpdate= null;
        try {
            modelBrandDao.update(modelBrand);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ModelBrand>( modelBrandUpdate,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ModelBrand> deleteModelBrand (@PathVariable("id") final Integer id){
        System.out.println(id);
        try {
            modelBrandDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ModelBrand>(HttpStatus.NO_CONTENT);
    }
}
