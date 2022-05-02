package org.example.cards.model;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class DrawnCards {

    private boolean success;
    @SerializedName(value = "deck_id")
    private String deckId;
    private List<Card> cards;
    private int remaining;
    private String error;

}
