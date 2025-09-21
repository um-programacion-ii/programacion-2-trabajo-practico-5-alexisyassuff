package com.example.demo.ServiciosImpl;
import com.example.demo.Entidades.Proyecto;
import com.example.demo.Excepciones.ProyectoDuplicadoException;
import com.example.demo.Excepciones.ProyectoNoEncontradoException;
import com.example.demo.Repositorios.ProyectoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProyectoServiceImplTest {

    @Mock
    private ProyectoRepositorio proyectoRepository;

    @InjectMocks
    private ProyectoServicioImpl proyectoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardar_ProyectoDuplicado() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Nuevo");

        when(proyectoRepository.findByNombre("Nuevo")).thenReturn(Optional.of(proyecto));

        assertThrows(ProyectoDuplicadoException.class, () -> proyectoService.guardar(proyecto));
    }

    @Test
    void testGuardar_ProyectoNuevo() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Otro");

        when(proyectoRepository.findByNombre("Otro")).thenReturn(Optional.empty());
        when(proyectoRepository.save(proyecto)).thenReturn(proyecto);

        Proyecto result = proyectoService.guardar(proyecto);

        assertEquals("Otro", result.getNombre());
        verify(proyectoRepository).save(proyecto);
    }

    @Test
    void testBuscarPorId_Existente() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));

        Proyecto result = proyectoService.buscarPorId(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(proyectoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.buscarPorId(1L));
    }

    @Test
    void testBuscarPorNombre_Existente() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test");

        when(proyectoRepository.findByNombre("Test")).thenReturn(Optional.of(proyecto));

        Proyecto result = proyectoService.buscarPorNombre("Test");

        assertEquals("Test", result.getNombre());
    }

    @Test
    void testBuscarPorNombre_NoEncontrado() {
        when(proyectoRepository.findByNombre("Test")).thenReturn(Optional.empty());

        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.buscarPorNombre("Test"));
    }

    @Test
    void testBuscarPorFechaInicioDespues_ConProyectos() {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();
        LocalDate fecha = LocalDate.now();

        when(proyectoRepository.findByFechaInicioAfter(fecha)).thenReturn(Arrays.asList(p1, p2));

        assertEquals(2, proyectoService.buscarPorFechaInicioDespues(fecha).size());
    }

    @Test
    void testBuscarPorFechaInicioDespues_SinProyectos() {
        LocalDate fecha = LocalDate.now();

        when(proyectoRepository.findByFechaInicioAfter(fecha)).thenReturn(Collections.emptyList());

        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.buscarPorFechaInicioDespues(fecha));
    }

    @Test
    void testBuscarPorFechaFinAntes_ConProyectos() {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();
        LocalDate fecha = LocalDate.now();

        when(proyectoRepository.findByFechaFinBefore(fecha)).thenReturn(Arrays.asList(p1, p2));

        assertEquals(2, proyectoService.buscarPorFechaFinAntes(fecha).size());
    }

    @Test
    void testBuscarPorFechaFinAntes_SinProyectos() {
        LocalDate fecha = LocalDate.now();

        when(proyectoRepository.findByFechaFinBefore(fecha)).thenReturn(Collections.emptyList());

        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.buscarPorFechaFinAntes(fecha));
    }

    @Test
    void testBuscarPorEmpleadoId_ConProyectos() {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();

        when(proyectoRepository.findByEmpleados_Id(1L)).thenReturn(Arrays.asList(p1, p2));

        assertEquals(2, proyectoService.buscarPorEmpleadoId(1L).size());
    }

    @Test
    void testBuscarPorEmpleadoId_SinProyectos() {
        when(proyectoRepository.findByEmpleados_Id(1L)).thenReturn(Collections.emptyList());

        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.buscarPorEmpleadoId(1L));
    }

    @Test
    void testObtenerTodos_ConProyectos() {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();

        when(proyectoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        assertEquals(2, proyectoService.obtenerTodos().size());
    }

    @Test
    void testObtenerTodos_SinProyectos() {
        when(proyectoRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.obtenerTodos());
    }

    @Test
    void testActualizar_CambioNombreDuplicado() {
        Proyecto existente = new Proyecto();
        existente.setId(1L);
        existente.setNombre("Original");

        Proyecto nuevo = new Proyecto();
        nuevo.setNombre("Duplicado");

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(proyectoRepository.findByNombre("Duplicado")).thenReturn(Optional.of(nuevo));

        assertThrows(ProyectoDuplicadoException.class, () -> proyectoService.actualizar(1L, nuevo));
    }

    @Test
    void testActualizar_Exitoso() {
        Proyecto existente = new Proyecto();
        existente.setId(1L);
        existente.setNombre("Original");

        Proyecto nuevo = new Proyecto();
        nuevo.setNombre("Original");

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(proyectoRepository.findByNombre("Original")).thenReturn(Optional.empty());
        when(proyectoRepository.save(ArgumentMatchers.any())).thenReturn(nuevo);

        Proyecto actualizado = proyectoService.actualizar(1L, nuevo);
        assertEquals("Original", actualizado.getNombre());
    }

    @Test
    void testEliminar_Existente() {
        Proyecto existente = new Proyecto();
        existente.setId(1L);

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(existente));

        proyectoService.eliminar(1L);

        verify(proyectoRepository).deleteById(1L);
    }

    @Test
    void testEliminar_NoExistente() {
        when(proyectoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProyectoNoEncontradoException.class, () -> proyectoService.eliminar(1L));
    }
}