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

    public static class Builder {
        private String summary;
        private final List<Experience> experiences = new ArrayList<>();
        private final List<Education> educations = new ArrayList<>();

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder addExperience(Experience ex) {
            this.experiences.add(ex);
            return this;
        }

        public Builder addEducation(Education ed) {
            this.educations.add(ed);
            return this;
        }

        public Profile build() {
            return new Profile(summary, experiences, educations);
        }
    }
}
