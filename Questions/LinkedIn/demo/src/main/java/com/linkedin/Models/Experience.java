package com.linkedin.Models;

public class Experience {
	private final String company;
	private final String role;
	private final int years;

	public Experience(String company, String role, int years) {
		this.company = company;
		this.role = role;
		this.years = years;
	}

	@Override
	public String toString() {
		return role + " at " + company + " (" + years + " years)";
	}
}
