package dev.said.domains;

import dev.said.enums.EmploymentModel;
import dev.said.enums.Gender;
import dev.said.enums.MartialStatus;
import dev.said.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
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
    private Role role;

    @Column(nullable = false)
    private Long salary;

    private Long reportingManagerId;
}
