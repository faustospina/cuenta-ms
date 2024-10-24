package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.model.dto.CuentaReporte;
import com.cuenta.cuenta_ms.model.dto.MovimientoReporte;
import com.cuenta.cuenta_ms.model.dto.ReporteDTO;
import com.cuenta.cuenta_ms.model.entities.Cliente;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import com.cuenta.cuenta_ms.model.entities.Movimiento;
import com.cuenta.cuenta_ms.model.entities.Persona;
import com.cuenta.cuenta_ms.repository.CuentaRepository;
import com.cuenta.cuenta_ms.repository.MovimientoRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReporteServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private MovimientoRepository movimientoRepository;
    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private ReporteServiceImpl reporteService;

    @Test
    void testGenerarReporte_conDatos() {
        Long clienteId = 1L;
        LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
        LocalDate fechaFin = LocalDate.of(2023, 12, 31);

        Persona persona = new Persona();
        persona.setNombre("Juan Pérez");

        Cliente cliente = new Cliente();
        cliente.setPersona(persona);

        Cuenta cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("123456789");
        cuenta.setTipoCuenta("AHORROS");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setCliente(cliente);

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDate.of(2023, 5, 10));
        movimiento.setTipoMovimiento("DEPOSITO");
        movimiento.setValor(500.0);
        movimiento.setSaldo(1500.0);
        movimiento.setEstado("SUCCESS");
        movimiento.setDescripcion("Depósito exitoso");

        when(cuentaService.getCuentasByCliente(clienteId)).thenReturn(List.of(cuenta));
        when(movimientoRepository.findByCuentaIdAndFechaBetween(1L, fechaInicio, fechaFin))
                .thenReturn(List.of(movimiento));
        ResponseEntity<Object> response = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReporteDTO reporte = (ReporteDTO) response.getBody();
        assertNotNull(reporte);
        assertEquals("Juan Pérez", reporte.getCliente());
        assertEquals(1, reporte.getCuentas().size());

        CuentaReporte cuentaReporte = reporte.getCuentas().get(0);
        assertEquals("123456789", cuentaReporte.getNumeroCuenta());
        assertEquals("AHORROS", cuentaReporte.getTipoCuenta());
        assertEquals(1000.0, cuentaReporte.getSaldoInicial());

        List<MovimientoReporte> movimientos = cuentaReporte.getMovimientos();
        assertEquals(1, movimientos.size());
        MovimientoReporte movimientoReporte = movimientos.get(0);
        assertEquals("DEPOSITO", movimientoReporte.getTipoMovimiento());
        assertEquals(500.0, movimientoReporte.getValor());
    }

    @Test
    void testGenerarReporte_sinDatos() {
        // Simular que el cliente no tiene cuentas
        Long clienteId = 2L;
        LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
        LocalDate fechaFin = LocalDate.of(2023, 12, 31);

        when(cuentaService.getCuentasByCliente(clienteId)).thenReturn(List.of());

        // Ejecutamos el método
        ResponseEntity<Object> response = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReporteDTO reporte = (ReporteDTO) response.getBody();
        assertNotNull(reporte);
        assertEquals("", reporte.getCliente());
        assertTrue(reporte.getCuentas().isEmpty());
    }
}