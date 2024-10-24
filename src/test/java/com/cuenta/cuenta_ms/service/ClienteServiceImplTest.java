package com.cuenta.cuenta_ms.service;

import com.cuenta.cuenta_ms.exception.NotFoundException;
import com.cuenta.cuenta_ms.model.entities.Cliente;
import com.cuenta.cuenta_ms.model.entities.Persona;
import com.cuenta.cuenta_ms.repository.ClienteRepository;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void testFindClienteById_WhenClienteExists() {
        Persona persona = new Persona(
                1L,
                "Juan Pérez",
                "Masculino",
                30,
                "123456789",
                "Calle Falsa 123",
                "555-1234"
        );

        Cliente cliente = new Cliente(
                1L,
                "password123",
                true,
                persona
        );

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));


        Cliente result = clienteService.findClienteById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("password123", result.getContrasena());
        assertTrue(result.isEstado());

        Persona resultPersona = result.getPersona();
        assertEquals("Juan Pérez", resultPersona.getNombre());
        assertEquals("Masculino", resultPersona.getGenero());
        assertEquals(30, resultPersona.getEdad());
        assertEquals("123456789", resultPersona.getIdentificacion());
        assertEquals("Calle Falsa 123", resultPersona.getDireccion());
        assertEquals("555-1234", resultPersona.getTelefono());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testFindClienteById_WhenClienteDoesNotExist() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                clienteService.findClienteById(1L)
        );
        assertEquals("Not found client", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete_WhenClienteExists() {
        Persona persona = new Persona(
                2L,
                "Ana Gómez",
                "Femenino",
                25,
                "987654321",
                "Avenida Siempreviva 742",
                "555-6789"
        );

        Cliente cliente = new Cliente(
                2L,
                "securePass",
                false,
                persona
        );

        ResponseEntity<Object> response = clienteService.delete(cliente);
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(clienteRepository, times(1)).delete(cliente);
    }
}