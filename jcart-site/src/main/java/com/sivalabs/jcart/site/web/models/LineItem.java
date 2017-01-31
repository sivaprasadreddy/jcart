/**
 * 
 */
package com.sivalabs.jcart.site.web.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sivalabs.jcart.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Siva
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class LineItem implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Product product;
    private int quantity;

    public BigDecimal getSubTotal()
    {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

}
