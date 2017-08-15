/**
 * 
 */
package com.sivalabs.jcart.site.web.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sivalabs.jcart.catalog.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

/**
 * @author Siva
 *
 */
@Controller
public class HomeController extends JCartSiteBaseController
{	
	@Autowired
	private CatalogService catalogService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Home";
	}
	
	@RequestMapping("/home")
	public String home(Model model)
	{
		List<Category> previewCategories = new ArrayList<Category>();
		List<Category> categories = catalogService.getAllCategories();
		for (Category category : categories)
		{
			Set<Product> products = category.getProducts();
			Set<Product> previewProducts = new HashSet<Product>();
			int noOfProductsToDisplay = 4;
			if(products.size() > noOfProductsToDisplay){
				Iterator<Product> iterator = products.iterator();
				for (int i = 0; i < noOfProductsToDisplay; i++)
				{
					previewProducts.add(iterator.next());
				}
			} else {
				previewProducts.addAll(products);
			}	
			category.setProducts(previewProducts);
			previewCategories.add(category);
		}
		model.addAttribute("categories", previewCategories);
		return "home";
	}
	
	@RequestMapping("/categories/{name}")
	public String category(@PathVariable String name, Model model)
	{
		Category category = catalogService.getCategoryByName(name);
		model.addAttribute("category", category);
		return "category";
	}
	
}
