package com.foodtogo.spring.security.login.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import io.swagger.annotations.ApiModel;


@Entity
@Table(name = "foods")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ApiModel(description="All details about the Foods. ")
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "food_category")
	private String food_category;
	
	
	@Column(name = "food_id")
	private String food_id;
	
	@Column(name = "food_name")
	private String food_name;
	
	@Column(name = "food_price")
	private double food_price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFood_category() {
		return food_category;
	}

	public void setFood_category(String food_category) {
		this.food_category = food_category;
	}

	public String getFood_id() {
		return food_id;
	}

	public void setFood_id(String food_id) {
		this.food_id = food_id;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public double getFood_price() {
		return food_price;
	}

	public void setFood_price(double food_price) {
		this.food_price = food_price;
	}
	
	
}


