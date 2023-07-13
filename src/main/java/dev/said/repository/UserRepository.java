package dev.said.repository;

import dev.said.domains.Document;
import dev.said.domains.User;
import dev.said.dto.user.CreateUserDTO;
import dev.said.enums.EmploymentModel;
import dev.said.enums.Gender;
import dev.said.enums.MartialStatus;
import dev.said.enums.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where upper(u.email) = upper(?1) and upper(u.firstName) = upper(?2) and upper(u.lastName) = upper(?3)")
    Optional<User> findByEmailAndByFirstNameAndByLastName(String email, String firstName, String lastName);


    @Transactional
    @Modifying
    @Query("update User u set u.firstName=:_fname, u.lastName=:_lname, u.dateOfBirth=:_dob, u.gender=:_gender," +
            " u.martialStatus=:_mstatus, u.phoneNumber=:_number, u.email=:_email, u.employmentModel=:_emodel," +
            " u.hireDate=:_hdate, u.resignationDate=:_rdate, u.probationPeriod=:_pperiod, u.role=:_role, u.salary=:_salary," +
            " u.reportingManagerId=:_rmanager where u.email=:_email and u.firstName=:_fname and u.lastName=:_lname")
    void updateUser(
            @Param("_fname") String s,
            @Param("_lname") String s1,
            @Param("_dob") String s2,
            @Param("_gender") Gender gender,
            @Param("_mstatus") MartialStatus martialStatus,
            @Param("_number") String s3,
            @Param("_email") String email,
            @Param("_emodel") EmploymentModel employmentModel,
            @Param("_hdate") String s4,
            @Param("_rdate") String s5,
            @Param("_pperiod") String s6,
            @Param("_role") Role role,
            @Param("_salary") Long salary,
            @Param("_rmanager") Long aLong
    );

    @Modifying
    @Transactional
    @Query("update User set picture = ?1 where authUserId = ?2")
    void updateProfilePicture(Document file, Long id);

}
