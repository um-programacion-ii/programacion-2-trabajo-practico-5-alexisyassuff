package org.example.Servicios;
import org.example.Entidades.*;
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
