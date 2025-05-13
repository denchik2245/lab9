package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ShoppingItemController {
    private final ShoppingItemRepository repository;

    public ShoppingItemController(ShoppingItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ShoppingItem> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ShoppingItem add(@RequestBody ShoppingItem item) {
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<ShoppingItem> existing = repository.findById(id);
        if (existing.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/bought")
    public ResponseEntity<ShoppingItem> markBought(@PathVariable Long id) {
        Optional<ShoppingItem> existing = repository.findById(id);
        if (existing.isPresent()) {
            ShoppingItem item = existing.get();
            item.setBought(true);
            repository.save(item);
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.notFound().build();
    }
}