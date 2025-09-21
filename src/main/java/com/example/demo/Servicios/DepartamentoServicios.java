package com.example.demo.Servicios;
import com.example.demo.Entidades.Departamento;

import java.util.List;

public interface DepartamentoServicios {
    Departamento guardar(Departamento departamento);
    Departamento buscarPorId(Long Id);
    List<Departamento> buscarPorEmpleadoId(Long id);
    List<Departamento> obtenerTodos();
    Departamento actualizar(Long id, Departamento departamento);
    void eliminar(Long id);
}
