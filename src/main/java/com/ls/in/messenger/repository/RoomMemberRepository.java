package com.ls.in.messenger.repository;

import com.ls.in.global.emp.domain.model.Emp;
import com.ls.in.messenger.domain.model.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Integer> {

	@Query("select rm.room.roomId from RoomMember rm where rm.emp.empId = :id")
	public List<Integer> findRoomIdsByEmpId(@Param("id") Integer id);

	@Query("select rm.emp from RoomMember rm where rm.room.roomId = :id")
	public List<Emp> findEmpByRoomId(@Param("id") Integer id);

}
