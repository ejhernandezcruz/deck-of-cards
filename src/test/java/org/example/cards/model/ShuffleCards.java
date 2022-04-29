package org.example.cards.model;


import lombok.Data;

@Data
public class ShuffleCards {

    private String success;
    private String deck_id;
    private boolean shuffled;
    private Integer remaining;
}
