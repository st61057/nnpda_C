package org.example.nnpda_sem_c;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SpatialService {

    private JdbcTemplate jdbcTemplate;

    public List<Edge> findNearestCitiesAndRoads(double longitude, double latitude) {
        String sql = "SELECT c.city_name, c.LATITUDE, c.LONGITUDE, r.EDGE_NAME, r.EDGE_VALUE FROM cities c JOIN edges r ON SDO_RELATE(r.SHAPE, SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE(c.longitude, c.latitude, NULL), NULL, NULL), 'mask=ANYINTERACT') = 'TRUE' WHERE SDO_NN(c.shape, SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE(?, ?, NULL),NULL, NULL),'sdo_num_res=3 unit=KM', 1) = 'TRUE'";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Edge city = new Edge();
            city.setCityName(rs.getString("CITY_NAME"));
            city.setLatitude(rs.getDouble("LATITUDE"));
            city.setLongitude(rs.getDouble("LONGITUDE"));
            city.setEdgeName(rs.getString("EDGE_NAME"));
            city.setEdgeValue(rs.getDouble("EDGE_VALUE"));
            return city;
        }, latitude, longitude);

    }

    public List<Node> findNearestCities(double longitude, double latitude) {
        String sql = "SELECT city_name, latitude, longitude, SDO_NN_DISTANCE(1) AS distance FROM CITIES WHERE SDO_NN(shape,SDO_GEOMETRY(2001,8307,SDO_POINT_TYPE(?, ?, NULL),NULL,NULL),'sdo_num_res=3 unit=KM', 1) = 'TRUE' ORDER BY distance";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Node city = new Node();
            city.setCityName(rs.getString("CITY_NAME"));
            city.setLatitude(rs.getDouble("LATITUDE"));
            city.setLongitude(rs.getDouble("LONGITUDE"));
            city.setDistance(rs.getDouble("DISTANCE"));
            return city;
        }, latitude, longitude);

    }


}
