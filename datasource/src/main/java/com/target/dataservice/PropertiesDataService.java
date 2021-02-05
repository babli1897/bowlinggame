package com.target.dataservice;

import com.target.model.ApplicationProperties;
import com.target.repository.ApplicationPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertiesDataService {

    @Autowired
    private ApplicationPropertiesRepository propertiesRepository;

    public String getPropertyValueByName(String name)
    {
        ApplicationProperties property = propertiesRepository.findByName(name);
        if(property==null)
            return null;
        return property.getValue();
    }
}
