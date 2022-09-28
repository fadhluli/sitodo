package com.example.irsyad.sitodo.service;

import com.example.irsyad.sitodo.model.TodoItem;
import com.example.irsyad.sitodo.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {

    private TodoListRepository todoListRepository;

    @Autowired
    public void setTodoListRepository(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public List<TodoItem> getTodoItems(){
        return (List<TodoItem>) todoListRepository.findAll();
    }

    public TodoItem addTodoItem(TodoItem todoItem){
        return todoListRepository.save(todoItem);
    }
}
