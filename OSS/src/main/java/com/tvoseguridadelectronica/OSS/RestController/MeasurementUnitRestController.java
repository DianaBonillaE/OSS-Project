package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.MeasurementUnit;
import com.tvoseguridadelectronica.OSS.Repository.MeasurementUnitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/measurementunit"})
public class MeasurementUnitRestController {

    private MeasurementUnitDao measurementUnitDao;

    @Autowired
    public void setMeasurementUnitDao(MeasurementUnitDao measurementUnitDao) {
        this.measurementUnitDao = measurementUnitDao;
    }

    @PostMapping(value = "/addMeasurementUnit")
    public ResponseEntity<MeasurementUnit> createMeasurementUnit(
            @RequestBody final MeasurementUnit measurementUnit) {

        MeasurementUnit measurementUnitCreate = measurementUnit;

        int id;
        String confirm;
        try {
            id = measurementUnitDao.addMeasurementUnit(measurementUnitCreate);
        } catch (Exception e) {
            return null;
        }

        if (id > 0) {
            confirm = "Unidad de Medida añadida con éxito";
        } else {
            confirm = "La unidad de Medida no fue añadida";
        }
        return new ResponseEntity<MeasurementUnit>(measurementUnitCreate, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<MeasurementUnit>> searchByName(@PathVariable("name") final String name) {
        List<MeasurementUnit> measurementUnits = measurementUnitDao.find(name);
        return new ResponseEntity<List<MeasurementUnit>>(measurementUnits, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<MeasurementUnit> searchById(@PathVariable("id") final int id) {
        MeasurementUnit measurementUnits = measurementUnitDao.findById(id);
        return new ResponseEntity<MeasurementUnit>(measurementUnits, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<MeasurementUnit>> getAll() {
        List<MeasurementUnit> measurementUnits = measurementUnitDao.getAll();
        return new ResponseEntity<List<MeasurementUnit>>(measurementUnits, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MeasurementUnit> updateMeasurementUnit(
            @PathVariable("id") final int id,
            @RequestBody final MeasurementUnit measurementUnit) throws SQLException {

        measurementUnitDao.updateMeasurementUnit(id, measurementUnit.getName());

        MeasurementUnit measurementUnitNew = (MeasurementUnit) measurementUnitDao.findById(id);

        return new ResponseEntity<MeasurementUnit>(measurementUnitNew, HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MeasurementUnit> deleteMeasurementUnit(@PathVariable("id") final int id) {

        try {
            measurementUnitDao.deleteMeasurementUnit(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<MeasurementUnit>(HttpStatus.NO_CONTENT);
    }

}
