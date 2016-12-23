/**
 * 
 */
package com.sivalabs.jcart.site.web.models;

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
public class LineItem
{
    private Product product;
    private int quantity;

    public BigDecimal getSubTotal()
    {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

}
