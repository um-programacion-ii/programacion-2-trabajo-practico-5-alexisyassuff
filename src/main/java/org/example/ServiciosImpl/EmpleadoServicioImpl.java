package org.example.ServiciosImpl;
import org.example.Entidades.Empleado;
import org.example.*;
import java.util.List;
import org.example.Excepciones.EmailDuplicadoException;
import org.example.Excepciones.EmpleadoNoEncontradoException;
import org.example.Repositorios.DepartamentoRepositorio;
import org.example.Repositorios.EmpleadoRepositorio;
import org.example.Servicios.EmpleadoServicio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.*;
import java.math.BigDecimal;
import org.example.*;

@Service
@Transactional
public class EmpleadoServicioImpl implements EmpleadoServicio {
    private final EmpleadoRepositorio empleadoRepository;
    private final DepartamentoRepositorio departamentoRepository;

    public EmpleadoServicioImpl(EmpleadoRepositorio empleadoRepository,
                                DepartamentoRepositorio departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        if (empleadoRepository.findByEmail(empleado.getEmail()).isPresent()) {
            throw new EmailDuplicadoException("El email ya está registrado: " + empleado.getEmail());
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Empleado no encontrado con ID: " + id));
    }

    @Override
    public List<Empleado> buscarPorDepartamento(String nombreDepartamento) {
        return empleadoRepository.findByNombreDepartamento(nombreDepartamento);
    }

    @Override
    public List<Empleado> buscarPorRangoSalario(BigDecimal salarioMin, BigDecimal salarioMax) {
        return empleadoRepository.findBySalarioBetween(salarioMin, salarioMax);
    }

    @Override
    public BigDecimal obtenerSalarioPromedioPorDepartamento(Long departamentoId) {
        return empleadoRepository.findAverageSalarioByDepartamento(departamentoId)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado actualizar(Long id, Empleado empleado) {
        if (!empleadoRepository.existsById(id)) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado con ID: " + id);
        }
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado con ID: " + id);
        }
        empleadoRepository.deleteById(id);
    }
}