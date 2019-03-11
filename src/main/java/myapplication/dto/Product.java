package myapplication.dto;

import com.khoi.proto.PriceEntry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "product")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "supplier")
  private String supplier;

  @Column(name = "createdtime", columnDefinition="DATETIME")
  @Nullable
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date createdTime;

  @Column(name = "updatedtime", columnDefinition="DATETIME")
  @Nullable
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date updatedTime;

  @Transient
  private int price;

  @Transient
  private List<Price> priceHistory;

  @Transient
  private Iterator<PriceEntry> priceEntries;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public List<Price> getPriceHistory() {
    return priceHistory;
  }

  public void setPriceHistory(List<Price> priceHistory) {
    this.priceHistory = priceHistory;
  }

  public Iterator<PriceEntry> getPriceEntries() {
    return priceEntries;
  }

  public void setPriceEntries(Iterator<PriceEntry> priceEntries) {
    this.priceEntries = priceEntries;
  }
}
