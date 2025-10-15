package com.sebastianwebapp.Ejercicio2.controllers;

import com.sebastianwebapp.Ejercicio2.domain.Producto;
import com.sebastianwebapp.Ejercicio2.domain.ProductoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Productos", description = "Operaciones CRUD para la gestión de productos")

public class CrudController {

    private List<Producto> productos = new ArrayList<>();


    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve la lista completa de productos registrados con el id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida con éxito",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Producto.class)))
            }
    )

    @GetMapping("/productos_id")
    public ResponseEntity<List<Producto>> productos_id() {
        if (productos.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT) // 204 No Content
                    .body(productos);
        }
        return ResponseEntity.ok(productos);
    }



    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve la lista completa de productos registrados sin el id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida con éxito",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoDTO.class)))
            }
    )

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> productos() {
        List<ProductoDTO> listaProductos = new ArrayList<>();
        for (Producto c : productos) {
            listaProductos.add(new ProductoDTO(c.getNombre(), c.getPrecio(), c.getCategoria()));
        }

        if (listaProductos.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT) // 204 si no hay productos
                    .body(listaProductos);
        }

        return ResponseEntity
                .status(HttpStatus.OK)           // 200 OK
                .body(listaProductos);
    }





    @Operation(
            summary = "Filtrar los productos",
            description = "Devuelve la lista filtrada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida con éxito",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoDTO.class)))
            }
    )

    @GetMapping("/productos_filtro")
    public ResponseEntity<List<ProductoDTO>> productos_filtro(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria) {

        List<ProductoDTO> listaProductos = new ArrayList<>();

        for (Producto c : productos) {
            boolean match = true;

            if (nombre != null && !nombre.isBlank()) {
                match = match && c.getNombre().equalsIgnoreCase(nombre);
            }
            if (categoria != null && !categoria.isBlank()) {
                match = match && c.getCategoria().equalsIgnoreCase(categoria);
            }

            if (match) {
                listaProductos.add(new ProductoDTO(c.getNombre(), c.getPrecio(), c.getCategoria()));
            }
        }

        if (listaProductos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listaProductos);
        }

        return ResponseEntity.ok(listaProductos);
    }




    @Operation(
            summary = "Agregar un nuevo producto",
            description = "Permite agregar un nuevo producto a la lista mediante un JSON con los datos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto agregado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos en el cuerpo de la petición")
            }
    )

    @PostMapping("/nuevo_producto")
    public ResponseEntity<String> nuevo_producto(@Valid @RequestBody Producto newCustomer){

        productos.add(newCustomer);

        // Retornas un ResponseEntity con mensaje y código 201 CREATED

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Producto agregado correctamente: " + newCustomer.getNombre());
    }


    @Operation(
            summary = "Actualizar un producto",
            description = "Permite actualizar un producto a la lista mediante un JSON con los datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Datos inválidos en el cuerpo de la petición")
            }
    )

    @PutMapping("/actualizar_producto/{id}")
    public ResponseEntity<String> actualizar_producto(
            @Parameter(description = "ID del producto a actualizar", example = "0", required = true)
            @PathVariable int id, @Valid @RequestBody ProductoDTO updateCustomer){

        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getID() == id){
                Producto c = productos.get(i);
                c.setNombre(updateCustomer.getNombre());
                c.setPrecio(updateCustomer.getPrecio());
                c.setCategoria(updateCustomer.getCategoria());
                productos.set(i, c);
                // Retorna mensaje con código 200 OK
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("El producto ha sido actualizado");
            }
        }


        // Retorna mensaje con código 404 NOT_FOUND
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("No se ha encontrado el producto");

    }



    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto de la lista por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )

    @DeleteMapping("/eliminar_producto/{id}")
    public ResponseEntity<String> eliminar_producto(
            @Parameter(description = "ID del producto a eliminar", example = "0", required = true)
            @PathVariable int id) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getID() == id) {
                productos.remove(i);
                return ResponseEntity
                        .status(HttpStatus.OK) // 200 OK
                        .body("El producto se eliminó correctamente");
            }
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404 Not Found
                .body("El producto no existe");
    }
}
