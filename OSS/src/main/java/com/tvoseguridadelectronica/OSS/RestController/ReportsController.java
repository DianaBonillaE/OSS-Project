package com.tvoseguridadelectronica.OSS.RestController;

import com.tvoseguridadelectronica.OSS.Domain.ReportWoDate;
import com.tvoseguridadelectronica.OSS.Repository.ReportsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/reports"})
public class ReportsController{

    ReportsDao reportsDao;

    @Autowired
    public void setReportsDao(ReportsDao reportsDao) {
        this.reportsDao=reportsDao;
    }

    @PostMapping("/")
    public ResponseEntity<List<ReportWoDate>> getAll(
            @RequestBody final ReportWoDate consulta) {
        System.out.println("******** "+consulta);
        List<ReportWoDate> reportWoDates = reportsDao.searchWoDate(consulta.getNameClient(),consulta.getDate());
        return new ResponseEntity<List<ReportWoDate>>(reportWoDates, HttpStatus.OK);
    }

}
