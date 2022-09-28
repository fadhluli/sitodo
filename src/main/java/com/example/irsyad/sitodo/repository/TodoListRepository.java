package com.example.irsyad.sitodo.repository;

import com.example.irsyad.sitodo.model.TodoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends CrudRepository<TodoItem, Long> {
}
