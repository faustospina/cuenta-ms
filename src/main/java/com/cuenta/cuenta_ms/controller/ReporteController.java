package com.cuenta.cuenta_ms.controller;

import com.cuenta.cuenta_ms.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping
    public ResponseEntity<Object> obtenerReporte(
            @RequestParam Long clienteId,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        return service.generarReporte(clienteId, fechaInicio, fechaFin);
    }

}
