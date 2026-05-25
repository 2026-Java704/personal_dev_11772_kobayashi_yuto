package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {
	private final Account account;
	private final TaskRepository taskRepository;
	private final CategoryRepository categoryRepository;

	public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository,
			Account account) {
		this.taskRepository = taskRepository;
		this.categoryRepository = categoryRepository;
		this.account = account;
	}

	@GetMapping("/tasks")
	public String index(
			@RequestParam(defaultValue = "") Integer userId,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String title,
			Model model) {

		List<Task> tasks = taskRepository.findByUserId(account.getId());

		//タスクの完了率
		double completed = (double) taskRepository.findByUserIdAndProgress(account.getId(), 2).size();
		double total = (double) tasks.size();

		System.out.println("完了 : 全体" + completed + ":" + total);

		int progressPercentage = (int) Math.floor((completed / total) * 100);

		//
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		//
		List<Task> taskList = null;
		if (categoryId == null && keyword.length() <= 0) {
			taskList = taskRepository.findByUserId(account.getId());
			System.out.println("ユーザーID検索：" + account.getId() + taskList.size());
		} else if (categoryId != null) {
			taskList = taskRepository.findByUserIdAndCategoryId(account.getId(), categoryId);
			System.out.println("ユーザーID検索" + account.getId() + "& カテゴリー：" + taskList.size());
		} else if (categoryId == null && keyword.length() > 0) {
			taskList = taskRepository.findByUserIdAndTitleContaining(account.getId(), keyword);
			System.out.println("ユーザーID検索" + account.getId() + "& キーワード：" + taskList.size());
		}

		long taskCount = taskList.size();
		model.addAttribute("progress", progressPercentage);
		model.addAttribute("taskCount", taskCount);
		model.addAttribute("tasks", taskList);
		model.addAttribute("keyword", keyword);
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
			@RequestParam(defaultValue = "") String memo, Model model) {

		List<String> errorList = new ArrayList<>();
		if (categoryId == null) {
			errorList.add("カテゴリーは必須です");
		}

		if (title.length() == 0) {
			errorList.add("タイトルは必須です");
		}

		if (closing_date == null) {
			errorList.add("期限は必須です");
		}

		if (progress == null) {
			errorList.add("進捗状況は必須です");
		}

		if (memo.length() == 0) {
			errorList.add("メモは必須です");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("title", title);
			model.addAttribute("closing_date", closing_date);
			model.addAttribute("progress", progress);
			model.addAttribute("memo", memo);
			return "addTask";
		}

		Category category = categoryRepository.findById(categoryId).get();

		Task task = new Task(account.getId(), category, title, closing_date, progress, memo);
		taskRepository.save(task);
		task.setUserId(account.getId());

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
