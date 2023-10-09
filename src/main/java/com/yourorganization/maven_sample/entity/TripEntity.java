package com.yourorganization.maven_sample.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Data
@Entity
@Table(name = "trips")
public class TripEntity {

    @Id
    @Column(name = "trip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trip_id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestEntity request_id;

    @Column(name = "start_date")
    private Timestamp start_date;
    @Column(name = "end_date")
    private Timestamp end_date;

    @Column(name = "total_payment")
    private Double total_payment;

    @Column(name = "status")
    private String status;

    public TripEntity() {}

    @Override
    public String toString() {
        return " Trip [" +
                "tripId=" + trip_id +
                ", requestId=" + request_id +
                ", startDate=" + start_date +
                ", endDate=" + end_date +
                ", totalPayment=" + total_payment +
                ", tripStatus='" + status + '\''+
                ']';
    }
}

