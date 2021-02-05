package com.target.validator;

import com.target.dataservice.PropertiesDataService;
import com.target.enums.Enums;
import com.target.exceptions.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.target.dto.StartGameRequest;
import org.springframework.util.CollectionUtils;

import static com.target.enums.Enums.Properties.NUMBER_OF_PLAYERS_PER_LANE;

@Component
public class RequestValidator {

    @Autowired
    private PropertiesDataService propertiesDataService;

    public void validateStartGameRequest(StartGameRequest request)
    {
        if(request==null)
            throw new InvalidRequestException(Enums.ResponseConstant.NULL_REQUEST_RECEIVED.name());

        if(CollectionUtils.isEmpty(request.getPlayerNames()))
            throw new InvalidRequestException(Enums.ResponseConstant.INVALID_PLAYERS.name());

        int noOfPlayersPerLane = Integer.parseInt(propertiesDataService.getPropertyValueByName(NUMBER_OF_PLAYERS_PER_LANE.name()));

        if(request.getPlayerNames().size()%noOfPlayersPerLane == 1)
            throw new InvalidRequestException(Enums.ResponseConstant.INVALID_PLAYERS.name());
    }
}
