package com.linhbowl.site.service;

import com.linhbowl.entity.Setting;
import com.linhbowl.entity.SettingCategory;
import com.linhbowl.site.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    @Autowired
    private SettingRepository repo;

    public EmailSettingBag getEmailSetting(){
        List<Setting> settings = repo.findByCategory(SettingCategory.MAIL_SERVER);
        settings.addAll(repo.findByCategory(SettingCategory.MAIL_TEMPLATES));
        return new EmailSettingBag(settings);
    }
}
