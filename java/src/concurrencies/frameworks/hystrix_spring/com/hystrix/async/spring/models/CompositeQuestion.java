package com.hystrix.async.spring.models;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


@Data
@AllArgsConstructor
public class CompositeQuestion {
 
	private String id;
 
	private String description;
 
	private Category category;
 
	private List<Option> options;

	public CompositeQuestion(String id, String description,
			Category category, List<Option> options) {
		setId(id);
		setDescription(description);
		setCategory(category);
		setOptions(options);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
}