/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sivalabs.jcart.site.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Product;
import com.sivalabs.jcart.site.web.models.Cart;
import com.sivalabs.jcart.site.web.models.LineItem;

import lombok.RequiredArgsConstructor;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@Controller
@RequiredArgsConstructor
public class CartController extends AbstractJCartSiteController
{
    private CatalogService catalogService;

    @Override
    protected String getHeaderTitle()
    {
        return "Cart";
    }

    @GetMapping(value = "/cart")
    public String showCart(HttpServletRequest request, Model model)
    {
        Cart cart = getOrCreateCart(request);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping(value = "/cart/items/count")
    @ResponseBody
    public Map<String, Object> getCartItemCount(HttpServletRequest request, Model model)
    {
        Cart cart = getOrCreateCart(request);
        int itemCount = cart.getItemCount();
        Map<String, Object> map = new HashMap<>();
        map.put("count", itemCount);
        return map;
    }

    @PostMapping(value = "/cart/items")
    @ResponseBody
    public void addToCart(@RequestBody Product product, HttpServletRequest request)
    {
        Cart cart = getOrCreateCart(request);
        Product cartProduct = catalogService.getProductBySku(product.getSku());
        cart.addItem(cartProduct);
    }

    @PutMapping(value = "/cart/items")
    @ResponseBody
    public void updateCartItem(@RequestBody LineItem item, HttpServletRequest request,
            HttpServletResponse response)
    {
        Cart cart = getOrCreateCart(request);
        if (item.getQuantity() <= 0)
        {
            String sku = item.getProduct().getSku();
            cart.removeItem(sku);
        }
        else
        {
            cart.updateItemQuantity(item.getProduct(), item.getQuantity());
        }
    }

    @DeleteMapping(value = "/cart/items/{sku}")
    @ResponseBody
    public void removeCartItem(@PathVariable("sku") String sku,
            HttpServletRequest request)
    {
        Cart cart = getOrCreateCart(request);
        cart.removeItem(sku);
    }

    @DeleteMapping(value = "/cart")
    @ResponseBody
    public void clearCart(HttpServletRequest request)
    {
        Cart cart = getOrCreateCart(request);
        cart.setItems(new ArrayList<LineItem>());
    }
}
