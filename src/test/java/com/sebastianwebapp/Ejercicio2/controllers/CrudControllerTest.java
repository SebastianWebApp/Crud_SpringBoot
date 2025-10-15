package com.sebastianwebapp.Ejercicio2.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @BeforeEach
    public void testAgregarProducto() throws Exception {
        String json = """
                {
                  "ID": 1,
                  "nombre": "Producto1",
                  "precio": 10.5,
                  "categoria": "Electronica"
                }
                """;

        mockMvc.perform(post("/nuevo_producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Producto agregado correctamente")));
    }


    @Test
    public void testObtenerProductos() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    public void testObtenerProductos_ID() throws Exception {
        mockMvc.perform(get("/productos_id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    public  void testFiltroProducto() throws Exception{
        mockMvc.perform(get("/productos_filtro")
                        .param("nombre", "Producto1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Producto1"))
                .andExpect(jsonPath("$[0].categoria").value("Electronica"));
    }


    @Test
    public void testActualizarProducto() throws Exception {
        String json = """
                {
                  "nombre": "Producto2",
                  "precio": 20.5,
                  "categoria": "Electronica"
                }
                """;

        mockMvc.perform(put("/actualizar_producto/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("El producto ha sido actualizado")));
    }


    @Test
    public void testEliminarProductos() throws Exception {
        mockMvc.perform(delete("/eliminar_producto/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("El producto se eliminó correctamente")));
    }
}
