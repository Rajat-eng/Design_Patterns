package com.linkedin.Models;
import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String summary;
    private final List<Experience> experiences = new ArrayList<>();
    private final List<Education> educations = new ArrayList<>();

    public Profile(String summary, List<Experience> experiences, List<Education> educations) {
        this.summary = summary;
        this.experiences.addAll(experiences);
        this.educations.addAll(educations);
    }


    public void display() {
        System.out.println("  Summary: " + (summary != null ? summary : "N/A"));

        System.out.println("  Experience:");
        if (experiences.isEmpty())
            System.out.println("    - None");
        else
            experiences.forEach(exp -> System.out.println("    - " + exp));

        System.out.println("  Education:");
        if (educations.isEmpty())
            System.out.println("    - None");
        else
            educations.forEach(edu -> System.out.println("    - " + edu));
    }

    public class Builder{
        private String summary;
        private List<Experience> experience;
        private List<Education> education;

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder addExperience(Experience ex) {
            this.experience.add(experience);
            return this;
        }

        public Builder addEducation(Education ed) {
            this.education.add(education);
            return this;
        }

        public Profile build() {
            return new Profile(summary, experience, education);
        }
    }
}
