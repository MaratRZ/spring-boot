package ru.gb.springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.springboot.entity.Product;
import ru.gb.springboot.service.ProductService;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // метод создания формы
    @GetMapping(value = "/create")
    public String showSimpleForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product-edit";
    }

    // метод вывода одного сообщения в браузер product/3?random=false
    @GetMapping(path = "/{id}")
    public String getProductById(Model model,
                                 @PathVariable Integer id,
                                 @RequestParam(name = "random", defaultValue = "false", required = false) Boolean isRandom) {
        Product product = null;
        if (isRandom) {
            product = productService.getRandomProduct();
        } else {
            product = productService.findById(id);
        }
        model.addAttribute("product", product);
        return "product-view";
    }

    // метод вывода списка сообщений в браузер
    @GetMapping("/all")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    // метод удаления сообщения из хранилища
    @GetMapping(path = "/delete")
    public String deleteById(@RequestParam Integer id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    // метод редактирования
    @GetMapping(path = "/edit")
    public String editById(Model model, @RequestParam Integer id) {
        log.debug("Param ID: " + id);
        Product product = productService.findById(id);
        if (product != null) {
            log.debug("Product is find");
        }
        model.addAttribute("product", product);
        return "product-edit";
    }

    // метод обработки формы и сохранения в бд
    @PostMapping(value = "/edit")
    public String processForm(Product product) {
        if (product.getId() == null) {
            productService.save(product);
        } else {
            productService.edit(product);
        }
        return "redirect:/product/all";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleError(Exception e) {
        log.error("Error: ", e);
        return e.getMessage();
    }
}
