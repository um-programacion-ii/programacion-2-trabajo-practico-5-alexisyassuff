package com.example.demo.Controladores;
import com.example.demo.Entidades.Departamento;
import com.example.demo.Servicios.DepartamentoServicios;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartamentoControladores.class)
class DepartamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoServicios departamentoService;

    @Test
    void obtenerTodos() throws Exception {
        Departamento d1 = new Departamento();
        Departamento d2 = new Departamento();
        Mockito.when(departamentoService.obtenerTodos()).thenReturn(Arrays.asList(d1, d2));

        mockMvc.perform(get("/api/departamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerPorId() throws Exception {
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNombre("TI");
        Mockito.when(departamentoService.buscarPorId(1L)).thenReturn(departamento);

        mockMvc.perform(get("/api/departamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crear() throws Exception {
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNombre("TI");
        Mockito.when(departamentoService.guardar(any(Departamento.class))).thenReturn(departamento);

        mockMvc.perform(post("/api/departamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"TI\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("TI"));
    }

    @Test
    void actualizar() throws Exception {
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNombre("TI actualizado");
        Mockito.when(departamentoService.actualizar(eq(1L), any(Departamento.class))).thenReturn(departamento);

        mockMvc.perform(put("/api/departamentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"TI actualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("TI actualizado"));
    }

    @Test
    void eliminar() throws Exception {
        Mockito.doNothing().when(departamentoService).eliminar(1L);

        mockMvc.perform(delete("/api/departamentos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerPorEmpleadoId() throws Exception {
        Departamento d1 = new Departamento();
        Departamento d2 = new Departamento();
        Mockito.when(departamentoService.buscarPorEmpleadoId(1L)).thenReturn(Arrays.asList(d1, d2));

        mockMvc.perform(get("/api/departamentos/empleado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}