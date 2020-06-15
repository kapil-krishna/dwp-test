package com.dwpAPI.services;

import com.dwpAPI.models.PersonApiModel;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GeoServiceTest {

    GeoService geoService = new GeoService();

    @Test
    public void returnsTrueForLocationWithinDistance() {
        double radiusOfDistance = 20;
        double sourceLat = 51.5074;
        double sourceLong = 0.1278;
        PersonApiModel person = new PersonApiModel();
        person.setLatitude(51.5074);
        person.setLongitude(0.1279);

        boolean result = geoService.isLocationWithinDistance(
                radiusOfDistance,
                sourceLat, sourceLong, person);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void returnsFalseForLocationOutsideOfDistance() {
        double radiusOfDistance = 20;
        double sourceLat = 51.5074;
        double sourceLong = 0.1278;
        PersonApiModel person = new PersonApiModel();
        person.setLatitude(55.3934);
        person.setLongitude(0.1279);

        boolean result = geoService.isLocationWithinDistance(
                radiusOfDistance,
                sourceLat, sourceLong,
                person);

        assertThat(result).isEqualTo(false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwsExceptionWhenLongIsOutOfRange() {
        double radiusOfDistance = 20;
        double sourceLat = 51.5074;
        double sourceLong = 181;
        PersonApiModel person = new PersonApiModel();
        person.setLatitude(55.3934);
        person.setLongitude(0.1279);

       geoService.isLocationWithinDistance(
                radiusOfDistance,
                sourceLat, sourceLong,
                person);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenLatIsOutOfRange() {
        double radiusOfDistance = 20;
        double sourceLat = 91;
        double sourceLong = 0.1312;
        PersonApiModel person = new PersonApiModel();
        person.setLatitude(55.3934);
        person.setLongitude(0.1279);

        geoService.isLocationWithinDistance(
                radiusOfDistance,
                sourceLat, sourceLong,
                person);
    }
}
