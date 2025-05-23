package org.example;

import java.util.List;
import java.util.Optional;

public interface ShoppingItemRepository {
    List<ShoppingItem> findAll();
    Optional<ShoppingItem> findById(Long id);
    ShoppingItem save(ShoppingItem item);
    void deleteById(Long id);
}
