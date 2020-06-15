package com.dwpAPI.services;

import com.dwpAPI.models.PersonApiModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class DwpApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GeoService geoService;

    @InjectMocks
    private final DwpApiService dwpApiService = new DwpApiService();

    private final PersonApiModel personApiModel1 = new PersonApiModel();
    private final PersonApiModel personApiModel2 = new PersonApiModel();

    private final List<PersonApiModel> fakeList1 = new ArrayList<>();
    private final List<PersonApiModel> fakeList2 = new ArrayList<>();

    private final String baseUri = "https://bpdts-test-app.herokuapp.com/";
    private double cityLatt = 51.5074;
    private double cityLong = -0.1278;

    @Test
    public void givenMocks_whenGetAllUsers_returnMockedPersonList() {
        personApiModel1.setId(1);
        fakeList1.add(personApiModel1);
        ResponseEntity<List<PersonApiModel>> expected = new ResponseEntity<>(fakeList1, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                baseUri + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonApiModel>>(){}))
                .thenReturn(expected);

        ResponseEntity<List<PersonApiModel>> actual = dwpApiService.getAllUsers();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void givenMocks_whenGetAllUsersInCity_returnMockedPersonList() {
        personApiModel2.setId(2);
        fakeList2.add(personApiModel2);
        ResponseEntity<List<PersonApiModel>> expected = new ResponseEntity<>(fakeList2, HttpStatus.OK);

        String cityName = "london";
        String cap = "London";

        Mockito.when(restTemplate.exchange(
                baseUri + "city/" + cap + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonApiModel>>(){}))
                .thenReturn(expected);

        ResponseEntity<List<PersonApiModel>> actual = dwpApiService.getAllUsersInCity(cityName);

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void givenMocks_whenGetAllUsersInAndAroundCity_returnMockedPersonList() {
        personApiModel1.setId(1);
        fakeList1.add(personApiModel1);
        ResponseEntity<List<PersonApiModel>> expected1 = new ResponseEntity<>(fakeList1, HttpStatus.OK);

        personApiModel2.setId(2);
        fakeList2.add(personApiModel2);
        ResponseEntity<List<PersonApiModel>> expected2 = new ResponseEntity<>(fakeList2, HttpStatus.OK);

        List<PersonApiModel> expected = new ArrayList<>();
        expected.addAll(fakeList1);
        expected.addAll(fakeList2);

        String cityName = "london";
        double distanceInMiles = 20;

        Mockito.when(dwpApiService.getAllUsers())
                .thenReturn(expected1);

        Mockito.when(dwpApiService.getAllUsersInCity(cityName))
                .thenReturn(expected2);

        Mockito.when(geoService.isLocationWithinDistance(anyDouble(), anyDouble(), anyDouble(), any()))
                .thenReturn(true);

//        Mockito.doReturn(true).when(geoService).isLocationWithinDistance(anyDouble(), anyDouble(), anyDouble(), any());

        List<PersonApiModel> actual = dwpApiService.getAllUsersInCityOrWithinDistanceOfCity(cityName, distanceInMiles);

        Assert.assertEquals(expected, actual);
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwsExceptionIfCityNameIsNotLondon() {
        String cityName = "new york";
        dwpApiService.getAllUsersInCity(cityName);
    }



}