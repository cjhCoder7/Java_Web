package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Borrow {
    private int sid;
    private int bid;
    private String borrowDate;
    private String returnDate;
}
