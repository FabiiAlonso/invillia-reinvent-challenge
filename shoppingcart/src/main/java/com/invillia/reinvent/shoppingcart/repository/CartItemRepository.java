package com.invillia.reinvent.shoppingcart.repository;

import com.invillia.reinvent.shoppingcart.entity.Cart;
import com.invillia.reinvent.shoppingcart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, String> {

    Optional<CartItem> findByCartAndSku(Cart cart, String sku);
    List<CartItem> findByCart(Cart cart);
}
