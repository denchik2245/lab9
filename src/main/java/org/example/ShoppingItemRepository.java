package org.example;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ShoppingItemRepository {

    private final ConcurrentHashMap<Long, ShoppingItem> items = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public List<ShoppingItem> findAll() {
        return new ArrayList<>(items.values());
    }

    public Optional<ShoppingItem> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    public ShoppingItem save(ShoppingItem item) {
        if (item.getId() == null) {
            item.setId(counter.incrementAndGet());
        }
        items.put(item.getId(), item);
        return item;
    }

    public void deleteById(Long id) {
        items.remove(id);
    }
}