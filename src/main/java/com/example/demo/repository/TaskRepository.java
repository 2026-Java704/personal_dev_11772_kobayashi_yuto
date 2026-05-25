package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByCategoryId(Integer categoryId);

	List<Task> findByUserId(Integer userId);

	List<Task> findByUserIdAndCategoryId(Integer userId, Integer categoryId);

	List<Task> findByUserIdAndTitleContaining(Integer userId, String keyword);

	//完了済みのタスク取得
	@Query(value = "SELECT * FROM tasks WHERE "
			+ "(progress = 2) "
			+ "AND (user_id = :userId)", nativeQuery = true)
	List<Task> findByCompleted(@Param("userId") Integer userId);

	List<Task> findByUserIdAndProgress(Integer userId, Integer progress);
}
