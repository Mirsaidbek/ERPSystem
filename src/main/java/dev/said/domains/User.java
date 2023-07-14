package dev.said.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.said.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users")
public class User {
    @Id
    private Long authUserId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MartialStatus martialStatus;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private EmploymentModel employmentModel;

    @Column(nullable = false)
    private String hireDate;

    @Column(nullable = false)
    private String resignationDate;

    @Column(nullable = false)
    private String probationPeriod;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false)
    private Long salary;

    @OneToOne
    private Document picture;

    @OneToOne
    private Document resume;

    private Long reportingManagerId;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;
}
