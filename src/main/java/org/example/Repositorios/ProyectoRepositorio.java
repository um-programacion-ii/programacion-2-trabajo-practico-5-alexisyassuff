package org.example.Repositorios;
import org.example.Entidades.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, Long> {
    Optional<Proyecto> findByNombre(String nombre);
    Optional<Proyecto> findById(Long id);
    List<Proyecto> findByFechaInicioAfter(LocalDate fechaInicio);
    List<Proyecto> findByFechaFinBefore(LocalDate fechaFin);
    List<Proyecto> findByEmpleados_Id(Long empleadoId);

    @Query("SELECT p FROM Proyecto p WHERE p.fechaInicio <= :fechaActual AND (p.fechaFin IS NULL OR p.fechaFin >= :fechaActual)")
    List<Proyecto> findProyectosActivos(@Param("fechaActual") LocalDate fechaActual);

    @Query("SELECT p FROM Proyecto p WHERE SIZE(p.empleados) > :cantidad")
    List<Proyecto> findProyectosConMasDeXEmpleados(@Param("cantidad") int cantidad);


}