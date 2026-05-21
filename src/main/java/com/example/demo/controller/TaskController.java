package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {
	private final TaskRepository taskRepository;
	private final CategoryRepository categoryRepository;

	public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository) {
		this.taskRepository = taskRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/tasks")
	public String index(
			@RequestParam(defaultValue = "") Integer categoryId,
			Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		List<Task> taskList = null;
		if (categoryId == null) {
			taskList = taskRepository.findAll();
		} else {
			taskList = taskRepository.findByCategoryId(categoryId);
		}

		model.addAttribute("tasks", taskList);

		return "task";
	}

	@GetMapping("/tasks/new")
	public String index(Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		return "addtask";
	}

	@PostMapping("/tasks/add")
	public String add(
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") LocalDate closing_date,
			@RequestParam(defaultValue = "") Integer progress,
			@RequestParam(defaultValue = "") String memo) {

		Category category = categoryRepository.findById(categoryId).get();

		Task task = new Task(category, title, closing_date, progress, memo);

		taskRepository.save(task);

		return "redirect:/tasks";
	}

	//更新画面表示
	@GetMapping("/tasks/{categoryId}/edit")
	public String edit(@PathVariable Integer categoryId, Model model) {

		Task task = taskRepository.findById(categoryId).get();

		model.addAttribute("task", task);

		return "editTask";
	}

	//更新処理
	@PostMapping("/tasks/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "") LocalDate closing_date,
			@RequestParam(defaultValue = "") Integer progress,
			@RequestParam(defaultValue = "") String memo) {

		Task task = taskRepository.findById(id).get();
		Category category = categoryRepository.findById(categoryId).get();
		task.setCategory(category);
		task.setTitle(title);
		task.setClosing_date(closing_date);
		task.setProgress(progress);
		task.setMemo(memo);

		taskRepository.save(task);

		return "redirect:/tasks";
	}

	//削除処理
	@PostMapping("/tasks/{id}/delete")
	public String delete(@PathVariable Integer id) {

		taskRepository.deleteById(id);

		return "redirect:/tasks";
	}
}
