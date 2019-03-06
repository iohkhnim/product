package myapplication.dto;

import java.io.Serializable;

public class Price implements Serializable {

  private int product_id;

  private int price;

  public int getProduct_id() {
    return product_id;
  }

  public void setProduct_id(int product_id) {
    this.product_id = product_id;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
