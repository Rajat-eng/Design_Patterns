package com.linkedin.Models;

public class Member {
	private final String id;
	private final String name;
	private final Profile profile;

	public Member(String id, String name, Profile profile) {
		this.id = id;
		this.name = name;
		this.profile = profile;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Profile getProfile() {
		return profile;
	}
}
