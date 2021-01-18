package com.example.myhome.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=30)
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
