package com.ls.in.post.repository;

import com.ls.in.post.domain.model.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile, Integer> {
}
