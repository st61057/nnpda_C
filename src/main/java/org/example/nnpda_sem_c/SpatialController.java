package org.example.nnpda_sem_c;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/spatial")
public class SpatialController {

    private final SpatialService spatialService;

    @GetMapping("/nearest-nodes-and-edges")
    public List<Edge> getNearestCitiesAndRoads(@RequestParam double latitude, @RequestParam double longitude) {
        return spatialService.findNearestCitiesAndRoads(longitude, latitude);
    }

    @GetMapping("/nearest-nodes")
    public List<Node> getNearestCities(@RequestParam double latitude, @RequestParam double longitude) {
        return spatialService.findNearestCities(longitude, latitude);
    }


}
