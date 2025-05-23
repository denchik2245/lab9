package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ShoppingItemRepository repository;

    @BeforeEach
    void clean() {
        repository.findAll().forEach(i -> repository.deleteById(i.getId()));
    }

    @Test
    void addAndGetAll() throws Exception {
        mvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Milk\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Milk"));

        mvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Milk"));
    }

    @Test
    void markBoughtAndDelete() throws Exception {
        // Создаём элемент напрямую через репозиторий
        ShoppingItem item = repository.save(new ShoppingItem(null, "Bread"));

        mvc.perform(put("/api/items/" + item.getId() + "/bought"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bought").value(true));

        mvc.perform(delete("/api/items/" + item.getId()))
                .andExpect(status().isNoContent());

        mvc.perform(delete("/api/items/9999"))
                .andExpect(status().isNotFound());
    }
}