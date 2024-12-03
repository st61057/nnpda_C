package org.example.nnpda_sem_c;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor
public class SpatialService {

    private JdbcTemplate jdbcTemplate;

    public List<Edge> findNearestNodesAndEdges(double latitude, double longitude) {
        String sql = "SELECT n.NODE_NAME, n.GEOMETRY.SDO_POINT.X AS LONGITUDE, n.GEOMETRY.SDO_POINT.Y AS LATITUDE, e.NAME AS EDGE_NAME, e.LENGTH AS EDGE_VALUE FROM nodes n JOIN edges e ON SDO_RELATE(e.GEOMETRY, n.GEOMETRY, 'mask=ANYINTERACT') = 'TRUE' WHERE SDO_NN(n.GEOMETRY, SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE(?, ?, NULL), NULL, NULL), 'sdo_num_res=3 unit=KM', 1) = 'TRUE'";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Edge city = new Edge();
            city.setNodeName(rs.getString("NODE_NAME"));
            city.setLongitude(rs.getDouble("LONGITUDE"));
            city.setLatitude(rs.getDouble("LATITUDE"));
            city.setEdgeName(rs.getString("EDGE_NAME"));
            city.setEdgeDistance(rs.getDouble("EDGE_VALUE"));
            return city;
        }, longitude, latitude);
    }

    public List<Node> findNearestNodes(double latitude, double longitude) {
        String sql = "SELECT n.NODE_NAME, " +
                "n.GEOMETRY.SDO_POINT.Y AS latitude, " +
                "n.GEOMETRY.SDO_POINT.X AS longitude, " +
                "SDO_NN_DISTANCE(1) AS distance " +
                "FROM nodes n " +
                "WHERE SDO_NN(GEOMETRY, " +
                "            SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE(?, ?, NULL), NULL, NULL), " +
                "            'sdo_num_res=3 unit=KM', 1) = 'TRUE' " +
                "ORDER BY distance";

        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters: longitude=" + longitude + ", latitude=" + latitude);

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Node city = new Node();
            city.setNodeName(rs.getString("NODE_NAME"));
            city.setLatitude(rs.getDouble("LATITUDE"));
            city.setLongitude(rs.getDouble("LONGITUDE"));
            city.setDistance(rs.getDouble("DISTANCE"));
            return city;
        }, longitude, latitude);
    }


}
