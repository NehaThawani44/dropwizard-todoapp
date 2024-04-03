package com.leanix.dropwizard.introduction.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.leanix.dropwizard.introduction.configuration.serialization.FlexibleLocalDateDeserializer;
import com.leanix.dropwizard.introduction.configuration.serialization.GermanLocalDateSerializer;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "todo")
@NamedQuery(
        name = "ToDo.findAll",
        query = "SELECT t FROM ToDo t"
)
@JsonPropertyOrder({ "id", "title", "description", "status", "dueDate","subtasks" })
public class ToDo   {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id", nullable = false)
    @NotNull
    @JsonProperty("id")
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    @NotNull
    @JsonProperty("title")
    private String title;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;


    @Column(name = "due_date")
    @JsonProperty("dueDate")
    @JsonDeserialize(using = FlexibleLocalDateDeserializer.class)
    @JsonSerialize(using = GermanLocalDateSerializer.class)
    private LocalDate dueDate;


    public ToDoType getType() {
        return type;
    }

    public void setType(ToDoType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    private ToDoType type;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty("status")
    private ToDoStatus toDoStatus;


    @Version
    private Date version;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
*/
    public Set<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Set<SubTask> subtasks) {
        this.subtasks = subtasks;
    }


    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private Set<SubTask> subtasks = new HashSet<SubTask>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }




    public ToDoStatus getToDoStatus() {
        return toDoStatus;
    }

    public void setToDoStatus(ToDoStatus toDoStatus) {
        this.toDoStatus = toDoStatus;
    }


    public ToDo(String task, String description) {
    }
    public ToDo() {

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
        if (!(o instanceof ToDo)) return false;
        ToDo toDo = (ToDo) o;
        return getId().equals(toDo.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    public void addSubTask(SubTask subTask) {
        subtasks.add(subTask);
        subTask.setToDo(this);
    }

    public void removeSubTask(SubTask subTask) {
        subtasks.remove(subTask);
        subTask.setToDo(null);
    }


}