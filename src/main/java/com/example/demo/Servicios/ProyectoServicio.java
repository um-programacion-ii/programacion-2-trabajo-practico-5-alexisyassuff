package com.example.demo.Servicios;
import com.example.demo.Entidades.Proyecto;

import java.util.List;
import java.time.LocalDate;

public interface ProyectoServicio {
    Proyecto guardar(Proyecto proyecto);
    Proyecto buscarPorId(Long id);
    Proyecto buscarPorNombre(String nombre);
    List<Proyecto> buscarPorFechaInicioDespues(LocalDate fechaInicio);
    List<Proyecto> buscarPorFechaFinAntes(LocalDate fechaFina);
    List<Proyecto> buscarPorEmpleadoId(Long empleadoId);
    List<Proyecto> obtenerTodos();
    Proyecto actualizar(Long id, Proyecto proyecto);
    void eliminar(Long id);
}
