package com.example.demo.ServiciosImpl;
import com.example.demo.Entidades.Empleado;
import com.example.demo.Excepciones.EmailDuplicadoException;
import com.example.demo.Excepciones.EmpleadoNoEncontradoException;
import com.example.demo.Repositorios.DepartamentoRepositorio;
import com.example.demo.Repositorios.EmpleadoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepositorio empleadoRepository;

    @Mock
    private DepartamentoRepositorio departamentoRepository;

    @InjectMocks
    private EmpleadoServicioImpl empleadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardar_EmailDuplicado() {
        Empleado empleado = new Empleado();
        empleado.setEmail("test@mail.com");

        when(empleadoRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(empleado));

        assertThrows(EmailDuplicadoException.class, () -> empleadoService.guardar(empleado));
    }

    @Test
    void testGuardar_EmailNuevo() {
        Empleado empleado = new Empleado();
        empleado.setEmail("nuevo@mail.com");

        when(empleadoRepository.findByEmail("nuevo@mail.com")).thenReturn(Optional.empty());
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado result = empleadoService.guardar(empleado);

        assertEquals("nuevo@mail.com", result.getEmail());
        verify(empleadoRepository).save(empleado);
    }

    @Test
    void testBuscarPorId_Existente() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        Empleado result = empleadoService.buscarPorId(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmpleadoNoEncontradoException.class, () -> empleadoService.buscarPorId(1L));
    }

    @Test
    void testBuscarPorDepartamento() {
        Empleado e1 = new Empleado();
        Empleado e2 = new Empleado();
        when(empleadoRepository.findByNombreDepartamento("TI")).thenReturn(Arrays.asList(e1, e2));

        assertEquals(2, empleadoService.buscarPorDepartamento("TI").size());
    }

    @Test
    void testBuscarPorRangoSalario() {
        Empleado e1 = new Empleado();
        Empleado e2 = new Empleado();
        when(empleadoRepository.findBySalarioBetween(new BigDecimal("1000"), new BigDecimal("2000")))
                .thenReturn(Arrays.asList(e1, e2));

        assertEquals(2, empleadoService.buscarPorRangoSalario(new BigDecimal("1000"), new BigDecimal("2000")).size());
    }

    @Test
    void testObtenerSalarioPromedioPorDepartamento_ConValor() {
        when(empleadoRepository.findAverageSalarioByDepartamento(1L)).thenReturn(Optional.of(new BigDecimal("1500")));

        assertEquals(new BigDecimal("1500"), empleadoService.obtenerSalarioPromedioPorDepartamento(1L));
    }

    @Test
    void testObtenerSalarioPromedioPorDepartamento_SinValor() {
        when(empleadoRepository.findAverageSalarioByDepartamento(1L)).thenReturn(Optional.empty());

        assertEquals(BigDecimal.ZERO, empleadoService.obtenerSalarioPromedioPorDepartamento(1L));
    }

    @Test
    void testObtenerTodos() {
        when(empleadoRepository.findAll()).thenReturn(Collections.singletonList(new Empleado()));

        assertEquals(1, empleadoService.obtenerTodos().size());
    }

    @Test
    void testActualizar_Existente() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);

        when(empleadoRepository.existsById(1L)).thenReturn(true);
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado result = empleadoService.actualizar(1L, empleado);

        assertEquals(1L, result.getId());
    }

    @Test
    void testActualizar_NoExistente() {
        Empleado empleado = new Empleado();

        when(empleadoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EmpleadoNoEncontradoException.class, () -> empleadoService.actualizar(1L, empleado));
    }

    @Test
    void testEliminar_Existente() {
        when(empleadoRepository.existsById(1L)).thenReturn(true);

        empleadoService.eliminar(1L);

        verify(empleadoRepository).deleteById(1L);
    }

    @Test
    void testEliminar_NoExistente() {
        when(empleadoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EmpleadoNoEncontradoException.class, () -> empleadoService.eliminar(1L));
    }
}