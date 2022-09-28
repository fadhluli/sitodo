package com.example.irsyad.sitodo.service;

import com.example.irsyad.sitodo.model.TodoItem;
import com.example.irsyad.sitodo.repository.TodoListRepository;
import com.example.irsyad.sitodo.service.TodoListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TodoListServiceTest {

    @Autowired
    private TodoListService todoListService;

    @MockBean
    private TodoListRepository todoListRepository;

    @Test
    void getTodoItems_someItems_ok(){
        when(todoListRepository.findAll()).thenReturn(List.of(
                new TodoItem("A"),
                new TodoItem("B"),
                new TodoItem("C")
        ));

        List<TodoItem> todoItems = todoListService.getTodoItems();

        assertEquals(3, todoItems.size());
    }

    @Test
    void addTodoItem_ok(){
        TodoItem todoItem = new TodoItem("Buy Milk");
        when(todoListRepository.save(any(TodoItem.class))).thenReturn(todoItem);

        TodoItem savedTodoItem = todoListService.addTodoItem(todoItem);

        assertEquals("Buy Milk", savedTodoItem.getTitle());
    }
}
