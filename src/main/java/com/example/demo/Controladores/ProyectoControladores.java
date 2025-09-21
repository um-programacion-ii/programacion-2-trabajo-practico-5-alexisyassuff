package com.example.demo.Controladores;
import com.example.demo.Entidades.Proyecto;
import com.example.demo.Servicios.ProyectoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@Validated
public class ProyectoControladores {
    private final ProyectoServicio proyectoService;

    public ProyectoControladores(ProyectoServicio proyectoService){
        this.proyectoService = proyectoService;
    }


    @GetMapping
    public List<Proyecto> obtenerTodos(){
        return proyectoService.obtenerTodos();
    }


    @GetMapping("/{id}")
    public Proyecto obtenerPorId(@PathVariable Long id){
        return proyectoService.buscarPorId(id);
    }


    @GetMapping("/nombre/{nombre}")
    public Proyecto obtenerPorNombre(@PathVariable String nombre){
        return proyectoService.buscarPorNombre(nombre);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proyecto crear(@Valid @RequestBody Proyecto proyecto){
        return proyectoService.guardar(proyecto);
    }


    @PutMapping("/{id}")
    public Proyecto actualizar(@PathVariable Long id,@Valid @RequestBody Proyecto proyecto){
        return proyectoService.actualizar(id, proyecto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        proyectoService.eliminar(id);
    }


    @GetMapping("/fecha/despues")
    public List<Proyecto> obtenerFechaInicioDespues(@RequestParam LocalDate fechaInicio){
        return proyectoService.buscarPorFechaInicioDespues(fechaInicio);
    }


    @GetMapping("/fecha/antes")
    public List<Proyecto> obtenerFechaFinAntes(@RequestParam LocalDate fechaFin){
        return proyectoService.buscarPorFechaFinAntes(fechaFin);
    }

    @GetMapping("/empleado/{id}")
    public List<Proyecto> obtenerPorEmpleadoId(@PathVariable Long id){
        return proyectoService.buscarPorEmpleadoId(id);
    }
}