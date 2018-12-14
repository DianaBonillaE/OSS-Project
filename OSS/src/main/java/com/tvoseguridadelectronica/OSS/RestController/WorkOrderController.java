package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.WorkOrder;
import com.tvoseguridadelectronica.OSS.Domain.WorkOrderType;
import com.tvoseguridadelectronica.OSS.Repository.WorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/workorder"})
public class WorkOrderController {

    private WorkOrderDao workOrderDao;

    @Autowired
    public void setWorkOrderDao(WorkOrderDao workOrderDao) {
        this.workOrderDao = workOrderDao;
    }

    @PostMapping(value = "/addWorkOrder")
    public ResponseEntity<WorkOrder> createWorkOrder(
            @RequestBody final WorkOrder workOrder) {
        WorkOrder workOrderCreate = workOrder;

        int id;
        String confirm;
        try {

            id = workOrderDao.addWorkOrder(workOrderCreate);

            for (int i = 0; i < workOrder.getEmployees().size() ; i++) {
                workOrderDao.addEmployees(id,workOrder.getEmployees().get(i).getId());
            }

        } catch (Exception e) {
            return null;
        }

        if (id > 0) {
            confirm = "La orden de trabajo fue añadida con éxito";
        } else {
            confirm = "La orden de trabajo no fue añadida";
        }
        return new ResponseEntity<WorkOrder>(workOrderCreate, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<WorkOrder>> getAll() {
        List<WorkOrder> workOrderList = workOrderDao.getAll();
        return new ResponseEntity<List<WorkOrder>>(workOrderList, HttpStatus.OK);
    }
    
    @GetMapping("/withoutEmployee/")
    public ResponseEntity<List<WorkOrder>> getAllWithoutEmployee() {
        List<WorkOrder> workOrderList = workOrderDao.getAllWithoutEmployee();
        return new ResponseEntity<List<WorkOrder>>(workOrderList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<WorkOrder> updateWorkOrder(
            @PathVariable("id") final int id,
            @RequestBody final WorkOrder workOrder) throws SQLException {

        workOrderDao.updateWorkOrder(id, workOrder);

        WorkOrder workOrderNew = (WorkOrder) workOrderDao.findById(id);

        return new ResponseEntity<WorkOrder>(workOrderNew, HttpStatus.OK);
    }

    
    @GetMapping("/find/{id}")
    public ResponseEntity<WorkOrder> searchById(@PathVariable("id") final int id) {
        WorkOrder workOrder = workOrderDao.findById(id);
        return new ResponseEntity<WorkOrder>(workOrder, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<WorkOrder> deleteWorkOrder(@PathVariable("id") final int id) {

        try {
           workOrderDao.deleteWorkOrder(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<WorkOrder>(HttpStatus.NO_CONTENT);
    }

}
