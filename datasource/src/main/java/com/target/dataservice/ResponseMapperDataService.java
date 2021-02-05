package com.target.dataservice;

import com.target.model.ResponseMapper;
import com.target.repository.ResponseMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseMapperDataService {

    @Autowired
    private ResponseMapperRepository responseMapperRepository;

    public ResponseMapper findByResponseCode(String respCode)
    {
        return responseMapperRepository.findByResponseCode(respCode);
    }
}

