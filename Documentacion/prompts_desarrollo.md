# Documentación de prompts y decisiones de diseño

## 1. Sobre la creación de entidades JPA

**No se utilizaron prompts para este apartado.**  
Las clases de entidades fueron diseñadas manualmente para cumplir con los requisitos de persistencia y relaciones del modelo de dominio.

---

## 2. Implementación de Repositorios y Servicios

### Prompt inicial para el repositorio de Proyecto

> **Prompt utilizado:**  
> Necesito un `@Repository` para la clase `Proyecto`:  
> *(Se incluyó el código de la entidad Proyecto con sus campos y relaciones.)*

**Respuesta obtenida:**
```java
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Optional<Proyecto> findByNombre(String nombre);
    List<Proyecto> findByFechaInicioAfter(LocalDate fechaInicio);
    List<Proyecto> findByFechaFinaBefore(LocalDate fechaFina);
    List<Proyecto> findByEmpleados_Id(Long empleadoId);
}
```
**Motivo para este prompt:**  
La consigna pedía un repositorio para gestionar la entidad `Proyecto` y consultas comunes.

**Cambios hechos sobre la respuesta:**
- Se añadió el método `findById`, necesario para búsquedas por identificador.

**Aprendizajes/Observaciones:**
- La consulta por nombre y fechas es directa con Spring Data.
- Usar nombres explícitos y claros en los métodos mejora la legibilidad y la autogeneración de queries.

---

## 3. Configuración de controladores y manejo de excepciones

### Prompt para manejo de excepciones HTTP

> **Prompt utilizado:**  
> Estoy en el Issue 22 del proyecto, donde debo configurar el manejo de excepciones HTTP para los controladores de Empleado, Departamento y Proyecto.  
> *(Se compartieron los archivos relevantes para dar contexto)*

**Respuesta obtenida:**  
La respuesta incluía la creación de un manejador global de excepciones, utilizando `@ControllerAdvice` y métodos `@ExceptionHandler` para distintas excepciones específicas, devolviendo respuestas JSON con campos como `timestamp`, `status` y `error`.  
Además, se propuso un método auxiliar para construir estas respuestas de forma uniforme.

**Ubicación sugerida:**  
El archivo se recomienda guardar en `src/main/java/excepciones/GlobalExceptionHandler.java`.

**Aprendizajes:**
- Se comprendió el uso de `@ControllerAdvice` para centralizar la gestión de errores.
- Se aprendió a personalizar los mensajes y códigos HTTP para cada tipo de excepción.
- Es una buena práctica devolver mensajes claros y estructurados al cliente ante cualquier fallo.

---

## 4. Decisiones de diseño tomadas

- **Estructura de paquetes:**  
  Se reorganizaron los paquetes para cumplir con las convenciones de escaneo de Spring Boot y evitar errores de beans no encontrados.
- **Repositorios:**  
  Se priorizó el uso de métodos derivados de nombres para consultas simples, manteniendo el código limpio y fácil de mantener.
- **Excepciones:**  
  Se optó por una gestión centralizada de errores, facilitando el mantenimiento y el control de las respuestas HTTP en toda la API.
- **Validaciones:**  
  La validación de datos y el manejo de errores de validación se realiza de forma homogénea, devolviendo detalles de los campos en caso de error.

---

## 5. Reflexión y aprendizaje

- Mejoré mi comprensión sobre cómo integrar prompts de IA para acelerar tareas repetitivas, pero siempre revisando y adaptando el código generado.


---