package com.linhbowl.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "settings")
public class Setting {

    @Id
    @Column(name = "`key`", nullable = false, length = 128)
    private String key;
    @Column(nullable = false, length = 1024)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private com.linhbowl.entity.SettingCategory category;

    public Setting() {
    }

    public Setting(String key, String value, com.linhbowl.entity.SettingCategory category) {
        this.key = key;
        this.value = value;
        this.category = category;
    }

    public Setting(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public com.linhbowl.entity.SettingCategory getCategory() {
        return category;
    }

    public void setCategory(com.linhbowl.entity.SettingCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return Objects.equals(key, setting.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
