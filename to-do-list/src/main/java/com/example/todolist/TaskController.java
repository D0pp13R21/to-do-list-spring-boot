package com.example.todolist;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addText("tasks");
        return "index";
    }

    @PostMapping("/tasks")
    public String createTask(@RequestParam String title) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        taskRepository.save(task);
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/complete")
    public String completeTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setCompleted(true);
            taskRepository.save(task);
        }
        return "redirect:/";
    }
}
