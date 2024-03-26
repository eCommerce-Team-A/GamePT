package com.example.gamePT.domain.todo.repository;

import com.example.gamePT.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {
}
