package com.tvoseguridadelectronica.OSS.RestController;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600) //direccion de angular
@RestController
@RequestMapping({"/api/measurementunit"})
public class MeasurementUnitRestController {

    @PostMapping(value = "/addMeasurementUnit")
    public String addCart(
            @RequestParam("name") String name) {

        String confirm="";
        try {

            String[] nameMeasurementBrand = name.split(",");
            //measurement

            //confirm = orderDetailDao.onCart(productId,units,sesion);
        }catch (Exception e) {
            return null;
        }

        if(confirm.equals("insert")){
            confirm= "producto añadido con éxito";
        }else{
            confirm = "producto no añadido";
        }
        return confirm;
    }


}
