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

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sivalabs.jcart.site.web.models.Cart;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@Controller

public class CartController extends AbstractJCartSiteController
{
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

}
