package com.yourorganization.maven_sample.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Data
@Entity
@Table(name = "repair_requests")

public class RepairEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestEntity request_id;
    @Column(name = "request_date")
    private Timestamp request_date;

    public RepairEntity() {}
}

