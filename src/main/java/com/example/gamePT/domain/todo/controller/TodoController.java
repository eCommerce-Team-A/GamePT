package com.example.gamePT.domain.todo.controller;

import com.example.gamePT.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/todosJava")
    public void getTodos() {

    }

    @PostMapping
    public void postTodos() {

    }

    @PutMapping
    public void putTodos() {

    }

    @DeleteMapping
    public void deleteTodos() {
        return;
    }
}
