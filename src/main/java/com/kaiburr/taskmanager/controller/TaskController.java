package com.kaiburr.taskmanager.controller;

import com.kaiburr.taskmanager.model.Task;
import com.kaiburr.taskmanager.model.TaskExecution;
import com.kaiburr.taskmanager.repository.TaskRepository;
import com.kaiburr.taskmanager.service.CommandValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private CommandValidator commandValidator;

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
        try {
            if (id != null) {
                Optional<Task> task = taskRepo.findById(id);
                if (task.isPresent()) {
                    return ResponseEntity.ok(task.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                List<Task> allTasks = taskRepo.findAll();
                return ResponseEntity.ok(allTasks);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving tasks");
        }
    }

    @PutMapping
    public ResponseEntity<?> putTask(@RequestBody Task task) {
        try {
            if (task.getCommand() == null || !commandValidator.isSafe(task.getCommand())) {
                return ResponseEntity.badRequest().body("Unsafe or missing command");
            }
            Task savedTask = taskRepo.save(task);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving task");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam String id) {
        try {
            if (taskRepo.existsById(id)) {
                taskRepo.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTasks(@RequestParam String name) {
        try {
            List<Task> tasks = taskRepo.findByNameContaining(name);
            if (tasks.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching tasks");
        }
    }

    @PutMapping("/{id}/execute")
    public ResponseEntity<?> executeTask(@PathVariable String id) {
        try {
            Optional<Task> optionalTask = taskRepo.findById(id);
            if (!optionalTask.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Task task = optionalTask.get();
            if (!commandValidator.isSafe(task.getCommand())) {
                return ResponseEntity.badRequest().body("Unsafe command detected");
            }

            Date start = new Date();

            ProcessBuilder pb = new ProcessBuilder(task.getCommand().split(" "));
            pb.redirectErrorStream(true);
            Process proc = pb.start();
            
            Scanner scanner = new Scanner(proc.getInputStream()).useDelimiter("\\A");
            String output = scanner.hasNext() ? scanner.next() : "";
            scanner.close();
            
            proc.waitFor(30, TimeUnit.SECONDS);
            Date end = new Date();

            TaskExecution execution = new TaskExecution();
            execution.setStartTime(start);
            execution.setEndTime(end);
            execution.setOutput(output);

            if (task.getTaskExecutions() == null) {
                task.setTaskExecutions(new ArrayList<>());
            }
            task.getTaskExecutions().add(execution);

            taskRepo.save(task);
            return ResponseEntity.ok(execution);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error executing task: " + e.getMessage());
        }
    }
}
