package dev.said.repository;

import dev.said.domains.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("select l from LeaveRequest l where l.employeeId = ?1")
    Optional<List<LeaveRequest>> findAllByEmployeeId(Long employeeId);

    @Query("select l from LeaveRequest l where l.employeeId = ?1 order by l.createdAt desc limit 1")
    Optional<LeaveRequest> findLastRequestByEmployeeId(Long employeeId);

    @Query("select l from LeaveRequest l where l.leaveRequestStatus = 'PENDING' and l.deleted = false")
    Optional<List<LeaveRequest>> findAllPendingLeaveRequests();

    @Query("select l from LeaveRequest l where l.leaveRequestStatus = 'APPROVED' and l.deleted = false")
    Optional<List<LeaveRequest>> findAllApprovedLeaveRequests();

    @Query("select l from LeaveRequest l where l.leaveRequestStatus = 'REJECTED' and l.deleted = false")
    Optional<List<LeaveRequest>> findAllRejectedLeaveRequests();

    @Query("select l from LeaveRequest l where l.deleted = false")
    List<LeaveRequest> findAllNonDeletedLeaveRequests();

}
