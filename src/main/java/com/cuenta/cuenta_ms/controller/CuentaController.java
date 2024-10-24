package com.cuenta.cuenta_ms.controller;

import com.cuenta.cuenta_ms.model.dto.CuentaDTO;
import com.cuenta.cuenta_ms.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cuentas")
public class CuentaController {
    @Autowired
    private CuentaService service;

    @PostMapping("/cliente/{id}")
    public ResponseEntity<Object> createCuenta(@PathVariable Long id, @RequestBody CuentaDTO request){
        return service.createCount(id,request);
    }
    @GetMapping
    public ResponseEntity<Object> getAll(){
       return service.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCuenta(@PathVariable Long id,@RequestBody CuentaDTO request){
        return service.updateCuenta(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
       return service.deleteCuenta(id);
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable Long id){
        return service.deleteCliente(id);
    }




}
