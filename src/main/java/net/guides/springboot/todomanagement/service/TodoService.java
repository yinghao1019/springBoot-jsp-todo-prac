package net.guides.springboot.todomanagement.service;

import lombok.RequiredArgsConstructor;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.repository.TodoRepository;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<Todo> getTodosByUser(String user) {
        return todoRepository.findByUserNameOrderByTargetDateDesc(user);
    }

    @Override
    public Optional<Todo> getTodoById(long id) {
        return todoRepository.findById(id);
    }

    @Override
    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }

    @Override
    public void addTodo(String name, String desc, Date targetDate, boolean isDone) {
        Todo todo = new Todo();
        todo.setUserName(name);
        todo.setDescription(desc);
        todo.setTargetDate(targetDate);
        todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(todoRepository::delete);
    }

    @Override
    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }
}
