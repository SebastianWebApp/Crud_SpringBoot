package com.sebastianwebapp.Ejercicio2.service;

import com.sebastianwebapp.Ejercicio2.domain.Product;
import com.sebastianwebapp.Ejercicio2.domain.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    private final List<Product> products = new ArrayList<>();


    public List<Product> getAll() {
        return products;
    }


    public List<ProductDTO> getAllWithoutId(){
        return products.stream()
                .map(p -> new ProductDTO(p.name(), p.price(), p.category()))
                .toList();
    }



    public List<ProductDTO> filter(String name, String category){

        return products.stream()
                .filter(p -> (name == null || p.name().equalsIgnoreCase(name)) &&
                        (category == null || p.category().equalsIgnoreCase(category)))
                .map(p -> new ProductDTO(p.name(), p.price(), p.category()))
                .toList();
    }


    public  void addProduct(Product p){
        products.add(p);
    }


    public boolean updateProduct(int id, ProductDTO dto){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).id() == id){
                Product updated = new Product(id, dto.name(), dto.price(), dto.category());
                products.set(i,updated);
                return true;
            }
        }
        return false;
    }


    public boolean deleteProduct(int id){
        return products.removeIf(p -> p.id() == id);
    }



}
