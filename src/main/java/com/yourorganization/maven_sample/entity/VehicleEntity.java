package com.yourorganization.maven_sample.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@Entity
@Table(name = "vehicles")

public class VehicleEntity {

    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicle_id;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "available")
    private boolean available;

    @Column(name = "status")
    private String status;

    public VehicleEntity() {}

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicle_id +
                ", capacity=" + capacity +
                ", isAvailable=" + available +
                '}';
    }
}
