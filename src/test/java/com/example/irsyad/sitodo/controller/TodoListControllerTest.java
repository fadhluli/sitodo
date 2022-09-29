package com.example.irsyad.sitodo.controller;

import com.example.irsyad.sitodo.model.TodoItem;
import com.example.irsyad.sitodo.service.TodoListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;
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
    @DisplayName("HTTP GET '/list' retrieves view")
    void showList_correctView() throws Exception{
        mockMvc.perform(get("/list")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                view().name("list")
        );
    }

    @Test
    @DisplayName("HTTP GET '/list' returns an HTML page")
    void showList_returnHtml() throws Exception {
        mockMvc.perform(get("/list")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("</html>"))
        );
    }

    @Test
    @DisplayName("HTTP GET '/list' returns an HTML page with non-empty list")
    void showList_withSampleData_ok() throws Exception {
        TodoItem mockTodoItem = new TodoItem("Buy milk");
        when(todoListService.getTodoItems()).thenReturn(List.of(mockTodoItem));

        mockMvc.perform(get("/list")).andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(TEXT_HTML),
                content().encoding(UTF_8),
                content().string(containsString("Buy milk"))
        );
    }

    @Test
    @DisplayName("HTTP POST redirect to '/list' and containt the data")
    void addList_ok() throws Exception {
        String title = "Makan";
        TodoItem todoItem = new TodoItem(title);
        when(todoListService.getTodoItems()).thenReturn(List.of(todoItem));

        mockMvc.perform(post("/list").param("item_text", title)).andExpectAll(
                status().is3xxRedirection()
        );

        mockMvc.perform(get("/list")).andExpectAll(
                status().isOk(),
                content().string(containsString(title))
        );
    }
}
