package com.ls.in.messenger.service;

import com.ls.in.messenger.domain.model.Message;
import com.ls.in.messenger.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
}
