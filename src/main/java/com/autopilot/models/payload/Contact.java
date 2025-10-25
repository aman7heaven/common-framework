package com.autopilot.models.payload;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "tbl_contact_master")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "uuid", length = 255, nullable = false, unique = true)
    private String uuid;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "subject", length = 1000)
    private String subject;

    @Column(name = "message")
    private String message;

    @Column(name = "dt_created_on")
    private OffsetDateTime dtCreatedOn;
}
