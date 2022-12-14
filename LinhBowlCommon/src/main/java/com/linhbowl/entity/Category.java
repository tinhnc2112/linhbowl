package com.linhbowl.entity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 128, nullable = false)
    private String image;
    private boolean enabled;

    public Category() {
    }

    public Category(int id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
        this.image = "default.png";
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Transient
    public String getImagePath() {
        if (this.id == null) {
            return "/images/image-thumbnail.png";
        }
        return "/category-images/" + this.id + "/" + this.image;
    }
}
