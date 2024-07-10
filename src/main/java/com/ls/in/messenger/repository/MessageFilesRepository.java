package com.ls.in.messenger.repository;

import com.ls.in.messenger.domain.model.MessageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageFilesRepository extends JpaRepository<MessageFile, Integer> {
}
