package com.example.demo.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	private String house;
	private int pin;

	public String getHouse() {
		return house;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
}