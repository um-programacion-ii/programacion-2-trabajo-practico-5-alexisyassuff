package com.example.demo.ServiciosImpl;
import com.example.demo.Entidades.Departamento;
import com.example.demo.Entidades.Empleado;
import com.example.demo.Excepciones.DepartamentoDuplicadoException;
import com.example.demo.Excepciones.DepartamentoNoEncontradoException;
import com.example.demo.Excepciones.OperacionNoPermitidaException;
import com.example.demo.Repositorios.DepartamentoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartamentoServiceImplTest {

    @Mock
    private DepartamentoRepositorio departamentoRepository;

    @InjectMocks
    private DepartamentoServiciosImpl departamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardar_DepartamentoDuplicado() {
        Departamento departamento = new Departamento();
        departamento.setNombre("TI");

        when(departamentoRepository.findByNombre("TI")).thenReturn(Optional.of(departamento));

        assertThrows(DepartamentoDuplicadoException.class, () -> departamentoService.guardar(departamento));
    }

    @Test
    void testGuardar_DepartamentoNuevo() {
        Departamento departamento = new Departamento();
        departamento.setNombre("RRHH");

        when(departamentoRepository.findByNombre("RRHH")).thenReturn(Optional.empty());
        when(departamentoRepository.save(departamento)).thenReturn(departamento);

        Departamento result = departamentoService.guardar(departamento);

        assertEquals("RRHH", result.getNombre());
        verify(departamentoRepository).save(departamento);
    }

    @Test
    void testBuscarPorId_Existente() {
        Departamento departamento = new Departamento();
        departamento.setId(1L);

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));

        Departamento result = departamentoService.buscarPorId(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DepartamentoNoEncontradoException.class, () -> departamentoService.buscarPorId(1L));
    }

    @Test
    void testObtenerTodos_ConDepartamentos() {
        Departamento d1 = new Departamento();
        Departamento d2 = new Departamento();
        when(departamentoRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        assertEquals(2, departamentoService.obtenerTodos().size());
    }

    @Test
    void testObtenerTodos_Vacio() {
        when(departamentoRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DepartamentoNoEncontradoException.class, () -> departamentoService.obtenerTodos());
    }

    @Test
    void testActualizar_CambioNombreDuplicado() {
        Departamento existente = new Departamento();
        existente.setId(1L);
        existente.setNombre("TI");

        Departamento nuevo = new Departamento();
        nuevo.setNombre("RRHH");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(departamentoRepository.findByNombre("RRHH")).thenReturn(Optional.of(nuevo));

        assertThrows(DepartamentoDuplicadoException.class, () -> departamentoService.actualizar(1L, nuevo));
    }

    @Test
    void testActualizar_Exitoso() {
        Departamento existente = new Departamento();
        existente.setId(1L);
        existente.setNombre("TI");

        Departamento nuevo = new Departamento();
        nuevo.setNombre("TI");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(departamentoRepository.findByNombre("TI")).thenReturn(Optional.empty());
        when(departamentoRepository.save(ArgumentMatchers.any())).thenReturn(nuevo);

        Departamento actualizado = departamentoService.actualizar(1L, nuevo);
        assertEquals("TI", actualizado.getNombre());
    }

    @Test
    void testEliminar_ConEmpleados() {
        Departamento existente = new Departamento();
        existente.setId(1L);
        existente.setEmpleados(Arrays.asList(new Empleado()));

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(existente));

        assertThrows(OperacionNoPermitidaException.class, () -> departamentoService.eliminar(1L));
    }

    @Test
    void testEliminar_SinEmpleados() {
        Departamento existente = new Departamento();
        existente.setId(1L);
        existente.setEmpleados(Collections.emptyList());

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(existente));

        departamentoService.eliminar(1L);

        verify(departamentoRepository).deleteById(1L);
    }
}