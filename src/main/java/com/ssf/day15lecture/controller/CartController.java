package com.ssf.day15lecture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ssf.day15lecture.model.Item;
import com.ssf.day15lecture.service.CartService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ModelAndView getUserCart(@RequestParam String name, HttpSession session) {
        ModelAndView mav = new ModelAndView("cart");
        List<Item> cart = cartService.getCart(name);
        mav.addObject("item", new Item());
        mav.addObject("cart", cart);
        session.setAttribute("name", name);
        return mav;
    }

    @PostMapping
    // the @ModelAttribute is used to bind form data to the item object
    public ModelAndView postCart(@Valid @ModelAttribute Item item, BindingResult binding, HttpSession session) {
        // creates a ModelAndView object with the view name "cart"
        // mav allows both the view and data to be displayed in the view
        ModelAndView mav = new ModelAndView("cart");
        if (binding.hasErrors()) {
            List<Item> cart = cartService.getCart((String) session.getAttribute("name"));
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("cart", cart);
            return mav;
        }
        String name = (String) session.getAttribute("name");
        cartService.addToCart(name, item);
        List<Item> cart = cartService.getCart(name);
        // adds a new Item instance - used for form submissions
        mav.addObject("item", new Item());
        mav.addObject("cart", cart);
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @PostMapping("/checkout")
    public String postCartCheckout(HttpSession session) {
        String name = (String) session.getAttribute("name");
        cartService.checkoutCart(name);
        session.invalidate();
        return "redirect:/";
    }
}