package com.example.irsyad.sitodo.controller;

import com.example.irsyad.sitodo.model.TodoItem;
import com.example.irsyad.sitodo.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoListController {

    private TodoListService todoListService;

    @Autowired
    public void setTodoListService(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<TodoItem> todoItems = todoListService.getTodoItems();

        model.addAttribute("todoList", todoItems);

        return "list";
    }

    @PostMapping("/list")
    public String addList(@RequestParam("item_text") String title){
        todoListService.addTodoItem(new TodoItem(title));

        return "redirect:/list";
    }
}
