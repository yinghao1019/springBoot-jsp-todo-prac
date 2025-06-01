package net.guides.springboot.todomanagement.repository;

import net.guides.springboot.todomanagement.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
    List<Todo> findByUserNameOrderByTargetDateDesc(String user);
}
