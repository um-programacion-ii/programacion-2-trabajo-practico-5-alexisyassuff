package org.example.Servicios;
import org.example.Entidades.*;
import org.example.Repositorios.*;
import java.util.List;

public interface DepartamentoServicios {
    Departamento guardar(Departamento departamento);
    Departamento buscarPorId(Long Id);
    List<Departamento> buscarPorEmpleadoId(Long id);
    List<Departamento> obtenerTodos();
    Departamento actualizar(Long id, Departamento departamento);
    void eliminar(Long id);
}
