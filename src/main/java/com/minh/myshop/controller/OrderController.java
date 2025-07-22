package com.minh.myshop.controller;

import com.minh.myshop.dto.OrderDto;
import com.minh.myshop.dto.OrderProductDto;
import com.minh.myshop.entity.Order;
import com.minh.myshop.enums.OrderStatus;
import com.minh.myshop.exception.NoSuchOrderException;
import com.minh.myshop.exception.ProductStockInvalid;
import com.minh.myshop.exception.UserIdNotFoundException;
import com.minh.myshop.service.impl.OrderProductServiceImpl;
import com.minh.myshop.service.impl.OrderServiceImpl;
import com.minh.myshop.service.impl.ProductServiceImpl;
import com.minh.myshop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderProductServiceImpl orderProductService;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/history")
    public ResponseEntity<?> orderHistory(@RequestParam(name = "userId") Integer userId) {
        var orderIdList = orderService.findAllOrderIdByUserIdAndStatus(userId, OrderStatus.COMPLETED);
        var list = new ArrayList<OrderDto>();
        for (var orderId : orderIdList) {
            var currOrderList = orderProductService.findAllByOrderOrderId(orderId).stream().map(OrderProductDto::new).collect(Collectors.toCollection(ArrayList::new));
            var orderDto = new OrderDto(orderId, currOrderList);
            list.add(orderDto);
        }
        return ResponseEntity.ok(Map.of("orderHistoryList", list));
    }


    @GetMapping("/")
    public ResponseEntity<?> getOrder(@RequestParam(name = "userId") Integer userId) throws NoSuchOrderException {
        var orderId = orderService.findOrderIdByUserIdAndStatus(userId, OrderStatus.PENDING);
        var currOrderList = orderProductService.findAllByOrderOrderId(orderId).stream().map(OrderProductDto::new).collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok(new OrderDto(orderId, currOrderList));
    }

    @PostMapping("/buyAll")
    public ResponseEntity<?> buyAll(@RequestBody OrderDto orderDto) throws ProductStockInvalid, NoSuchOrderException {
        var order = orderService.getOrderById(orderDto.getOrderId());
//        productService.changeListProductStock(orderDto.getProductList());
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderService.save(order);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestParam(name = "userId") Integer userId) throws UserIdNotFoundException {
        var newOrder = Order.builder().user(userService.getUserById(userId)).build();
        orderService.save(newOrder);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("/addProductToOrder/{id}")
    public ResponseEntity<?> addProductToOrder(@PathVariable Integer id, @RequestBody OrderDto orderDto) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeProductQuantity(@PathVariable Integer id, @RequestBody OrderDto orderDto) throws NoSuchOrderException, ProductStockInvalid {
        var e = orderDto.getProductList().getFirst();
        orderProductService.changeOrderProductQuantity(id, e.getProductId(), e.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductInOrder(@PathVariable Integer id, @RequestBody Integer productId) {
        orderProductService.deleteByOrderIdAndProductId(id, productId);
        return ResponseEntity.ok().build();
    }

}
