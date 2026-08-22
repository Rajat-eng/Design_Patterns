package com.linkedin.Models;

public class Education {
	private final String institute;
	private final String degree;
	private final int graduationYear;

	public Education(String institute, String degree, int graduationYear) {
		this.institute = institute;
		this.degree = degree;
		this.graduationYear = graduationYear;
	}

	@Override
	public String toString() {
		return degree + " at " + institute + " (" + graduationYear + ")";
	}
}
