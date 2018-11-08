package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.MeasurementUnit;
import com.tvoseguridadelectronica.OSS.Repository.MeasurementUnitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/measurementunit"})
public class MeasurementUnitRestController {

   private MeasurementUnitDao measurementUnitDao;

    @Autowired
    public void setMeasurementUnitDao (MeasurementUnitDao measurementUnitDao){
        this.measurementUnitDao=measurementUnitDao;
    }

    @PostMapping(value = "/addMeasurementUnit")
    public ResponseEntity<MeasurementUnit> createMeasurementUnit(
            @RequestBody final MeasurementUnit measurementUnit){

        MeasurementUnit measurementUnitCreate = measurementUnit;

        int id;
        String confirm;
        try {
            System.out.println(measurementUnitCreate);
             id= measurementUnitDao.addMeasurementUnit(measurementUnitCreate);
        }catch (Exception e) {
            return null;
        }

        if(id>0){
             confirm= "Unidad de Medida añadida con éxito";
        }else{
            confirm = "La unidad de Medida no fue añadida";
        }
        return new ResponseEntity<MeasurementUnit>(measurementUnitCreate, HttpStatus.CREATED);
    }


}
