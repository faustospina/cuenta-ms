package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.model.dto.CuentaReporte;
import com.cuenta.cuenta_ms.model.dto.MovimientoReporte;
import com.cuenta.cuenta_ms.model.dto.ReporteDTO;
import com.cuenta.cuenta_ms.model.entities.Cuenta;
import com.cuenta.cuenta_ms.repository.CuentaRepository;
import com.cuenta.cuenta_ms.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaService cuentaService;

    @Override
    public ResponseEntity<Object> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Cuenta> cuentas = cuentaService.getCuentasByCliente(clienteId);

        List<CuentaReporte> cuentasReporte = cuentas.stream().map(cuenta -> {
            List<MovimientoReporte> movimientosReporte = movimientoRepository
                    .findByCuentaIdAndFechaBetween(cuenta.getId(), fechaInicio, fechaFin)
                    .stream()
                    .map(mov -> new MovimientoReporte(
                            mov.getFecha(),
                            mov.getTipoMovimiento(),
                            mov.getValor(),
                            mov.getSaldo()
                    )).collect(Collectors.toList());

            return new CuentaReporte(
                    cuenta.getNumeroCuenta(),
                    cuenta.getTipoCuenta(),
                    cuenta.getSaldoInicial(),
                    movimientosReporte
            );
        }).collect(Collectors.toList());

        String cliente = cuentas.isEmpty() ? "" : cuentas.get(0).getCliente().getPersona().getNombre();

        return new ResponseEntity<>( new ReporteDTO(cliente, cuentasReporte), HttpStatus.OK);
    }
}
