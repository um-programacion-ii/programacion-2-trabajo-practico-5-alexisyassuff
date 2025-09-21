package com.example.demo.Repositorios;
import com.example.demo.Entidades.Empleado;
import com.example.demo.Entidades.Proyecto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("mysql") // Cambiar a "postgres" para probar con PostgreSQL
class ProyectoRepositorioTest {

    @Autowired
    private ProyectoRepositorio proyectoRepository;

    @Test
    @DisplayName("findByNombre funciona en MySQL/PostgreSQL")
    void testFindByNombre() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("TestProyecto");
        proyectoRepository.save(proyecto);

        Optional<Proyecto> encontrado = proyectoRepository.findByNombre("TestProyecto");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("TestProyecto");
    }

    @Test
    @DisplayName("findByFechaInicioAfter funciona en MySQL/PostgreSQL")
    void testFindByFechaInicioAfter() {
        Proyecto proyecto = new Proyecto();
        proyecto.setFechaInicio(LocalDate.of(2025, 2, 1));
        proyectoRepository.save(proyecto);

        List<Proyecto> lista = proyectoRepository.findByFechaInicioAfter(LocalDate.of(2025, 1, 1));
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("findByFechaFinBefore funciona en MySQL/PostgreSQL")
    void testFindByFechaFinBefore() {
        Proyecto proyecto = new Proyecto();
        proyecto.setFechaFin(LocalDate.of(2025, 1, 1));
        proyectoRepository.save(proyecto);

        List<Proyecto> lista = proyectoRepository.findByFechaFinBefore(LocalDate.of(2025, 2, 1));
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("findByEmpleados_Id funciona en MySQL/PostgreSQL")
    void testFindByEmpleadosId() {
        Proyecto proyecto = new Proyecto();
        Empleado emp = new Empleado();
        proyecto.setEmpleados(Set.of(emp));
        proyectoRepository.save(proyecto);

        List<Proyecto> lista = proyectoRepository.findByEmpleados_Id(emp.getId());
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("findProyectosActivos funciona en MySQL/PostgreSQL")
    void testFindProyectosActivos() {
        Proyecto proyecto = new Proyecto();
        proyecto.setFechaInicio(LocalDate.now().minusDays(1));
        proyecto.setFechaFin(LocalDate.now().plusDays(1));
        proyectoRepository.save(proyecto);

        List<Proyecto> lista = proyectoRepository.findProyectosActivos(LocalDate.now());
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("findProyectosConMasDeXEmpleados funciona en MySQL/PostgreSQL")
    void testFindProyectosConMasDeXEmpleados() {
        Proyecto proyecto = new Proyecto();
        Empleado emp1 = new Empleado();
        Empleado emp2 = new Empleado();
        proyecto.setEmpleados(Set.of(emp1, emp2));
        proyectoRepository.save(proyecto);

        List<Proyecto> lista = proyectoRepository.findProyectosConMasDeXEmpleados(1);
        assertThat(lista).isNotEmpty();
    }
}