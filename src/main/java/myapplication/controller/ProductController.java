package myapplication.controller;

import java.util.List;
import myapplication.dao.IProductDAO;
import myapplication.dto.Product;
import myapplication.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("product")
public class ProductController {

  @Autowired
  private IProductService productService;

  @GetMapping("findAll")
  public ResponseEntity<List<Product>> findAll() {
    List<Product> list = productService.findAll();
    return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<Product> findByid(@PathVariable("id") int id) {
    Product obj = productService.findByid(id);
    return new ResponseEntity<Product>(obj, HttpStatus.OK);
  }

  @PostMapping("create")
  public ResponseEntity<Void> create(@RequestBody Product product) {
    Boolean flag = productService.create(product);
    if (flag.equals(true)) {
      return new ResponseEntity<Void>(HttpStatus.CREATED);
    } else {
      return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping("update")
  public ResponseEntity<Void> update(@RequestBody Product product) {
    Boolean flag = productService.update(product);
    if (flag.equals(true)) {
      return new ResponseEntity<Void>(HttpStatus.OK);
    } else {
      return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") int id){
    Boolean flag = productService.delete(id);
    if (flag.equals(true)) {
      return new ResponseEntity<Void>(HttpStatus.OK);
    } else {
      return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
  }
}
