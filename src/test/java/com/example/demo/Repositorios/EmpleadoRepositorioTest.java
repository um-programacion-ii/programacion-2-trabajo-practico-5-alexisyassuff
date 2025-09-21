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
@ActiveProfiles("mysql")
class EmpleadoRepositorioTest {

    @Autowired
    private EmpleadoRepositorio empleadoRepository;

    @Autowired
    private DepartamentoRepositorio departamentoRepository;

    private Empleado crearEmpleado(String nombre, String apellido, String email, LocalDate fecha, BigDecimal salario, Departamento dpto) {
        Empleado emp = new Empleado();
        emp.setNombre(nombre);
        emp.setApellido(apellido);
        emp.setEmail(email);
        emp.setFechaContratacion(fecha);
        emp.setSalario(salario);
        emp.setDepartamento(dpto);
        return emp;
    }

    @Test
    @DisplayName("findByEmail funciona en MySQL/PostgreSQL")
    void testFindByEmail() {
        Departamento dpto = new Departamento();
        dpto.setNombre("TI");
        departamentoRepository.save(dpto);

        Empleado emp = crearEmpleado("Juan", "Perez", "test@mail.com", LocalDate.of(2023, 1, 1), new BigDecimal("1500"), dpto);
        empleadoRepository.save(emp);

        Optional<Empleado> encontrado = empleadoRepository.findByEmail("test@mail.com");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail()).isEqualTo("test@mail.com");
    }

    @Test
    @DisplayName("findByDepartamento funciona en MySQL/PostgreSQL")
    void testFindByDepartamento() {
        Departamento dpto = new Departamento();
        dpto.setNombre("TI");
        departamentoRepository.save(dpto);

        Empleado emp = crearEmpleado("Ana", "Lopez", "ana@mail.com", LocalDate.of(2023, 2, 1), new BigDecimal("1700"), dpto);
        empleadoRepository.save(emp);

        List<Empleado> lista = empleadoRepository.findByDepartamento(dpto);
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("findBySalarioBetween funciona en MySQL/PostgreSQL")
    void testFindBySalarioBetween() {
        Departamento dpto = new Departamento();
        dpto.setNombre("TI");
        departamentoRepository.save(dpto);

        Empleado emp1 = crearEmpleado("Carlos", "Garcia", "carlos@mail.com", LocalDate.of(2022, 5, 1), new BigDecimal("1000"), dpto);
        Empleado emp2 = crearEmpleado("Maria", "Sanchez", "maria@mail.com", LocalDate.of(2022, 6, 1), new BigDecimal("2000"), dpto);
        empleadoRepository.save(emp1);
        empleadoRepository.save(emp2);

        List<Empleado> lista = empleadoRepository.findBySalarioBetween(new BigDecimal("900"), new BigDecimal("2100"));
        assertThat(lista).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("findByFechaContratacionAfter funciona en MySQL/PostgreSQL")
    void testFindByFechaContratacionAfter() {
        Departamento dpto = new Departamento();
        dpto.setNombre("TI");
        departamentoRepository.save(dpto);

        Empleado emp = crearEmpleado("Lucia", "Diaz", "lucia@mail.com", LocalDate.of(2025, 1, 2), new BigDecimal("1800"), dpto);
        empleadoRepository.save(emp);

        List<Empleado> lista = empleadoRepository.findByFechaContratacionAfter(LocalDate.of(2025, 1, 1));
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("findByNombreDepartamento funciona en MySQL/PostgreSQL")
    void testFindByNombreDepartamento() {
        Departamento dpto = new Departamento();
        dpto.setNombre("TI");
        departamentoRepository.save(dpto);

        Empleado emp = crearEmpleado("Jorge", "Fernandez", "jorge@mail.com", LocalDate.of(2023, 3, 1), new BigDecimal("1600"), dpto);
        empleadoRepository.save(emp);

        List<Empleado> lista = empleadoRepository.findByNombreDepartamento("TI");
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("findAverageSalarioByDepartamento funciona en MySQL/PostgreSQL")
    void testFindAverageSalarioByDepartamento() {
        Departamento dpto = new Departamento();
        dpto.setNombre("TI");
        departamentoRepository.save(dpto);

        Empleado emp1 = crearEmpleado("Pablo", "Ruiz", "pablo@mail.com", LocalDate.of(2022, 7, 1), new BigDecimal("1000"), dpto);
        Empleado emp2 = crearEmpleado("Sofia", "Martinez", "sofia@mail.com", LocalDate.of(2022, 8, 1), new BigDecimal("2000"), dpto);
        empleadoRepository.save(emp1);
        empleadoRepository.save(emp2);

        Optional<BigDecimal> promedio = empleadoRepository.findAverageSalarioByDepartamento(dpto.getId());
        assertThat(promedio).isPresent();
        assertThat(promedio.get()).isEqualByComparingTo(new BigDecimal("1500"));
    }
}
