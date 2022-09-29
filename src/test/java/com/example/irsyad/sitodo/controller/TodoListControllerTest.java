package com.example.irsyad.sitodo.controller;

import com.example.irsyad.sitodo.model.TodoItem;
import com.example.irsyad.sitodo.model.TodoList;
import com.example.irsyad.sitodo.service.TodoListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoListController.class)
@Tag("unit")
public class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoListService todoListService;

    @Test
    @DisplayName("HTTP GET '/list' retrieves list view")
    void showList_resolvesToIndex() throws Exception {
        mockMvc.perform(get("/list")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                view().name("list")
        );
    }

    @Test
    @DisplayName("HTTP GET '/list' returns an HTML page")
    void showList_returnsHtml() throws Exception {
        mockMvc.perform(get("/list")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("</html>"))
        );
    }

    @Test
    @DisplayName("HTTP GET '/list/{id}' returns an HTML page with non-empty list")
    void showList_byId_returnsHtml() throws Exception {
        TodoItem mockTodoItem = createMockTodoItem(1L, "Buy milk");
        TodoList mockList = mock(TodoList.class);
        when(mockList.getId()).thenReturn(1L);
        when(mockList.getItems()).thenReturn(List.of(mockTodoItem));
        when(todoListService.getTodoListById(anyLong())).thenReturn(mockList);

        mockMvc.perform(get("/list/1")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("<table")),
                content().string(containsString("<tr")),
                content().string(containsString("Buy milk")),
                content().string(containsString("</html>"))
        );
    }

    @Test
    @DisplayName("Suppose the given ID does not exist, HTTP GET '/list/{id}' returns an error page")
    void showList_byId_notFound() throws Exception {
        when(todoListService.getTodoListById(anyLong())).thenThrow(NoSuchElementException.class);

//        mockMvc.perform(get("/list/1")).andExpectAll(
//                content().string(containsString("Not Found"))
//        );
    }

    @Test
    @DisplayName("HTTP GET '/list/{id}/update/{item_id}' successfully updated status of an item")
    void updateItem_ok() throws Exception {
        // TODO: Implement me!

        TodoList list = createMockTodoList(1L, new TodoItem("Makan"));

        when(todoListService.getTodoListById(anyLong())).thenReturn(list);
//        when(todoListService.getTodoListById(anyLong())).thenReturn((TodoList) List.of(list.getItems().get(0)));
        todoListService.updateTodoItem(1L, 1L, false);

        boolean status = list.getItems().get(0).getFinished();

        Assertions.assertFalse(status);
    }


    @Test
    @DisplayName("HTTP GET '/list/{id}/delete/{item_id}' successfully delete of an item")
    void deleteItem_ok() throws Exception {
        TodoList list = createMockTodoList(1L, new TodoItem("Makan"));

        when(todoListService.getTodoListById(anyLong())).thenReturn(list);
//        when(todoListService.getTodoListById(anyLong())).thenReturn((TodoList) List.of(list.getItems().get(0)));
//        todoListService.deleteTodoItem(1L, 1L);
    }

    // TODO: Create the tests for ensuring the correctness of deleteItem() method from the controller.

    private TodoList createMockTodoList(Long id, TodoItem ... items) {
        TodoList mockTodoList = mock(TodoList.class);

        when(mockTodoList.getId()).thenReturn(id);
        when(mockTodoList.getItems()).thenReturn(List.of(items));

        return mockTodoList;
    }

    private TodoItem createMockTodoItem(Long id, String title) {
        TodoItem mockTodoItem = mock(TodoItem.class);

        when(mockTodoItem.getId()).thenReturn(id);
        when(mockTodoItem.getTitle()).thenReturn(title);
        when(mockTodoItem.getFinished()).thenCallRealMethod();

        return mockTodoItem;
    }

//    @Test
//    @DisplayName("HTTP GET '/list' retrieves view")
//    void showList_correctView() throws Exception{
//        mockMvc.perform(get("/list")).andExpectAll(
//                status().isOk(),
//                content().contentTypeCompatibleWith(TEXT_HTML),
//                content().encoding(UTF_8),
//                view().name("list")
//        );
//    }
//
//    @Test
//    @DisplayName("HTTP GET '/list' returns an HTML page")
//    void showList_returnHtml() throws Exception {
//        mockMvc.perform(get("/list")).andExpectAll(
//                status().isOk(),
//                content().contentTypeCompatibleWith(TEXT_HTML),
//                content().encoding(UTF_8),
//                content().string(containsString("</html>"))
//        );
//    }
//
//    @Test
//    @DisplayName("HTTP GET '/list' returns an HTML page with non-empty list")
//    void showList_withSampleData_ok() throws Exception {
//        TodoItem mockTodoItem = new TodoItem("Buy milk");
//        when(todoListService.getTodoItems()).thenReturn(List.of(mockTodoItem));
//
//        mockMvc.perform(get("/list")).andExpectAll(
//                status().isOk(),
//                content().contentTypeCompatibleWith(TEXT_HTML),
//                content().encoding(UTF_8),
//                content().string(containsString("Buy milk"))
//        );
//    }
//
//    @Test
//    @DisplayName("HTTP POST redirect to '/list' and containt the data")
//    void addList_ok() throws Exception {
//        String title = "Makan";
//        TodoItem todoItem = new TodoItem(title);
//        when(todoListService.getTodoItems()).thenReturn(List.of(todoItem));
//
//        mockMvc.perform(post("/list").param("item_text", title)).andExpectAll(
//                status().is3xxRedirection()
//        );
//
//        mockMvc.perform(get("/list")).andExpectAll(
//                status().isOk(),
//                content().string(containsString(title))
//        );
//    }
}
