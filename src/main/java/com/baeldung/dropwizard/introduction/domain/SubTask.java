package com.baeldung.dropwizard.introduction.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "subtask")
@NamedQuery(
        name = "SubTask.findAll",
        query = "SELECT subtasks FROM SubTask subtasks"
)
@JsonPropertyOrder({ "id", "title", "description", "version" })
public class SubTask {

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @NotNull
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Version
    private Date version;

    public SubTask() {
        // Default constructor
    }

    public ToDo getToDo() {
        return toDo;
    }

    public void setToDo(ToDo toDo) {
        this.toDo = toDo;
    }

    // Getters and Setters

    @ManyToOne
    @JoinColumn(name = "todo_id")
    @JsonBackReference
    private ToDo toDo;

    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        SubTask subTask = (SubTask) o;
        return getId().equals(subTask.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}