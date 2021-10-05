package ru.geekbrains.backend.buisness.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.backend.buisness.service.ProductSoapService;
import ru.geekbrains.backend.buisness.soap.products.GetProductByTitleRequest;
import ru.geekbrains.backend.buisness.soap.products.GetProductByTitleResponse;
import ru.geekbrains.backend.buisness.soap.products.GetProductListResponse;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://gbshop.ru/soap/ws/products";
    private final ProductSoapService productService;

    @Autowired
    public ProductEndpoint(ProductSoapService productService) {
        this.productService = productService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductListRequest")
    @ResponsePayload
    @Transactional
    public GetProductByTitleResponse getProductByTitle(@RequestPayload GetProductByTitleRequest request) {
        GetProductByTitleResponse response = new GetProductByTitleResponse();
        response.setProduct(productService.getProductByTitle(request.getTitle()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductListRequest")
    @ResponsePayload
    @Transactional
    public GetProductListResponse getProductList() {
        GetProductListResponse response = new GetProductListResponse();
        response.getProducts().addAll(productService.findAll());
        return response;
    }
}
