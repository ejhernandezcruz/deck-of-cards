package org.example.cards.model;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ShuffleCards {

    private boolean success;
    @SerializedName(value = "deck_id")
    private String deckId;
    private boolean shuffled;
    private Integer remaining;
}
