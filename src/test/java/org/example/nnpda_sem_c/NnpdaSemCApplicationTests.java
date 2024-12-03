package org.example.nnpda_sem_c;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xmlunit.util.Nodes;

import java.util.List;

@SpringBootTest
class NnpdaSemCApplicationTests {

    @Autowired
    private SpatialService spatialService;

    @Test
    void contextLoads() {
    }

    @Test
    void findNodes() {
        List<Node> nodes = spatialService.findNearestNodes(50.1, 14.5);
        for (Node node : nodes) {
            System.out.println(node);
        }
    }

    @Test
    void findNodesAndEdges() {
        List<Edge> edges = spatialService.findNearestNodesAndEdges(50.1, 14.5);
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }

}
