package com.sebastianwebapp.Ejercicio2.controllers;

import com.sebastianwebapp.Ejercicio2.domain.Product;
import com.sebastianwebapp.Ejercicio2.domain.ProductDTO;
import com.sebastianwebapp.Ejercicio2.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService service;
    private ProductController controller;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(ProductService.class);
        controller = new ProductController(service);
    }

    @Test
    void testGetAllProducts_returnsOk() {
        List<ProductDTO> mockList = List.of(new ProductDTO("TV", 200.0, "Electronics"));
        when(service.getAllWithoutId()).thenReturn(mockList);

        ResponseEntity<List<ProductDTO>> response = controller.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("TV", response.getBody().get(0).name());
    }

    @Test
    void testGetAllProducts_returnsNoContent() {
        when(service.getAllWithoutId()).thenReturn(List.of());

        ResponseEntity<List<ProductDTO>> response = controller.getAllProducts();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testAddProduct_returnsCreated() {
        Product p = new Product(0,"TV", 200.0, "Electronics");
        ResponseEntity<String> response = controller.addProduct(p);

        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Product added"));
        verify(service).addProduct(p);
    }

    @Test
    void testUpdateProduct_successful() {
        when(service.updateProduct(eq(1), any())).thenReturn(true);

        ResponseEntity<String> response = controller.updateProduct(1, new ProductDTO("Phone", 100.0, "Tech"));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product updated successfully", response.getBody());
    }

    @Test
    void testUpdateProduct_notFound() {
        when(service.updateProduct(eq(99), any())).thenReturn(false);

        ResponseEntity<String> response = controller.updateProduct(99, new ProductDTO("Phone", 100.0, "Tech"));

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Product not found.", response.getBody());
    }

    @Test
    void testDeleteProduct_successful() {
        when(service.deleteProduct(1)).thenReturn(true);

        ResponseEntity<String> response = controller.deleteProduct(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product delete successfully", response.getBody());
    }

    @Test
    void testDeleteProduct_notFound() {
        when(service.deleteProduct(10)).thenReturn(false);

        ResponseEntity<String> response = controller.deleteProduct(10);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Product not found.", response.getBody());
    }
}
