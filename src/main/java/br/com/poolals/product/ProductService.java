package br.com.poolals.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepositoy productRepositoy;
    private final ModelMapper mapper;

    public ProductResponse create(ProductRequest request) {
        productRepositoy.findByProductNumber(request.getProductNumber())
                .ifPresent(product -> {
                    throw new ProductAlreadyExistException("Product already exist with product number: " + request.getProductNumber());
                });

        Product product = mapper.map(request, Product.class);
        Product save = productRepositoy.save(product);

        return mapper.map(save, ProductResponse.class);
    }

    public List<ProductResponse> list() {
        List<Product> products = productRepositoy.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Product already exist with product number");
        }

        List<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product -> productResponses.add(mapper.map(product, ProductResponse.class)));

        return productResponses;
    }
}
