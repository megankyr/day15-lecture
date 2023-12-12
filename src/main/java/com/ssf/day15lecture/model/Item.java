package com.ssf.day15lecture.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Item {
    
    @NotEmpty(message = "Please enter the item name")
    private String itemname;

    @Min(value = 1, message = "Minimum order quantity is 1 item")
    @Max(value = 10, message = "Maximum order quantity are 10 items")
    private Integer quantity;

    public Item() {
    }

    public Item(@NotEmpty(message = "Please enter the item name") String itemname,
            @Min(value = 1, message = "Minimum order quantity is 1 item") @Max(value = 10, message = "Maximum order quantity are 10 items") Integer quantity) {
        this.itemname = itemname;
        this.quantity = quantity;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item [name=" + itemname + ", quantity=" + quantity + "]";

    }




}
