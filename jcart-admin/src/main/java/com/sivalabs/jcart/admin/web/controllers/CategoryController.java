/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import java.util.List;

import javax.validation.Valid;

import com.sivalabs.jcart.admin.web.security.SecurityUtil;
import com.sivalabs.jcart.catalog.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.admin.web.validators.CategoryValidator;
import com.sivalabs.jcart.entities.Category;

/**
 * @author Siva
 *
 */
@Controller
@Secured(SecurityUtil.MANAGE_CATEGORIES)
@Slf4j
public class CategoryController extends JCartAdminBaseController
{
	private static final String viewPrefix = "categories/";
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired private CategoryValidator categoryValidator;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Categories";
	}
	
	@RequestMapping(value="/categories", method=RequestMethod.GET)
	public String listCategories(Model model) {
		List<Category> list = catalogService.getAllCategories();
		model.addAttribute("categories",list);
		return viewPrefix+"categories";
	}
	
	@RequestMapping(value="/categories/new", method=RequestMethod.GET)
	public String createCategoryForm(Model model) {
		Category category = new Category();
		model.addAttribute("category",category);
		
		return viewPrefix+"create_category";
	}

	@RequestMapping(value="/categories", method=RequestMethod.POST)
	public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		categoryValidator.validate(category, result);
		if(result.hasErrors()){
			return viewPrefix+"create_category";
		}
		Category persistedCategory = catalogService.createCategory(category);
		log.debug("Created new category with id : {} and name : {}", persistedCategory.getId(), persistedCategory.getName());
		redirectAttributes.addFlashAttribute("info", "Category created successfully");
		return "redirect:/categories";
	}
	
	@RequestMapping(value="/categories/{id}", method=RequestMethod.GET)
	public String editCategoryForm(@PathVariable Integer id, Model model) {
		Category category = catalogService.getCategoryById(id);
		model.addAttribute("category",category);
		return viewPrefix+"edit_category";
	}
	
	@RequestMapping(value="/categories/{id}", method=RequestMethod.POST)
	public String updateCategory(Category category, Model model, RedirectAttributes redirectAttributes) {
		Category persistedCategory = catalogService.updateCategory(category);
		log.debug("Updated category with id : {} and name : {}", persistedCategory.getId(), persistedCategory.getName());
		redirectAttributes.addFlashAttribute("info", "Category updated successfully");
		return "redirect:/categories";
	}

}
