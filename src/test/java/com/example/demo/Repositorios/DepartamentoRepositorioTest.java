package com.example.demo.Repositorios;
import com.example.demo.Entidades.Departamento;
import com.example.demo.Entidades.Empleado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("mysql") // "postgres"
class DepartamentoRepositorioTest {

    @Autowired
    private DepartamentoRepositorio departamentoRepository;

    @Autowired
    private EmpleadoRepositorio empleadoRepository;

    @Test
    @DisplayName("findByNombre funciona en MySQL/PostgreSQL")
    void testFindByNombre() {
        Departamento dpto = new Departamento();
        dpto.setNombre("TI");
        departamentoRepository.save(dpto);

        Optional<Departamento> encontrado = departamentoRepository.findByNombre("TI");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("TI");
    }

    @Test
    @DisplayName("findDepartamentosSinEmpleados funciona en MySQL/PostgreSQL")
    void testFindDepartamentosSinEmpleados() {
        Departamento dpto = new Departamento();
        dpto.setNombre("SinEmpleados");
        departamentoRepository.save(dpto);

        List<Departamento> lista = departamentoRepository.findDepartamentosSinEmpleados();
        assertThat(lista).extracting(Departamento::getNombre).contains("SinEmpleados");
    }

    @Test
    @DisplayName("findDepartamentosConMasDeXEmpleados funciona en MySQL/PostgreSQL")
    void testFindDepartamentosConMasDeXEmpleados() {
        Departamento dpto = new Departamento();
        dpto.setNombre("Muchos");
        dpto.setDescripcion("Departamento con muchos empleados");
        departamentoRepository.save(dpto);

        Empleado emp1 = new Empleado();
        emp1.setNombre("E1");
        emp1.setApellido("Apellido1");
        emp1.setEmail("e1@example.com");
        emp1.setFechaContratacion(LocalDate.of(2020, 1, 23));
        emp1.setSalario(new BigDecimal("20000"));
        emp1.setDepartamento(dpto);

        Empleado emp2 = new Empleado();
        emp2.setNombre("E2");
        emp2.setApellido("Apellido2");
        emp2.setEmail("e2@example.com");
        emp2.setFechaContratacion(LocalDate.of(2020, 1, 23));
        emp2.setSalario(new BigDecimal("8"));
        emp2.setDepartamento(dpto);

        empleadoRepository.save(emp1);
        empleadoRepository.save(emp2);

        List<Departamento> lista = departamentoRepository.findDepartamentosConMasDeXEmpleados(1);
        assertThat(lista).extracting(Departamento::getNombre).contains("Muchos");
    }
}