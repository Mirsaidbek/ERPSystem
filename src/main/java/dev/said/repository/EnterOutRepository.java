package dev.said.repository;

import dev.said.domains.EnterOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnterOutRepository extends JpaRepository<EnterOut, Long> {

    @Query("select e from EnterOut e where e.userId = ?1 order by e.id desc limit 1")
    EnterOut findLastByUserId(Long userId);

    @Query(nativeQuery = true, value = "select * from enter_out where user_id = ?1 and (cast(entered_at as varchar) like ?2)")
    Optional<List<EnterOut>> findEnterOutByUserIdAndDate(Long userId, String date);


    @Query(nativeQuery = true, value = "select * from enter_out where user_id = :_id and cast(entered_at as varchar) between :_sDate and :_eDate")
    Optional<List<EnterOut>> findEnterOutByUserIdAndIntervalDate(
            @Param("_id") Long userId,
            @Param("_sDate") String startDate,
            @Param("_eDate") String endDate);

}
