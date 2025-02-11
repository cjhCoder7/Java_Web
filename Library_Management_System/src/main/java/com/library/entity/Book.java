package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private int bid;
    private String title;
    private String author;
    private String press;
    private String description;
}
