package ro.ubb.domain;

import java.util.Objects;

public class Assignment implements HasID<String> {
    private String id;
    private String description;
    private int deadlineWeek;
    private int startWeek;

    public Assignment(String id, String description, int deadlineWeek, int startWeek) {
        this.id = id;
        this.description = description;
        this.deadlineWeek = deadlineWeek;
        this.startWeek = startWeek;
    }

    @Override
    public String getID() { return id; }

    @Override
    public void setID(String idTema) { this.id = idTema; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getDeadlineWeek() { return deadlineWeek; }

    public void setDeadlineWeek(int deadlineWeek) { this.deadlineWeek = deadlineWeek; }

    public int getStartWeek() { return startWeek; }

    public void setStartWeek(int startWeek) { this.startWeek = startWeek; }

    @Override
    public String toString() {
        return "Tema{" + "id='" + id + "', descriere='" + description + ", deadline=" + deadlineWeek +
                ", startline=" + startWeek + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment assignment = (Assignment) o;
        return Objects.equals(id, assignment.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
