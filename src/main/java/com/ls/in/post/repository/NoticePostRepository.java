package com.ls.in.post.repository;

import com.ls.in.post.domain.model.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticePostRepository extends JpaRepository<NoticePost, Integer> {
}
