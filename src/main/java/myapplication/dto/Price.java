package myapplication.dto;

import java.io.Serializable;

public class Price implements Serializable {

  private int product_id;

  private int price;

  private java.util.Date createdTime;

  private java.util.Date updatedTime;

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

  public java.util.Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(java.util.Date createdTime) {
    this.createdTime = createdTime;
  }

  public java.util.Date getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(java.util.Date updatedTime) {
    this.updatedTime = updatedTime;
  }
}
