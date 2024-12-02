package org.example.nnpda_sem_c;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Edge {

    private String edgeName;
    private double edgeDistance;

    private double startLatitude;
    private double startLongitude;

    private double endLatitude;
    private double endLongitude;

}
