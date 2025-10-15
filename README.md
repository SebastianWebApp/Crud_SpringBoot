# CRUD de Productos con Spring Boot (sin base de datos)

Este proyecto es un ejemplo de cómo crear un **CRUD completo de productos** utilizando Spring Boot, **sin necesidad de una base de datos**. Todo se maneja en memoria para enfocarse en la lógica de negocio, APIs REST y pruebas unitarias.

---

## Características

- **CRUD completo:** Create, Read, Update, Delete de productos.
- **Pruebas unitarias:** Usando `MockMvc` para validar los endpoints.
- **Documentación:** Endpoints documentados con Swagger/OpenAPI.
- **En memoria:** No se requiere base de datos, ideal para aprender la lógica y estructura de un proyecto Spring Boot.

---

## Tecnologías y herramientas

- **Java 21**
- **Spring Boot 3**
- **MockMvc** para pruebas unitarias
- **Swagger/OpenAPI** para documentación de APIs
- **Maven** para la gestión de dependencias

---

## Endpoints principales

| Método | Ruta                        | Descripción                     |
|--------|-----------------------------|---------------------------------|
| GET    | `/productos`                | Obtener todos los productos     |
| GET    | `/productos_id`             | Obtener productos con ID        |
| POST   | `/nuevo_producto`           | Agregar un nuevo producto       |
| PUT    | `/actualizar_producto/{id}` | Actualizar un producto existente|
| DELETE | `/eliminar_producto/{id}`   | Eliminar un producto            |
| GET    | `/productos_filtro/{nombre}/{producto}` | Filtrar productos |

> Swagger UI disponible en: `http://localhost:80/swagger-ui.html`

---

## Pruebas unitarias

Se utilizan pruebas con `MockMvc` para verificar:

- Creación de productos
- Obtención de productos
- Actualización de productos
- Eliminación de productos
- Filtrado de productos

Ejemplo de ejecución de prueba:

```java
mockMvc.perform(post("/nuevo_producto")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(content().string(containsString("Producto agregado correctamente")));
