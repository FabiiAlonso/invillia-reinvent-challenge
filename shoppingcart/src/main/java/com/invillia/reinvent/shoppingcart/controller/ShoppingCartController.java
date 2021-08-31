package com.invillia.reinvent.shoppingcart.controller;

import com.invillia.reinvent.shoppingcart.entity.Cart;
import com.invillia.reinvent.shoppingcart.entity.CartItem;
import com.invillia.reinvent.shoppingcart.repository.CartItemRepository;
import com.invillia.reinvent.shoppingcart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    //Adicionar um item no carrinho
    @PostMapping(value = "/{userId}/items/{sku}")
    public ResponseEntity<CartItem> addCartItem(@PathVariable String userId, @PathVariable String sku,
                                                @RequestBody CartItemRequest cartItemRequest){

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(userId);
            cartRepository.save(cart);
        }

         Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndSku(cart, sku);
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
        } else {
            cartItem = new CartItem(
                    sku, 
                    cartItemRequest.getPrice(),
                    cartItemRequest.getName(),
                    cartItemRequest.getQuantity(),
                    cart);
            cartItemRepository.save(cartItem);
        }

        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    //Editar quantidade
    @PatchMapping(value = "{userId}/items/{sku}")
    public ResponseEntity<CartItem> editCartItemQuantity(@PathVariable String userId, @PathVariable String sku,
                                                        @RequestParam Integer quantity){

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndSku(cart, sku);
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    //Editar itens
    @PutMapping(value = "{userId}/items/{sku}")
    ResponseEntity<CartItem> editCartItem(@PathVariable String userId, @PathVariable String sku,
                                          @RequestBody CartItemRequest request) {

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndSku(cart, sku);
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            cartItem.setPrice(request.getPrice());
            cartItem.setName(request.getName());
            cartItem.setQuantity(request.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    //Recuperar um item do carrinho
    @GetMapping(value = "/{userid}/items/{sku}")
    public ResponseEntity<CartItem> getItemBySku(@PathVariable String userId, @PathVariable String sku) {

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndSku(cart, sku);
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    //Recuperar o carrinho
    @GetMapping(value = "/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable String userId) {

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()){
            cart = optionalCart.get();
        } else{
            return ResponseEntity.notFound().build();
        }

        cart.getTotal();

        return ResponseEntity.ok().body(cart);
    }

    //Excluir o carrinho
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Cart> deleteCart(@PathVariable String userId) {

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        List<CartItem> items = cartItemRepository.findByCart(cart);

        cartItemRepository.deleteAll(items);
        cartRepository.delete(cart);

        //response
        cart.setItems(items);
        cart.getTotal();

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    //Excluir um item do carrinho
    @DeleteMapping(value = "/{userId}/items/{sku}")
    public ResponseEntity<CartItem> deleteCartItem(@PathVariable String  userId, @PathVariable String sku) {

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndSku(cart, sku);
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        cartItemRepository.delete(cartItem);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

}
