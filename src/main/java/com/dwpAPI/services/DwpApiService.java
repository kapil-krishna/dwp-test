package com.dwpAPI.services;

import com.dwpAPI.models.PersonApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DwpApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private GeoService geoService;

    private String baseUri = "https://bpdts-test-app.herokuapp.com/";

    private double cityLatt = 51.5074;
    private double cityLong = -0.1278;

    public List<PersonApiModel> getAllUsersInCityOrWithinDistanceOfCity(String cityName, double distanceInMiles) throws RuntimeException {

        HashMap<Integer, PersonApiModel> usersInCityOrWithinDistance = new HashMap<>();

        List<PersonApiModel> usersInCity = getAllUsersInCity(cityName).getBody();

        for (PersonApiModel person: usersInCity) {
            usersInCityOrWithinDistance.put(person.getId(), person);
        }

        List<PersonApiModel> allUsers = getAllUsers().getBody();

        for (PersonApiModel person : allUsers) {
            boolean withinDistance = geoService.isLocationWithinDistance(distanceInMiles, cityLatt, cityLong, person);
            if (withinDistance && !usersInCityOrWithinDistance.containsKey(person.getId())) {
                usersInCityOrWithinDistance.put(person.getId(), person);
            }
        }

        return new ArrayList<>(usersInCityOrWithinDistance.values());
    }

    public ResponseEntity<List<PersonApiModel>> getAllUsersInCity(String cityName) throws RuntimeException {
        String cap = cityName.substring(0, 1).toUpperCase() + cityName.substring((1));

        if (!cap.equals("London")) {
            throw new IllegalArgumentException("Invalid city name. Please only use the city of London.");
        }

        return restTemplate.exchange(
                baseUri + "city/" + cap + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonApiModel>>(){}
        );
    }

    public ResponseEntity<List<PersonApiModel>> getAllUsers() throws RuntimeException {
        return restTemplate.exchange(
                baseUri + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonApiModel>>(){}
        );
    }

}
