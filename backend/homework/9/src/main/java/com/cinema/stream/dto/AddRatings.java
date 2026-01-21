package com.cinema.stream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRatings {

    private String comment;
    private int rating;

    public AddRatings(){}

}
