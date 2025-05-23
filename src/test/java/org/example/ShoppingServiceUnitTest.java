package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceUnitTest {

    @Mock
    private ShoppingItemRepository repo;

    @InjectMocks
    private ShoppingItemService service;

    @Test
    void addCallsSaveAndReturnsSaved() {
        ShoppingItem input = new ShoppingItem(null, "Eggs");
        ShoppingItem saved = new ShoppingItem(1L, "Eggs");
        when(repo.save(input)).thenReturn(saved);

        ShoppingItem result = service.add(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repo).save(input);
    }

    @Test
    void deleteReturnsFalseIfNotFound() {
        when(repo.findById(42L)).thenReturn(Optional.empty());

        boolean result = service.delete(42L);

        assertThat(result).isFalse();
        verify(repo, never()).deleteById(any());
    }

    @Test
    void deleteReturnsTrueIfFound() {
        ShoppingItem item = new ShoppingItem(2L, "Cheese");
        when(repo.findById(2L)).thenReturn(Optional.of(item));

        boolean result = service.delete(2L);

        assertThat(result).isTrue();
        verify(repo).deleteById(2L);
    }

    @Test
    void markBoughtSetsFlagAndSaves() {
        ShoppingItem item = new ShoppingItem(3L, "Butter");
        when(repo.findById(3L)).thenReturn(Optional.of(item));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Optional<ShoppingItem> maybe = service.markBought(3L);

        assertThat(maybe).isPresent();
        assertThat(maybe.get().isBought()).isTrue();
        verify(repo).save(item);
    }
}