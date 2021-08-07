# spring-boot-ms-model

---

```
POST - http://localhost:9080/spring-boot-ms-model/v1/products
```

- add new product
![](resources/post_new_product_ok.png)

- add existing product
![](resources/post_existing_product.png)

- add product with invalid request
![](resources/post_invalid_request.png)

---
---

```
GET - http://localhost:9080/spring-boot-ms-model/v1/products?page=0&size=10
```
- get list of product with valid request
![](resources/list_product_pageable.png)

- get list of product with invalid request page
![](resources/list_product_invalid_request_pageable.png)
 
- get list of product with invalid request size
![](resources/list_product_invalid_request_pageable_2.png)