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

    private String nodeName;
    private double latitude;
    private double longitude;

}
