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
/*
    @GetMapping("/lista")
    public ResponseEntity<List<ProductCategory>> listAllCategoriesNotProduct(){
        List<ProductCategory> categories = categoryDao.findNotProduct();
        return new ResponseEntity<List<ProductCategory>>(categories,HttpStatus.OK);
    }

    @GetMapping("/{productCategoryId}")
    public ResponseEntity<ProductCategory> productCategoryById(@PathVariable("productCategoryId") final Integer id){
        ProductCategory productCategory = categoryDao.findById(id);
        return new ResponseEntity<ProductCategory>(productCategory, HttpStatus.OK);
    }*/

    @PostMapping(value="/insertModelBrand")
    public ResponseEntity<ModelBrand> createModelBrand(@RequestBody final ModelBrand modelBrand){

        ModelBrand modelBrandCreate= null;

        try {
            modelBrandDao.insert(modelBrand);
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
