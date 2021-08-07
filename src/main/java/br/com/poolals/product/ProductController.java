package br.com.poolals.product;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/v1/products")
@Api(value = "Product Management System")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productResponse.getId()).toUri();

        return ResponseEntity.created(location).body(productResponse);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductResponse>> listProducts(
            @Valid PageableRequest pageableRequest) {
        PageableResponse<ProductResponse> productsResponse = productService.list(pageableRequest);

        return ResponseEntity.ok().body(productsResponse);
    }

}
