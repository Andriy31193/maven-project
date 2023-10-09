package com.yourorganization.maven_sample.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@Entity
@Table(name = "requests")
public class RequestEntity {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer request_id;
    @Column(name = "destination")
    private String destination;
    @Column(name = "cargo_type")
    private String cargo_type;
    @Column(name = "cargo_quantity")
    private Integer cargo_quantity;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private DriverEntity driver_id;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle_id;
    @Column(name = "cargo_weight")
    private Double cargo_weight;

    public RequestEntity() {}
}
