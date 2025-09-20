package org.example.ServiciosImpl;
import org.example.*;
import org.example.Entidades.Proyecto;
import org.example.Excepciones.ProyectoDuplicadoException;
import org.example.Excepciones.ProyectoNoEncontradoException;
import org.example.Repositorios.ProyectoRepositorio;
import org.example.Servicios.ProyectoServicio;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ProyectoServicioImpl implements ProyectoServicio {

    private final ProyectoRepositorio proyectoRepository;

    public ProyectoServicioImpl(ProyectoRepositorio proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public Proyecto guardar(Proyecto proyecto) {
        if (proyectoRepository.findByNombre(proyecto.getNombre()).isPresent()) {
            throw new ProyectoDuplicadoException("Ya existe un proyecto con el nombre: " + proyecto.getNombre());
        }
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto buscarPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new ProyectoNoEncontradoException("Proyecto no encontrado con ID: " + id));
    }

    @Override
    public Proyecto buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombre(nombre)
                .orElseThrow(() -> new ProyectoNoEncontradoException("Proyecto no encontrado con nombre: " + nombre));
    }

    @Override
    public List<Proyecto> buscarPorFechaInicioDespues(LocalDate fechaInicio) {
        List<Proyecto> proyectos = proyectoRepository.findByFechaInicioAfter(fechaInicio);
        if (proyectos.isEmpty()) {
            throw new ProyectoNoEncontradoException("No se encontraron proyectos que inicien después de: " + fechaInicio);
        }
        return proyectos;
    }

    @Override
    public List<Proyecto> buscarPorFechaFinAntes(LocalDate fechaFina) {
        List<Proyecto> proyectos = proyectoRepository.findByFechaFinBefore(fechaFina);
        if (proyectos.isEmpty()) {
            throw new ProyectoNoEncontradoException("No se encontraron proyectos que finalicen antes de: " + fechaFina);
        }
        return proyectos;
    }

    @Override
    public List<Proyecto> buscarPorEmpleadoId(Long empleadoId) {
        List<Proyecto> proyectos = proyectoRepository.findByEmpleados_Id(empleadoId);
        if (proyectos.isEmpty()) {
            throw new ProyectoNoEncontradoException("No se encontraron proyectos para el empleado ID: " + empleadoId);
        }
        return proyectos;
    }

    @Override
    public List<Proyecto> obtenerTodos() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        if (proyectos.isEmpty()) {
            throw new ProyectoNoEncontradoException("No hay proyectos registrados.");
        }
        return proyectos;
    }

    @Override
    public Proyecto actualizar(Long id, Proyecto proyecto) {
        Proyecto existente = proyectoRepository.findById(id)
                .orElseThrow(() -> new ProyectoNoEncontradoException("Proyecto no encontrado con ID: " + id));
        if (!existente.getNombre().equals(proyecto.getNombre())
                && proyectoRepository.findByNombre(proyecto.getNombre()).isPresent()) {
            throw new ProyectoDuplicadoException("Ya existe un proyecto con el nombre: " + proyecto.getNombre());
        }
        proyecto.setId(id);
        return proyectoRepository.save(proyecto);
    }

    @Override
    public void eliminar(Long id) {
        Proyecto existente = proyectoRepository.findById(id)
                .orElseThrow(() -> new ProyectoNoEncontradoException("Proyecto no encontrado con ID: " + id));
        proyectoRepository.deleteById(id);
    }
}