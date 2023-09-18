package ru.sber.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.backend.entities.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByDish_IdAndClient_Id(long dishId, long clientId);

    void deleteCartByClient_Id(long clientId);

    List<Cart> findCartByClient_Id(long clientId);

    int countCartsByClient_Id(long clientId);

    int countCartsByDish_Id(long productId);
}
