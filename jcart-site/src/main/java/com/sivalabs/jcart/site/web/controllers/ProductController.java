/**
 * 
 */
package com.sivalabs.jcart.site.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Product;
import com.sivalabs.jcart.site.web.utils.WebUtils;

/**
 * @author Siva
 *
 */
@Controller
public class ProductController extends JCartSiteBaseController
{	
	@Autowired
	private CatalogService catalogService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Product";
	}	
	
	@RequestMapping("/products/{sku}")
	public String product(@PathVariable String sku, Model model)
	{
		Product product = catalogService.getProductBySku(sku);
		model.addAttribute("product", product);
		return "product";
	}
	
	@RequestMapping("/products")
	public String searchProducts(@RequestParam(name="q", defaultValue="") String query, Model model)
	{
		List<Product> products = catalogService.searchProducts(query);
		model.addAttribute("products", products);
		return "products";
	}

	@RequestMapping(value="/products/images/{productId}", method=RequestMethod.GET)
	public void showProductImage(@PathVariable String productId, HttpServletRequest request, HttpServletResponse response) {
		try {
			FileSystemResource file = new FileSystemResource(WebUtils.IMAGES_DIR +productId+".jpg");     
			response.setContentType("image/jpg");
			org.apache.commons.io.IOUtils.copy(file.getInputStream(), response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}	      
	}
}
