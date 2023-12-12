package com.ssf.day15lecture.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ssf.day15lecture.model.Item;

@Service
public class CartService {

    @Autowired
    @Qualifier("redis")
    private RedisTemplate<String, String> template;

    public List<Item> getCart(String name) {
        List<Item> cart = new ArrayList<>();
        if (template.hasKey(name)) {
            Map<Object, Object> entries = template.opsForHash().entries(name);
            for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                String itemName = (String) entry.getKey();
                Integer quantity = Integer.parseInt((String) entry.getValue());
                cart.add(new Item(itemName, quantity));
                System.out.println("Item: " + itemName + " Quantity: " + quantity);
            }
        }

        else {
            System.out.println("Creating new cart in Redis");
        }
        return cart;
    }

    public void addToCart(String name, Item item) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        hashOps.put(name, item.getItemname(), item.getQuantity().toString());
    }

    public void checkoutCart(String name) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        Set<String> fieldKeys = hashOps.keys(name);
        hashOps.delete(name, fieldKeys.toArray(new String[0]));
    }
}
