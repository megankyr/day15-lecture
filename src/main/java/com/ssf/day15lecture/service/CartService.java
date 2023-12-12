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

    // retrieves cart from redis and converts it into a List<Item> object
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

    // adding items to redis hash
    public void addToCart(String name, Item item) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        hashOps.put(name, item.getItemname(), item.getQuantity().toString());
    }

    // deleting user from redis hash
    public void checkoutCart(String name) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        // fieldKeys is a set of strings containing all the keys(fields) associated with the name
        Set<String> fieldKeys = hashOps.keys(name);
        // the delete method expects an array of strings, so we convert the set to an array
        hashOps.delete(name, fieldKeys.toArray(new String[0]));
    }
}
