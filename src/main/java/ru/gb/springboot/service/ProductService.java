package ru.gb.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.springboot.entity.Product;
import ru.gb.springboot.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product edit(Product product) {
        return productRepository.edit(product);
    }

    public Product findById(int id) {
       return productRepository.findById(id).orElse(new Product());
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public long count() {
        return productRepository.count();
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Product getRandomProduct() {
        return productRepository.getRandomProduct();
    }
}
