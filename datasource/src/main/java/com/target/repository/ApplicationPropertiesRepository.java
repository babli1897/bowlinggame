package com.target.repository;

import com.target.model.ApplicationProperties;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationPropertiesRepository extends CrudRepository<ApplicationProperties,Long> {

    ApplicationProperties findByName(String name);
}
