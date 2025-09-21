package com.example.demo.Controladores;
import com.example.demo.Entidades.Empleado;
import com.example.demo.Servicios.EmpleadoServicio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoControladores.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoServicio empleadoService;

    @Test
    void obtenerTodos() throws Exception {
        Empleado e1 = new Empleado();
        Empleado e2 = new Empleado();
        Mockito.when(empleadoService.obtenerTodos()).thenReturn(Arrays.asList(e1, e2));

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerPorId() throws Exception {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setEmail("test@mail.com");
        Mockito.when(empleadoService.buscarPorId(1L)).thenReturn(empleado);

        mockMvc.perform(get("/api/empleados/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crear() throws Exception {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setEmail("nuevo@mail.com");
        Mockito.when(empleadoService.guardar(any(Empleado.class))).thenReturn(empleado);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"nuevo@mail.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("nuevo@mail.com"));
    }

    @Test
    void actualizar() throws Exception {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setEmail("actualizado@mail.com");
        Mockito.when(empleadoService.actualizar(eq(1L), any(Empleado.class))).thenReturn(empleado);

        mockMvc.perform(put("/api/empleados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"actualizado@mail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("actualizado@mail.com"));
    }

    @Test
    void eliminar() throws Exception {
        Mockito.doNothing().when(empleadoService).eliminar(1L);

        mockMvc.perform(delete("/api/empleados/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerPorDepartamento() throws Exception {
        Empleado e1 = new Empleado();
        Empleado e2 = new Empleado();
        Mockito.when(empleadoService.buscarPorDepartamento("TI")).thenReturn(Arrays.asList(e1, e2));

        mockMvc.perform(get("/api/empleados/departamento/TI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerPorRangoSalario() throws Exception {
        Empleado e1 = new Empleado();
        Empleado e2 = new Empleado();
        Mockito.when(empleadoService.buscarPorRangoSalario(new BigDecimal("1000"), new BigDecimal("2000")))
                .thenReturn(Arrays.asList(e1, e2));

        mockMvc.perform(get("/api/empleados/salario?min=1000&max=2000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void salarioPromedioDepartamento() throws Exception {
        Mockito.when(empleadoService.obtenerSalarioPromedioPorDepartamento(1L)).thenReturn(new BigDecimal("1500"));

        mockMvc.perform(get("/api/empleados/promedio/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1500"));
    }
}