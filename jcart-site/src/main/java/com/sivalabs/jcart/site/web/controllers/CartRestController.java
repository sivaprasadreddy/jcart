package com.sivalabs.jcart.site.web.controllers;

import static java.util.Collections.singletonMap;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Product;
import com.sivalabs.jcart.site.web.models.Cart;
import com.sivalabs.jcart.site.web.models.LineItem;

import lombok.RequiredArgsConstructor;

/**
 * @author rajakolli
 *
 */
@RestController
@RequiredArgsConstructor
public class CartRestController extends AbstractJCartSiteController
{
    private final CatalogService catalogService;

    @Override
    protected String getHeaderTitle()
    {
        return "Rest Cart";
    }

    @GetMapping(value = "/cart/items/count")
    public Map<String, Object> getCartItemCount(HttpServletRequest request, Model model)
    {
        Cart cart = getOrCreateCart(request);
        int itemCount = cart.getItemCount();
        return singletonMap("count", itemCount);
    }

    @PostMapping(value = "/cart/items")
    public void addToCart(@RequestBody Product product, HttpServletRequest request)
    {
        Cart cart = getOrCreateCart(request);
        Product cartProduct = catalogService.getProductBySku(product.getSku());
        cart.addItem(cartProduct);
    }

    @PutMapping(value = "/cart/items")
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
    public void removeCartItem(@PathVariable("sku") String sku,
            HttpServletRequest request)
    {
        Cart cart = getOrCreateCart(request);
        cart.removeItem(sku);
    }

    @DeleteMapping(value = "/cart")
    public void clearCart(HttpServletRequest request)
    {
        Cart cart = getOrCreateCart(request);
        cart.setItems(new ArrayList<LineItem>());
    }

}
