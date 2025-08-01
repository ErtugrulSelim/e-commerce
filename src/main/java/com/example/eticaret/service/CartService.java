package com.example.eticaret.service;

import com.example.eticaret.dto.CartItemDto;
import com.example.eticaret.dto.ProductDto;
import com.example.eticaret.exceptions.NotFoundException;
import com.example.eticaret.exceptions.StockException;
import com.example.eticaret.model.Cart;
import com.example.eticaret.model.CartItem;
import com.example.eticaret.model.Product;
import com.example.eticaret.model.User;
import com.example.eticaret.repository.CartItemRepository;
import com.example.eticaret.repository.CartRepository;
import com.example.eticaret.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class CartService {

    private final ProductService productService;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;

    private Cart getCurrentUserCart(User user) {
        return cartRepository.findByUser(user);
    }
    @Transactional
    public List<CartItemDto> getMappingCartItemDto(User user) {
        Cart cart = getCurrentUserCart(user);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.stream().map(cardItem -> {
            CartItemDto dto = new CartItemDto();
            dto.setCart(cardItem.getCart());
            dto.setQuantity(cardItem.getQuantity());
            ProductDto productDto = productService.convertToDto(cardItem.getProduct());
            dto.setProduct(productDto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void addToCart(User user, Long productId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Optional<CartItem> existingCartItem = findCartItemByProductId(cart, productId);

        if (existingCartItem.isPresent()) {
            CartItem exactCartItem = existingCartItem.get();
            exactCartItem.setCart(cart);
            updateCardItem(exactCartItem, quantity, product, cart);
        } else {
            checkStock(quantity, 0, product.getStock());
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            updateCardItem(newItem, quantity, product, cart);
            cart.getCartItems().add(newItem);
        }
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setCartItems(new ArrayList<>());
            return cartRepository.save(newCart);
        }
        return cart;
    }

    private Optional<CartItem> findCartItemByProductId(Cart cart, Long productId) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    private void checkStock(int quantityToAdd, int currentQuantity, int stock) {
        if (stock < (quantityToAdd + currentQuantity)) {
            throw new StockException("Stock exceeded.");
        }
    }

    private void updateCardItem(CartItem cartItem, int quantity, Product product, Cart cart) {
        checkStock(quantity, cartItem.getQuantity(), product.getStock());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void deleteProductFromCart(User user, Long productId, int quantity) {
        Cart cart = getCurrentUserCart(user);
        Optional<CartItem> existingCartItem = findCartItemByProductId(cart, productId);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            if (cartItem.getQuantity() <= quantity) {
                cart.getCartItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() - quantity);
                cartItemRepository.save(cartItem);
            }
            cartRepository.save(cart);
        } else {
            throw new NotFoundException("Product not found in cart.");
        }
    }

    @Transactional
    public void cleanCart(User user) {
        Cart cart = getCurrentUserCart(user);
        List<CartItem> cartItem = cartItemRepository.findByCart(cart);
        cart.getCartItems().removeAll(cartItemRepository.findByCart(cart));
        cartItemRepository.deleteAll(cartItem);
    }

}

