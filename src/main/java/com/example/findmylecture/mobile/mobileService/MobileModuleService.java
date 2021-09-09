package com.example.findmylecture.mobile.mobileService;

import com.example.findmylecture.model.Module;
import com.example.findmylecture.repository.ModuleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileModuleService {
    @Autowired
    private ModuleRepo moduleRepo;

}
