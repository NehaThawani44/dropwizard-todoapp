package com.leanix.dropwizard.introduction.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NamedQuery(
        name = "User.findAll",
        query = "SELECT users FROM User users"
)
@JsonPropertyOrder({ "id", "username"})
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @JsonProperty("username")
    private String username;

    @Column(name = "password")
    private String password;


    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //private Set<ToDo> todos = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public Set<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(Set<ToDo> todos) {
        this.todos = todos;
    }*/


   /* public void addToDo(ToDo toDo) {
        todos.add(toDo);
        //toDo.setUser(this);
    }


    public void removeToDo(ToDo toDo) {
        todos.remove(toDo);
        //toDo.setUser(null);
    }*/
}
