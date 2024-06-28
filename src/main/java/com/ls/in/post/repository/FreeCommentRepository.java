package com.ls.in.post.repository;

import com.ls.in.post.domain.model.FreeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeCommentRepository extends JpaRepository<FreeComment, Integer> {
}
