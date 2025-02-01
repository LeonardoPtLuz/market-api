package com.example.marketproject.market.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.marketproject.market.DTO.ProductDTO;
import com.example.marketproject.market.Repository.ProductRepository;
import com.example.marketproject.market.entity.Product;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // Método para converter Entity para DTO
    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    // Método para converter DTO para Entity
    private Product toEntity(ProductDTO dto) {
        Product product = new  Product();
        BeanUtils.copyProperties(dto, product);
        return product;
    }

     // Busca todos os produtos
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca produto por id
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com esse ID não foi encontrado!"));
        return toDTO(product);
    }

    // Cria novo produto
    @Transactional
    public ProductDTO create(ProductDTO dto) {
        Product product = toEntity(dto);
        product = repository.save(product);
        return toDTO(product);
    }

     // Atualiza produto existente da lista pelo id
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não foi possivél atualizar!"));
        
        BeanUtils.copyProperties(dto, product, "id");
        product = repository.save(product);
        return toDTO(product);
    }

    // Remove produto pelo id
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Id não encontrado");
        }
        repository.deleteById(id);
    }
}
