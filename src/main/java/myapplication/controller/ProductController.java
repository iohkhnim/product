package myapplication.controller;

import java.util.List;
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

  /**
   * <p>An API endpoint (/product/findAll) with method GET gets information of all Products </p>
   * @return Return all products information
   */
  @GetMapping("findAll")
  public ResponseEntity<List<Product>> findAll() {
    List<Product> list = productService.findAll();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * <p>An API endpoint (/product/{id}) with method GET gets information of given Product ID</p>
   * @param id Product ID
   * @return Return information of given Product ID
   */
  @GetMapping("{id}")
  public ResponseEntity<Product> findByid(@PathVariable("id") int id) {
    Product obj = productService.findByid(id);
    return new ResponseEntity<>(obj, HttpStatus.OK);
  }

  /**
   * <p>An API endpoint (/product/create) with method POST creates a product</p>
   * @param product Product information
   * @return Https status according to result
   */
  @PostMapping("create")
  public ResponseEntity<String> create(@RequestBody Product product) {
    int id = productService.create(product);
    if (id > 0) {
      return new ResponseEntity<>(String.valueOf(id), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>("-1", HttpStatus.CONFLICT);
    }
  }

  /**
   * <p>An API endpoint (/product/update) with method PUT updates a product</p>
   * @param product product information
   * @return Https status according to result
   */
  @PutMapping("update")
  public ResponseEntity<String> update(@RequestBody Product product) {
    int id = productService.update(product);
    if (id > 0) {
      return new ResponseEntity<>(String.valueOf(id),HttpStatus.OK);
    } else {
      return new ResponseEntity<>("-1", HttpStatus.CONFLICT);
    }
  }

  /**
   * <p>An API endpoint (/product/delete/{id}) with method DELETE deletes a product </p>
   * @param id Product ID needs to be deleted
   * @return Return Http status according to result
   */
  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") int id) {
    Boolean flag = productService.delete(id);
    if (flag.equals(true)) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
