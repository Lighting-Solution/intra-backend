package com.ls.in.post.repository;

import com.ls.in.post.domain.model.FreePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreePostRepository extends JpaRepository<FreePost, Integer> {
}
