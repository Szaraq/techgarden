package com.techgarden.navi.controller;

import com.techgarden.navi.model.LocationRequest;
import com.techgarden.navi.model.db.Location;
import com.techgarden.navi.repository.LocationRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/navi/location")
public class LocationController {
    private static final Logger LOG = LoggerFactory.getLogger(LocationController.class);
    private static final double MAX_LONGITUDE = 180;
    private static final double MAX_LATITUDE = 90;

    private LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody LocationRequest locationRequest, HttpServletResponse response) throws IOException {
        if(!validateCoordinates(locationRequest.getLongitude(), locationRequest.getLatitude())) {
            String message = String.format("Invalid coordinates: %s %s", locationRequest.getLongitude(), locationRequest.getLatitude());
            LOG.error(message);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, message);
            return;
        }
        Location location = new Location(locationRequest.getDevice(), null, locationRequest.getLongitude(), locationRequest.getLatitude());
        locationRepository.save(location);
        LOG.info("Saved: {}", location);
    }

    private boolean validateCoordinates(double longitude, double latitude) {
        return !(longitude > MAX_LONGITUDE || longitude < -MAX_LONGITUDE ||
                latitude > MAX_LATITUDE || latitude < -MAX_LATITUDE);
    }

    @RequestMapping(value = "/getCurrentPosition/{id:\\d+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Location getCurrentPosition(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
        try {
            return locationRepository.findLastByDevice(id);
        } catch(NotFoundException exc) {
            LOG.error(exc.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND, exc.getMessage());
            return null;
        }
    }
}
