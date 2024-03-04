package com.example.gamePT.domain.chatLog.repoistory;

import com.example.gamePT.domain.chatLog.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
}
