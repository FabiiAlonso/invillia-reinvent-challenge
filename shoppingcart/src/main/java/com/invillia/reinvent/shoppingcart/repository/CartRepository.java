package com.invillia.reinvent.shoppingcart.repository;


import com.invillia.reinvent.shoppingcart.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUserId(String userId);
}
