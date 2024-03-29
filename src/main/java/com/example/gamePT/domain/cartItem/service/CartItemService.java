package com.example.gamePT.domain.cartItem.service;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.cartItem.repository.CartItemRepository;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItem findById(Long id) {
        Optional<CartItem> _cartItem = this.cartItemRepository.findById(id);
        if (_cartItem.isEmpty()) {
            return null;
        }
        return _cartItem.get();
    }

    public CartItem findCartItem(SiteUser buyer, Course course) {
        Optional<CartItem> _cartItem = this.cartItemRepository.findByBuyerAndCourse(buyer, course);
        if (_cartItem.isEmpty()) {
            return null;
        }
        return _cartItem.get();
    }

    public void create (SiteUser buyer, Course course){
        CartItem cartItem = CartItem.builder()
                .buyer(buyer)
                .course(course)
                .build();
        this.cartItemRepository.save(cartItem);
    }

    public List<CartItem> findByBuyerId(Long id) {
        return this.cartItemRepository.findByBuyerId(id);
    }


    public Boolean deleteCartItemBy(Long id, SiteUser buyer) {

        CartItem ci = findById(id);

        if(ci == null){
            return false;
        }

        if(!ci.getBuyer().getUsername().equals(buyer.getUsername())){
            return false;
        }

        cartItemRepository.delete(ci);

        return true;
    }
}
