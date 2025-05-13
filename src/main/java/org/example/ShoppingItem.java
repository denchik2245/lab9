package org.example;

public class ShoppingItem {
    private Long id;
    private String name;
    private boolean bought;

    public ShoppingItem() {}

    public ShoppingItem(Long id, String name) {
        this.id = id;
        this.name = name;
        this.bought = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isBought() { return bought; }
    public void setBought(boolean bought) { this.bought = bought; }
}