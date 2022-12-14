package com.linhbowl.entity;

import javax.persistence.*;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 256, nullable = false)
    private String name;

    @Column(length = 4096, nullable = false)
    private String description;

    @Column(length = 64)
    private String photo;

    private boolean enabled;

    private float price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private com.linhbowl.entity.Category category;

    public Product(Integer id) {
        this.id = id;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public com.linhbowl.entity.Category getCategory() {
        return category;
    }

    public void setCategory(com.linhbowl.entity.Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
    @Transient
    public String getImagePath() {
        if (this.id == null) {
            return "/images/image-rice_thumbnail.png";
        }
        return "/product-images/" + this.id + "/" + this.photo;
    }
}
