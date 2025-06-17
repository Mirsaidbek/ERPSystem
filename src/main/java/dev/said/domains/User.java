package dev.said.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.said.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

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

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MartialStatus martialStatus;

    @NotBlank(message = "Phone number is required")
//    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Please provide a valid phone number")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private EmploymentModel employmentModel;

    @NotNull(message = "Hire date is required")
    @Column(nullable = false)
    private LocalDate hireDate;

    @Column(nullable = true) // Changed to nullable as not all employees may have resigned
    private LocalDate resignationDate;

    @NotBlank(message = "Probation period is required")
    @Column(nullable = false)
    private String probationPeriod; // Kept as String as it might be a duration format

    @Enumerated(EnumType.STRING)
    private Position position;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be positive")
    @Column(nullable = false)
    private Long salary;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Document picture;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Document resume;

    private Long reportingManagerId;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;
}
