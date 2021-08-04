package br.com.poolals.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepositoy extends JpaRepository<Product, Long> {
    Optional<Product> findByProductNumber(Long productNumber);
}
