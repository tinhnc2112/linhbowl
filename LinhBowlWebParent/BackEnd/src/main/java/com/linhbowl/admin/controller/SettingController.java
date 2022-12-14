package com.linhbowl.admin.controller;

import com.linhbowl.entity.Setting;
import com.linhbowl.admin.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SettingController {

    @Autowired
    private SettingService service;

    @GetMapping("settings")
    public String listAll(Model model){
        List<Setting> listSettings = service.listAllSetting();
        for(Setting setting : listSettings){
            model.addAttribute(setting.getKey(), setting.getValue());
        }
        return "settings/settings";
    }

    @PostMapping("/settings/save-mail-server")
    public String saveMailServerSetting(HttpServletRequest request, RedirectAttributes ra){
        List<Setting> serverSettings = service.getMailServerSetting();
        updateSettingValueForm(request, serverSettings);
        ra.addFlashAttribute("message", "Mail server setting have been saved.");
        return "redirect:/settings";
    }

    @PostMapping("/settings/save-mail-templates")
    public String saveMailTemplatesSetting(HttpServletRequest request, RedirectAttributes ra){
        List<Setting> serverSettings = service.getMailTemplateSetting();
        updateSettingValueForm(request, serverSettings);
        ra.addFlashAttribute("message", "Mail template setting have been saved.");
        return "redirect:/settings";
    }

    private void updateSettingValueForm(HttpServletRequest request, List<Setting> listSettings){
        for(Setting setting : listSettings){
            String value = request.getParameter(setting.getKey());
            if(value != null){
                setting.setValue(value);
            }
        }
        service.saveAll(listSettings);
    }
}
