package com.kaiburr.taskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.kaiburr.taskmanager.model.Task;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByNameContaining(String name);
}
