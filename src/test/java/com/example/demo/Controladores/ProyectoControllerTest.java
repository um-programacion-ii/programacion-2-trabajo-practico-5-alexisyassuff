package com.example.demo.Controladores;
import com.example.demo.Entidades.Proyecto;
import com.example.demo.Servicios.ProyectoServicio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProyectoControladores.class)
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProyectoServicio proyectoService;

    @Test
    void obtenerTodos() throws Exception {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();
        Mockito.when(proyectoService.obtenerTodos()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/proyectos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerPorId() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        Mockito.when(proyectoService.buscarPorId(1L)).thenReturn(proyecto);

        mockMvc.perform(get("/api/proyectos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerPorNombre() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Test");
        Mockito.when(proyectoService.buscarPorNombre("Test")).thenReturn(proyecto);

        mockMvc.perform(get("/api/proyectos/nombre/Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test"));
    }

    @Test
    void crear() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Nuevo");
        Mockito.when(proyectoService.guardar(any(Proyecto.class))).thenReturn(proyecto);

        mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nuevo\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nuevo"));
    }

    @Test
    void actualizar() throws Exception {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Actualizado");
        Mockito.when(proyectoService.actualizar(eq(1L), any(Proyecto.class))).thenReturn(proyecto);

        mockMvc.perform(put("/api/proyectos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Actualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizado"));
    }

    @Test
    void eliminar() throws Exception {
        Mockito.doNothing().when(proyectoService).eliminar(1L);

        mockMvc.perform(delete("/api/proyectos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerFechaInicioDespues() throws Exception {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();
        LocalDate fecha = LocalDate.of(2025, 1, 1);
        Mockito.when(proyectoService.buscarPorFechaInicioDespues(fecha)).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/proyectos/fecha/despues?fechaInicio=2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerFechaFinAntes() throws Exception {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();
        LocalDate fecha = LocalDate.of(2025, 1, 1);
        Mockito.when(proyectoService.buscarPorFechaFinAntes(fecha)).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/proyectos/fecha/antes?fechaFin=2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerPorEmpleadoId() throws Exception {
        Proyecto p1 = new Proyecto();
        Proyecto p2 = new Proyecto();
        Mockito.when(proyectoService.buscarPorEmpleadoId(1L)).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/proyectos/empleado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}