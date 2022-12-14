package com.linhbowl.admin.controller;

import com.linhbowl.admin.FileUploadUtil;
import com.linhbowl.admin.exeption.CategoryNotFoundExeption;
import com.linhbowl.entity.Category;
import com.linhbowl.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping("/categories")
	public String listFirstPage(Model model) {
		return listByPage(1, null, model);
	}

	@GetMapping("/categories/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, @Param("keyword") String keyword, Model model) {
		Page<Category> page = service.listByPage(pageNum, keyword);
		List<Category> categories = page.getContent();
		long startCount = (pageNum - 1) * CategoryService.CATEGORY_PER_PAGE + 1;
		long endCount = startCount + CategoryService.CATEGORY_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("categories", categories);
		model.addAttribute("keyword", keyword);
		return "categories/categories";
	}

	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("pageTitle", "Create new category");
		return "categories/category_form";
	}

	@PostMapping("/categories/save")
	public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(fileName);
			Category saveCategory = service.save(category);

			String uploadDir = "category-images/" + saveCategory.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			service.save(category);
		}

		ra.addFlashAttribute("message", "The category has saved successfully.");
		return "redirect:/categories";
	}

	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Category category = service.getById(id);
			model.addAttribute("category", category);
			model.addAttribute("pageTitle", "Edit category");
			ra.addFlashAttribute("message", "The category has been updated.");
			return "categories/category_form";
		} catch (CategoryNotFoundExeption e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/categories";
		}
	}
	@GetMapping("/categories/enabled/{id}")
	public String updateEnabledStatus(@PathVariable("id") Integer id, RedirectAttributes ra) throws CategoryNotFoundExeption {
		service.updateEnabledStatus(id);
		String name = service.getById(id).getName();
		String status = service.getEnableById(id)? "disable" : "enable";
		String message = name + " has been " + status;
		ra.addFlashAttribute("message", message);
		return "redirect:/categories";
	}

	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes ra) {
		try {
			service.deleteCategory(id);
			ra.addFlashAttribute("message", "The category ID " + id + " has been deleted successfully");
		}catch (CategoryNotFoundExeption e){
			ra.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/categories";
	}
}
