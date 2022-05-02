package org.example.cards.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.example.cards.constants.API;
import org.example.cards.model.Card;
import org.example.cards.model.DrawCards;
import org.example.cards.model.ShuffleCards;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@Slf4j
public class DeckCards {

    private static final String API_BASE_URL = "http://deckofcardsapi.com/api";
    private final RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri(API_BASE_URL).build();

    private Response requestDeckOfCards(String resource) {
        return given().spec(requestSpec)
                .when().get(resource)
                .then().assertThat().statusCode(API.HTTP_OK)
                .extract().response();
    }

    public ShuffleCards createDeck() {
        String resource = "/deck/new/shuffle/?deck_count=1";
        Response response = requestDeckOfCards(resource);
        return response.jsonPath().getObject("$", ShuffleCards.class);
    }

    public DrawCards drawnCards(String deck, int numberOfCards) {
        String resource = String.format("/deck/%s/draw/?count=%s", deck, numberOfCards);
        Response response = requestDeckOfCards(resource);
        return response.jsonPath().getObject("$", DrawCards.class);
    }

    public void createPile(String pileName, String deck, List<Card> cards) {
        String pileCards = cards.stream()
                .map(Card::getCode)
                .collect(Collectors.joining(","));
        String resourcePile = String.format("/deck/%s/pile/%s/add/?cards=%s", deck, pileName, pileCards);
        requestDeckOfCards(resourcePile);
    }

    public List<Card> getCardsFromPile(String pileName, String deck) {
        String resource = String.format("/deck/%s/pile/%s/list/", deck, pileName);
        Response response = requestDeckOfCards(resource);
        String jsonPath = String.format("piles.%s.cards", pileName);
        return response.jsonPath().getList(jsonPath, Card.class);
    }

    public void printCards(String pileName, List<Card> cards) {
        cards.stream()
                .map(card -> String.format("%s: %s - %s", pileName, card.getValue(), card.getSuit()))
                .forEach(System.out::println);
    }

}
