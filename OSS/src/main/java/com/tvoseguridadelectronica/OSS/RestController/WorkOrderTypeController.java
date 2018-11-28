package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.WorkOrderType;
import com.tvoseguridadelectronica.OSS.Repository.WorkOrderTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/workordertype"})
public class WorkOrderTypeController {

    private WorkOrderTypeDao workOrderTypeDao;

    @Autowired
    public void setWorkOrderTypeDao(WorkOrderTypeDao workOrderTypeDao) {
        this.workOrderTypeDao = workOrderTypeDao;
    }

    @PostMapping(value = "/addWorkOrderType")
    public ResponseEntity<WorkOrderType> createWorkOrderType(
            @RequestBody final WorkOrderType workOrderType) {
        WorkOrderType workOrderTypeCreate = workOrderType;

        int id;
        String confirm;
        try {
            id = workOrderTypeDao.addWorkOrderType(workOrderTypeCreate);
        } catch (Exception e) {
            return null;
        }

        if (id > 0) {
            confirm = "Tipo de orden de trabajo añadido con éxito";
        } else {
            confirm = "Tipo de orden de trabajo no fue añadido";
        }
        return new ResponseEntity<WorkOrderType>(workOrderTypeCreate, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<WorkOrderType>> searchByName(@PathVariable("name") final String name) {
        List<WorkOrderType> workOrderTypeList= workOrderTypeDao.find(name);
        return new ResponseEntity<List<WorkOrderType>>(workOrderTypeList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<WorkOrderType> searchById(@PathVariable("id") final int id) {
        WorkOrderType workOrderType = workOrderTypeDao.findById(id);
        return new ResponseEntity<WorkOrderType>(workOrderType, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<WorkOrderType>> getAll() {
        List<WorkOrderType> workOrderTypeList = workOrderTypeDao.getAll();
        return new ResponseEntity<List<WorkOrderType>>(workOrderTypeList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<WorkOrderType> updateMeasurementUnit(
            @PathVariable("id") final int id,
            @RequestBody final WorkOrderType workOrderType) throws SQLException {

        workOrderTypeDao.updateWorkOrderType(id, workOrderType.getName());

        WorkOrderType workOrderTypeNew = (WorkOrderType) workOrderTypeDao.findById(id);

        return new ResponseEntity<WorkOrderType>(workOrderTypeNew, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<WorkOrderType> deleteWorkOrderType(@PathVariable("id") final int id) {

        try {
            workOrderTypeDao.deleteWorkOrderType(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<WorkOrderType>(HttpStatus.NO_CONTENT);
    }
}
