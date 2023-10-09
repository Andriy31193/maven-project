package com.yourorganization.maven_sample.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Data
@Entity
@Table(name = "drivers")

public class DriverEntity {

    @Id
    @Column(name = "driver_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driver_id;

    @Column(name = "name")
    private String name;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "available")
    private Boolean available;

    public DriverEntity() {}

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driver_id +
                ", name='" + name + '\'' +
                ", experience=" + experience +
                ", isAvailable=" + available +
                '}';
    }
}
