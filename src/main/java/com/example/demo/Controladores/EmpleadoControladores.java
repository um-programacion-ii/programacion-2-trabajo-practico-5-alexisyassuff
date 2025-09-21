package com.example.demo.Controladores;
import jakarta.validation.Valid;
import com.example.demo.Entidades.Empleado;
import com.example.demo.Servicios.EmpleadoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Validated
public class EmpleadoControladores {
    private final EmpleadoServicio empleadoService;

    public EmpleadoControladores(EmpleadoServicio empleadoService) {
        this.empleadoService = empleadoService;
    }


    @GetMapping
    public List<Empleado> obtenerTodos() {
        return empleadoService.obtenerTodos();
    }


    @GetMapping("/{id}")
    public Empleado obtenerPorId(@PathVariable Long id) {
        return empleadoService.buscarPorId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado crear(@Valid @RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }


    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        return empleadoService.actualizar(id, empleado);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
    }


    @GetMapping("/departamento/{nombre}")
    public List<Empleado> obtenerPorDepartamento(@PathVariable String nombre) {
        return empleadoService.buscarPorDepartamento(nombre);
    }


    @GetMapping("/salario")
    public List<Empleado> obtenerPorRangoSalario(@RequestParam BigDecimal min,
                                                 @RequestParam BigDecimal max) {
        return empleadoService.buscarPorRangoSalario(min, max);
    }


    @GetMapping("/promedio/{departamentoId}")
    public BigDecimal salarioPromedioDepartamento(@PathVariable Long departamentoId){
        return empleadoService.obtenerSalarioPromedioPorDepartamento(departamentoId);
    }
}