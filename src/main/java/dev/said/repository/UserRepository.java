package dev.said.repository;

import dev.said.domains.Document;
import dev.said.domains.User;
import dev.said.enums.EmploymentModel;
import dev.said.enums.Gender;
import dev.said.enums.MartialStatus;
import dev.said.enums.Position;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where upper(u.email) = upper(?1) and upper(u.firstName) = upper(?2) and upper(u.lastName) = upper(?3)")
    Optional<User> findByEmailAndByFirstNameAndByLastName(String email, String firstName, String lastName);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName=:_fname, u.lastName=:_lname, u.dateOfBirth=:_dob, u.gender=:_gender," +
            " u.martialStatus=:_mstatus, u.phoneNumber=:_number, u.email=:_email, u.employmentModel=:_emodel," +
            " u.hireDate=:_hdate, u.resignationDate=:_rdate, u.probationPeriod=:_pperiod, u.position=:_role, u.salary=:_salary," +
            " u.reportingManagerId=:_rmanager where u.email=:_email and u.firstName=:_fname and u.lastName=:_lname")
    void updateUser(
            @Param("_fname") String firstName,
            @Param("_lname") String lastName,
            @Param("_dob") LocalDate dateOfBirth,
            @Param("_gender") Gender gender,
            @Param("_mstatus") MartialStatus martialStatus,
            @Param("_number") String phoneNumber,
            @Param("_email") String email,
            @Param("_emodel") EmploymentModel employmentModel,
            @Param("_hdate") LocalDate hireDate,
            @Param("_rdate") LocalDate resignationDate,
            @Param("_pperiod") String probationPeriod,
            @Param("_role") Position position,
            @Param("_salary") Long salary,
            @Param("_rmanager") Long reportingManagerId
    );

    @Modifying
    @Transactional
    @Query("update User set picture = ?1 where authUserId = ?2")
    void updateProfilePicture(Document file, Long id);

    @Query("select u from User u where u.deleted = false")
    List<User> findAllNonDeletedUsers();


    @Query(value = "select * from users u where u.auth_user_id in (select au.id from auth_user au where au.role = 'EMPLOYEE' and au.deleted = false) and u.deleted = false", nativeQuery = true)
    List<User> findAllByDeletedFalseAndUserRole();

    @Modifying
    @Transactional
    @Query("update User set deleted = true where authUserId = ?1")
    void deleteByAuthUserId(Long id);

}
