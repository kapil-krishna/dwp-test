package com.dwpAPI.controllers;

import com.dwpAPI.models.PersonApiModel;
import com.dwpAPI.services.DwpApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private DwpApiService dwpApiService;

    @Autowired
    public UserController(DwpApiService dwpApiService) {
        this.dwpApiService = dwpApiService;
    }

    @RequestMapping(value = "/{cityName}", method = RequestMethod.GET)
    @ResponseBody
    public List<PersonApiModel> getAllUsersInCityAndWithinDistanceOfCity(
            @PathVariable String cityName,
            @RequestParam double distanceInMiles) {
        return dwpApiService.getAllUsersInCityOrWithinDistanceOfCity(cityName, distanceInMiles);
    }

}
