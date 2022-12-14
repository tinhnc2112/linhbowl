package com.linhbowl.admin.service;

import com.linhbowl.entity.Setting;
import com.linhbowl.entity.SettingCategory;
import com.linhbowl.admin.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    @Autowired
    private SettingRepository repo;

    public List<Setting> listAllSetting(){
        return (List<Setting>) repo.findAll();
    }

    public void saveAll(Iterable<Setting> settings){
        repo.saveAll(settings);
    }

    public List<Setting> getMailServerSetting(){
        return repo.findByCategory(SettingCategory.MAIL_SERVER);
    }

    public List<Setting> getMailTemplateSetting(){
        return repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }

}
