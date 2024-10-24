package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.model.dto.ClienteDTO;
import com.cuenta.cuenta_ms.model.dto.CuentaDTO;
import com.cuenta.cuenta_ms.model.dto.TipoCuenta;
import com.cuenta.cuenta_ms.model.entities.Cliente;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import com.cuenta.cuenta_ms.model.entities.Movimiento;
import com.cuenta.cuenta_ms.model.mapper.CuentaMapper;
import com.cuenta.cuenta_ms.repository.CuentaRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaMapper cuentaMapper;

    @Mock
    private MovimientoService movimientoService;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @Test
    void testCreateCount() {
        Cliente cliente = new Cliente(1L, "password123", true, null);
        ClienteDTO clienteDTO = new ClienteDTO(1L,"password123",true );

        CuentaDTO cuentaDTO = new CuentaDTO(
                null,
                "123456",
                TipoCuenta.Ahorros,
                5000.0,
                true,
                clienteDTO
        );

        Cuenta cuenta = new Cuenta(
                1L,
                "123456",
                "Ahorros",
                5000.0,
                true,
                cliente
        );

        when(clienteService.findClienteById(1L)).thenReturn(cliente);
        when(cuentaMapper.toEntity(cuentaDTO)).thenReturn(cuenta);
        when(cuentaRepository.save(cuenta)).thenReturn(cuenta);
        when(cuentaMapper.toDTO(cuenta)).thenReturn(cuentaDTO);

        ResponseEntity<Object> response = cuentaService.createCount(1L, cuentaDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cuentaDTO, response.getBody());

        verify(clienteService, times(1)).findClienteById(1L);
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testUpdateCuenta() {
        Cuenta cuentaExistente = new Cuenta(
                1L,
                "123456",
                "Ahorros",
                5000.0,
                true,
                null
        );

        CuentaDTO cuentaDTO = new CuentaDTO(
                1L,
                "789012",
                TipoCuenta.Corriente,
                10000.0,
                false,
                null
        );

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuentaExistente));
        when(cuentaRepository.save(any(Cuenta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cuentaMapper.toDTO(any(Cuenta.class))).thenReturn(cuentaDTO);
        ResponseEntity<Object> response = cuentaService.updateCuenta(1L, cuentaDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CuentaDTO updatedCuenta = (CuentaDTO) response.getBody();
        assertEquals("789012", updatedCuenta.numeroCuenta());
        assertEquals(TipoCuenta.Corriente, updatedCuenta.tipoCuenta());
        assertEquals(10000.0, updatedCuenta.saldoInicial());
        assertFalse(updatedCuenta.estado());
        verify(cuentaRepository, times(1)).findById(1L);
        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }

    @Test
    void testDeleteCuenta() {
        Cuenta cuenta = new Cuenta(1L, "123456", "Ahorros", 5000.0, true, null);
        Movimiento movimiento = new Movimiento(1L, LocalDate.now(), "Depósito", 1000.0, 6000.0, 1L, "Activo", "Depósito inicial");

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(movimientoService.getMovimientosByCuenta(1L)).thenReturn(List.of(movimiento));
        ResponseEntity<Object> response = cuentaService.deleteCuenta(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(movimientoService, times(1)).delete(1L);
        verify(cuentaRepository, times(1)).delete(cuenta);
    }


}