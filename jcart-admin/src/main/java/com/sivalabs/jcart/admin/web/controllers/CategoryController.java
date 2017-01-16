package com.sivalabs.jcart.admin.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.admin.security.SecurityUtil;
import com.sivalabs.jcart.admin.web.validators.CategoryValidator;
import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Category;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */

@Slf4j
@Controller
@Secured(SecurityUtil.MANAGE_CATEGORIES)
public class CategoryController extends AbstractJCartAdminController
{
    private static final String VIEWPREFIX = "categories/";

    private CatalogService catalogService;
    private CategoryValidator categoryValidator;
    
    /**
     * @param catalogService
     * @param categoryValidator
     */
    public CategoryController(CatalogService catalogService,
            CategoryValidator categoryValidator)
    {
        super();
        this.catalogService = catalogService;
        this.categoryValidator = categoryValidator;
    }

    @Override
    protected String getHeaderTitle()
    {
        return "Manage Categories";
    }

    @GetMapping(value = "/categories")
    public String listCategories(Model model)
    {
        List<Category> list = catalogService.getAllCategories();
        model.addAttribute("categories", list);
        return VIEWPREFIX + "categories";
    }

    @GetMapping(value = "/categories/new")
    public String createCategoryForm(Model model)
    {
        Category category = new Category();
        model.addAttribute("category", category);

        return VIEWPREFIX + "create_category";
    }

    @PostMapping(value = "/categories")
    public String createCategory(@Valid @ModelAttribute("category") Category category,
            BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        categoryValidator.validate(category, result);
        if (result.hasErrors())
        {
            return VIEWPREFIX + "create_category";
        }
        Category persistedCategory = catalogService.createCategory(category);
        log.debug("Created new category with id : {} and name : {}",
                persistedCategory.getId(), persistedCategory.getName());
        redirectAttributes.addFlashAttribute("info", "Category created successfully");
        return "redirect:/categories";
    }

    @GetMapping(value = "/categories/{id}")
    public String editCategoryForm(@PathVariable Integer id, Model model)
    {
        Category category = catalogService.getCategoryById(id);
        model.addAttribute("category", category);
        return VIEWPREFIX + "edit_category";
    }

    @PostMapping(value = "/categories/{id}")
    public String updateCategory(Category category, Model model,
            RedirectAttributes redirectAttributes)
    {
        Category persistedCategory = catalogService.updateCategory(category);
        log.debug("Updated category with id : {} and name : {}",
                persistedCategory.getId(), persistedCategory.getName());
        redirectAttributes.addFlashAttribute("info", "Category updated successfully");
        return "redirect:/categories";
    }

}
