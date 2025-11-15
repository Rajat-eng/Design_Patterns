package models;

public class Movie {
    private String name;
    private String genre;
    private int duration; // in minutes
    private String language;
    private String releaseDate;

    public Movie(String name, String genre, int duration, String language, String releaseDate) {
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public String getLanguage() {
        return language;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    
}
