/*
 * Copyright 2016-2017 the original author or authors.
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
package com.sivalabs.jcart.site.web.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sivalabs.jcart.entities.Address;
import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Payment;
import com.sivalabs.jcart.entities.Product;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@Getter
@Setter
public class Cart implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List<LineItem> items;
    private Customer customer;
    private Address deliveryAddress;
    private Payment payment;

    public Cart()
    {
        items = new ArrayList<>();
        customer = new Customer();
        deliveryAddress = new Address();
        payment = new Payment();
    }

    public void addItem(Product product)
    {
        for (LineItem lineItem : items)
        {
            if (lineItem.getProduct().getSku().equals(product.getSku()))
            {
                lineItem.setQuantity(lineItem.getQuantity() + 1);
                return;
            }
        }
        LineItem item = new LineItem(product, 1);
        this.items.add(item);
    }

    public void updateItemQuantity(Product product, int quantity)
    {
        for (LineItem lineItem : items)
        {
            if (lineItem.getProduct().getSku().equals(product.getSku()))
            {
                lineItem.setQuantity(quantity);
            }
        }

    }

    public void removeItem(String sku)
    {
        items.removeIf(lineItem -> lineItem.getProduct().getSku().equals(sku));
    }

    public void clearItems()
    {
        items = new ArrayList<>();
    }

    public int getItemCount()
    {
        return items.stream().mapToInt(LineItem::getQuantity).sum();
    }

    public BigDecimal getTotalAmount()
    {
        return items.stream().map(LineItem::getSubTotal).reduce(BigDecimal.ZERO,
                BigDecimal::add);
    }

}
