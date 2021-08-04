package br.com.poolals.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepositoy productRepositoy;
    private final ModelMapper mapper;

    public ProductResponse create(ProductRequest request) {
        productRepositoy.findByProductNumber(request.getProductNumber())
                .ifPresent(product -> {
                    throw new AlreadyExistException("Product already exist with product number: " + request.getProductNumber());
                });

        Product product = mapper.map(request, Product.class);
        Product save = productRepositoy.save(product);

        return mapper.map(save, ProductResponse.class);
    }

}
