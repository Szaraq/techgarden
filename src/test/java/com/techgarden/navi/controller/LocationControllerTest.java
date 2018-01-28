package com.techgarden.navi.controller;

import com.techgarden.navi.model.LocationRequest;
import com.techgarden.navi.model.db.Location;
import com.techgarden.navi.repository.LocationRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocationControllerTest {
    @Mock
    private LocationRepository locationRepository;

    private LocationController locationController;

    @Before
    public void setup() {
        locationController = new LocationController(locationRepository);
    }

    @Test
    public void testSave() throws IOException {
        LocationRequest request = new LocationRequest((long) 1, 12.34, 56.78);
        Location location = new Location((long) 1, null, 12.34, 56.78);
        locationController.save(request, mock(HttpServletResponse.class));
        verify(locationRepository).save(eq(location));
    }

    @Test
    public void testSaveWithInvalidCoordinates() throws IOException {
        checkCoordinates(181, 0);
        checkCoordinates(-181, 0);
        checkCoordinates(0, 91);
        checkCoordinates(0, -91);
    }

    private void checkCoordinates(double longitude, double latitude) throws IOException {
        LocationRequest request = new LocationRequest((long) 1, longitude, latitude);
        HttpServletResponse response = mock(HttpServletResponse.class);
        locationController.save(request, response);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Invalid coordinates: %s %s", longitude, latitude));
    }

    @Test
    public void testGetCurrentPosition() throws NotFoundException, IOException {
        Location location = new Location((long) 1, new Date(1517151460), 12.34, 56.78);

        when(locationRepository.findLastByDevice((long) 1)).thenReturn(location);
        Location actual = locationController.getCurrentPosition((long) 1, mock(HttpServletResponse.class));

        assertEquals(location, actual);
    }

    @Test
    public void testGetCurrentPositionNotFound() throws NotFoundException, IOException {
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        when(locationRepository.findLastByDevice((long) 1)).thenThrow(new NotFoundException("No data for device: 1"));

        Location actual = locationController.getCurrentPosition((long) 1, servletResponse);

        verify(servletResponse).sendError(HttpServletResponse.SC_NOT_FOUND, "No data for device: 1");
        assertNull(actual);
    }
}
