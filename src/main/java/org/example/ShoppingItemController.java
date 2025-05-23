package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ShoppingItemController {
    private final ShoppingItemService service;

    public ShoppingItemController(ShoppingItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShoppingItem> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ShoppingItem add(@RequestBody ShoppingItem item) {
        return service.add(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/bought")
    public ResponseEntity<ShoppingItem> markBought(@PathVariable Long id) {
        return service.markBought(id)
                .map(item -> ResponseEntity.ok(item))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}