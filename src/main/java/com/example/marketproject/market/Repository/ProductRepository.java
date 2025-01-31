package com.example.marketproject.market.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.marketproject.market.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
