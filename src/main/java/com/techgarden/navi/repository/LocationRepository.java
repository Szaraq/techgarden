package com.techgarden.navi.repository;

import com.techgarden.navi.model.db.Location;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query("select l from Location l where l.device = ?1 order by l.id desc")
    List<Location> findByDevice(Long device, Pageable pageable);

    default Location findLastByDevice(Long device) throws NotFoundException {
        List<Location> locationList = findByDevice(device, new PageRequest(0, 1));
        if(locationList.isEmpty()) {
            throw new NotFoundException("No data for device: " + device);
        }
        return locationList.get(0);
    }
}
