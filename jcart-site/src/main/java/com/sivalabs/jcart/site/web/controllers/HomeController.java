package com.sivalabs.jcart.site.web.controllers;

import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

/**
 * @author Siva
 *
 */
@Controller
public class HomeController extends AbstractJCartSiteController
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
        List<Category> previewCategories = new ArrayList<>();
        List<Category> categories = catalogService.getAllCategories();
        for (Category category : categories)
        {
            int noOfProductsToDisplay = 4;
            Set<Product> previewProducts = category.getProducts().stream()
                    .limit(noOfProductsToDisplay).collect(toSet());
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
