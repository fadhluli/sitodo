package com.example.irsyad.sitodo.controller;

import com.example.irsyad.sitodo.model.TodoItem;
import com.example.irsyad.sitodo.model.TodoList;
import com.example.irsyad.sitodo.service.TodoListService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class TodoListController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoListController.class);

    private TodoListService todoListService;

    @Autowired
    public void setTodoListService(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping("/list")
    public String showList(TodoList todoList, Model model) {
        model.addAttribute("todoList", todoList);

        return "list";
    }

    @GetMapping("/list/{id}")
    public String showList(@PathVariable("id") Long id, Model model) {
        TodoList foundTodoList = todoListService.getTodoListById(id);

        model.addAttribute("todoList", foundTodoList);

        return "list";
    }

    @PostMapping("/list")
    public String newItem(@RequestParam("item_text") String item) {
        TodoList saved = todoListService.addTodoItem(new TodoItem(item));

        return redirectToList(saved.getId());
    }

    @PostMapping("/list/{id}")
    public String newItem(@PathVariable("id") Long id,
                          @RequestParam("item_text") String item) {
        TodoList saved = todoListService.addTodoItem(id, new TodoItem(item));

        return redirectToList(saved.getId());
    }

    @GetMapping("/list/{list_id}/update/{item_id}")
    public String updateItem(@PathVariable("list_id") Long listId,
                             @PathVariable("item_id") Long itemId,
                             @RequestParam("finished") Boolean finished) {

        TodoList updated = todoListService.updateTodoItem(listId, itemId, finished);
        return redirectToList(updated.getId());
    }

    // TODO: Create a method named deleteItem() that will remove a todo item from a todo list.
    //       The arguments can be similar to the updateItem() above.

    @GetMapping("/list/{list_id}/delete/{item_id}")
    public String deleteItem(@PathVariable("list_id") Long listId,
                             @PathVariable("item_id") Long itemId){

        todoListService.deleteTodoItem(listId, itemId);
        return redirectToList(listId);
    }

    @ExceptionHandler
    public String handleException(NoSuchElementException exception) {
        return "404";
    }

    private String redirectToList(Long id) {
        return String.format("redirect:/list/%d", id);
    }

//    private TodoListService todoListService;
//
//    @Autowired
//    public void setTodoListService(TodoListService todoListService) {
//        this.todoListService = todoListService;
//    }


//    @GetMapping("/list")
//    public String showList(Model model) {
//        List<TodoItem> todoItems = todoListService.getTodoItems();
//
//        model.addAttribute("todoList", todoItems);
//
//        return "list";
//    }
//
//    @PostMapping("/list")
//    public String addList(@RequestParam("item_text") String title){
//        todoListService.addTodoItem(new TodoItem(title));
//
//        return "redirect:/list";
//    }
}
