package com.target.controllers;

import com.target.dataservice.ResponseMapperDataService;
import com.target.dto.BaseResponse;
import com.target.enums.Enums;
import com.target.exceptions.InvalidRequestException;
import com.target.model.ResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = GameController.class)
@Slf4j
public class ControllerAdvice {

    @Autowired
    private ResponseMapperDataService responseMapperDataService;

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidRequestException.class)
    public BaseResponse handleValidatorException(InvalidRequestException exc) {

        log.error("Validation Exception, ",exc);
        String responseCode = exc.getRespCode();
        ResponseMapper responseMapper = responseMapperDataService.findByResponseCode(responseCode);
        return new BaseResponse(responseCode,responseMapper.getRespMsg(),"FAILURE");

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception exc)
    {
        log.error("Internal System error, ",exc);
        ResponseMapper responseMapper = responseMapperDataService.findByResponseCode(Enums.ResponseConstant.INTERNAL_SYSTEM_ERROR.name());
        return new BaseResponse(Enums.ResponseConstant.INTERNAL_SYSTEM_ERROR.name(),responseMapper.getRespMsg(),"FAILURE");
    }
}
