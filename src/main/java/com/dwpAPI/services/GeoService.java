package com.dwpAPI.services;

import com.dwpAPI.models.PersonApiModel;
import org.springframework.stereotype.Service;

@Service
public class GeoService {

    //taken from https://www.movable-type.co.uk/scripts/latlong.html

    public boolean isLocationWithinDistance(double radiusInMiles,
                                            double sourceLat, double sourceLong,
                                            PersonApiModel person) throws IllegalArgumentException {

        boolean isLatLongWithinRange = checkIfLatLongAreWithinRange(
                sourceLat, sourceLong,
                person.getLatitude(), person.getLongitude());

        if (!isLatLongWithinRange) {
            throw new IllegalArgumentException("Latitude=" + person.getLatitude() +  "or Longitude=" + person.getLongitude() + "are not valid values for id=" + person.getId());
        }

        int earthMeanRadius = 6371;

        double latDiff = Math.toRadians(sourceLat -  person.getLatitude());
        double longDiff = Math.toRadians(sourceLong - person.getLongitude());
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(sourceLat)) * Math.cos(Math.toRadians(person.getLatitude()))
                * Math.sin(longDiff / 2) * Math.sin(longDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthMeanRadius * c;

        double radiusInKm = radiusInMiles * 1.609344;

        return radiusInKm >= distance;
    }

    public boolean checkIfLatLongAreWithinRange(double sourceLat, double sourceLong,
                                                   double destinationLat, double destinationLong) {
        boolean sourceLatInRange = Math.abs(sourceLat) <= 90;
        boolean destLatInRange = Math.abs(destinationLat) <= 90;
        boolean sourceLongInRange = Math.abs(sourceLong) <= 180;
        boolean destLongInRange = Math.abs(destinationLong) <= 180;


        return sourceLatInRange && destLatInRange
                && sourceLongInRange && destLongInRange;
    }

}
