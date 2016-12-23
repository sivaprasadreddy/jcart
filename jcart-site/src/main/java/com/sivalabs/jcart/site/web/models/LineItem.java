/**
 * 
 */
package com.sivalabs.jcart.site.web.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sivalabs.jcart.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Siva
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
