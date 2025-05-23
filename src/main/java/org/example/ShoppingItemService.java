package org.example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingItemService {
    private final ShoppingItemRepository repository;

    public ShoppingItemService(ShoppingItemRepository repository) {
        this.repository = repository;
    }

    public List<ShoppingItem> getAll() {
        return repository.findAll();
    }

    public ShoppingItem add(ShoppingItem item) {
        return repository.save(item);
    }

    /**
     * Удаляет элемент по id.
     * @return true, если удалено; false, если не найдено
     */
    public boolean delete(Long id) {
        Optional<ShoppingItem> existing = repository.findById(id);
        if (existing.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Помечает элемент как купленный.
     * @return Optional с обновлённым элементом или пустой, если не найден
     */
    public Optional<ShoppingItem> markBought(Long id) {
        return repository.findById(id)
                .map(item -> {
                    item.setBought(true);
                    return repository.save(item);
                });
    }
}