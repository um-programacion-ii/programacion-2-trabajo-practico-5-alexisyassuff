package org.example.Controladores;
import jakarta.validation.Valid;
import org.example.Entidades.Departamento;
import org.example.Servicios.DepartamentoServicios;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
@Validated
public class DepartamentoControladores {
    private final DepartamentoServicios departamentoService;

    public DepartamentoControladores(DepartamentoServicios departamentoService){
        this.departamentoService = departamentoService;
    }


    @GetMapping
    public List<Departamento> obtenerTodos() {
        return departamentoService.obtenerTodos();
    }


    @GetMapping("/{id}")
    public Departamento obtenerPorId(@PathVariable Long id) {
        return departamentoService.buscarPorId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Departamento crear(@Valid @RequestBody Departamento departamento) {
        return departamentoService.guardar(departamento);
    }


    @PutMapping("/{id}")
    public Departamento actualizar(@PathVariable Long id,@Valid @RequestBody Departamento departamento) {
        return departamentoService.actualizar(id, departamento);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        departamentoService.eliminar(id);
    }


    @GetMapping("/empleado/{id}")
    public List<Departamento> obtenerPorEmpleadoId(@PathVariable Long id) {
        return departamentoService.buscarPorEmpleadoId(id);
    }
}
