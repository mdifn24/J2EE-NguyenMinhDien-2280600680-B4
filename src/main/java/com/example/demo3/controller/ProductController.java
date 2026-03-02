package com.example.demo3.controller;

import com.example.demo3.model.Product;
import com.example.demo3.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // LIST
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("products", service.getAll());
        return "list";
    }

    // FORM ADD
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "form";
    }

    // SAVE (ADD + UPDATE)
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Product product,
                       BindingResult result,
                       @RequestParam("file") MultipartFile file) throws IOException {

        if (result.hasErrors()) {
            return "form";
        }

        if (!file.isEmpty()) {

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);

            file.transferTo(dest);

            product.setImage(fileName);
        }

        service.save(product);
        return "redirect:/products";
    }

    // FORM EDIT
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = service.getById(id);
        model.addAttribute("product", product);
        return "form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/products";
    }
}