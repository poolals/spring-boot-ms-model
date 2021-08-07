package br.com.poolals.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                    throw new ProductAlreadyExistException("Product already exist with product number: " + request.getProductNumber());
                });

        Product product = mapper.map(request, Product.class);
        Product save = productRepositoy.save(product);

        return mapper.map(save, ProductResponse.class);
    }

    public PageableResponse<ProductResponse> list(PageableRequest request) {
        Pageable pagingPageable = PageRequest.of(request.getPage(), request.getSize());

        Page<Product> products = productRepositoy.findAll(pagingPageable);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Product not founded");
        }

        return new PageableResponse<>(products);
    }

}
