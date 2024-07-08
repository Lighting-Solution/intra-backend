package com.ls.in.document.util;

import lombok.Getter;

@Getter
public enum Category {
	PUBLIC("public"),
	SERVICE("service"),
	MANAGE("manage"),
	SOLUTION("solution"),
	APPROVAL("approval");

	private final String categoryName;

	Category(String categoryName) {
		this.categoryName = categoryName;
	}

	public static Category fromCategoryName(String categoryName) {
		for (Category category : Category.values()) {
			if(category.getCategoryName().equalsIgnoreCase(categoryName))
				return category;
		}
		throw new IllegalArgumentException("No enum constant with categoryName " + categoryName);
	}
}
