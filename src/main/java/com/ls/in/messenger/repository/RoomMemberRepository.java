package com.ls.in.messenger.repository;

import com.ls.in.messenger.domain.model.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Integer> {
}
