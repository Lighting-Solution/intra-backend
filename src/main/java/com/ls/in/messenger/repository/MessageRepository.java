package com.ls.in.messenger.repository;

import com.ls.in.messenger.domain.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByRoomMemberRoomRoomId(Integer roomId);
}
*/

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message>  findByRoomMemberRoomMemberIdIn(List<Integer> roomMemberIds);

	@Modifying
	@Query("delete from Message m where m.roomMember.roomMemberId = :roomMemberId")
	void deleteMessagesByRoomMemberId(@Param("roomMemberId") Integer roomMemberId);

	//findByRoomMemberRoomMemberIdIn
}