package com.example.gamePT.domain.chatLog.service;

import com.example.gamePT.domain.chatLog.entity.ChatLog;
import com.example.gamePT.domain.chatLog.repoistory.ChatLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatLogService {
    private final ChatLogRepository chatLogRepository;

    public void save(ChatLog chatLog){
        chatLogRepository.save(chatLog);
    }
}
