package com.linhbowl.admin.repository;

import com.linhbowl.entity.Setting;
import com.linhbowl.entity.SettingCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends CrudRepository<Setting, String> {

    List<Setting> findByCategory(SettingCategory category);
}
