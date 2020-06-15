package com.dwpAPI.controllers;

import com.dwpAPI.models.PersonApiModel;
import com.dwpAPI.services.DwpApiService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private DwpApiService dwpApiService;

    @InjectMocks
    private UserController userController = new UserController(dwpApiService);

    @Test
    public void givenMocks_whenGetAllUsersInAndAroundCity_returnMockedPersonList() throws Exception {
        PersonApiModel personApiModel = new PersonApiModel();
        personApiModel.setId(1);

        List<PersonApiModel> expected = new ArrayList<>();
        expected.add(personApiModel);

        String cityName = "london";
        double distanceInMiles = 50;

        Mockito.when(dwpApiService.getAllUsersInCityOrWithinDistanceOfCity(cityName, distanceInMiles))
                .thenReturn(expected);

        List<PersonApiModel> actual = userController.getAllUsersInCityAndWithinDistanceOfCity(cityName, distanceInMiles);

        Assert.assertEquals(expected, actual);
    }
}