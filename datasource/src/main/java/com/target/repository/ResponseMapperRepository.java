package com.target.repository;

import com.target.model.ResponseMapper;
import org.springframework.data.repository.CrudRepository;

public interface ResponseMapperRepository extends CrudRepository<ResponseMapper,Long> {

    ResponseMapper findByResponseCode(String respCode);
}
