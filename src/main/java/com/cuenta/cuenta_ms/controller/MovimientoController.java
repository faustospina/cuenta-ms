package com.cuenta.cuenta_ms.controller;

import com.cuenta.cuenta_ms.model.dto.MovimientoDTO;
import com.cuenta.cuenta_ms.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @GetMapping
    public ResponseEntity<Object> getAll(){
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody MovimientoDTO request){
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return service.delete(id);
    }

}
