package org.example.cards.tests;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.example.cards.api.DeckCards;
import org.example.cards.model.Card;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;


@Slf4j
public class CardsTest {

    public static final String DECK_KEY = "deck_key";
    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";
    public static final int NUMBER_OF_CARDS = 3;
    private DeckCards deckCards = new DeckCards();

    @Test(testName = "Create a Deck", priority = 1)
    public void createDeskTest(ITestContext context) {
        String deck = deckCards.createDeck();
        context.setAttribute(DECK_KEY, deck);
    }

    @Test(testName = "Create 2 piles with 3 cards", priority = 2)
    public void createPilesTest(ITestContext context) {
        String deckId = (String) context.getAttribute(DECK_KEY);
        List<Card> player1Cards = deckCards.drawnCards(deckId, NUMBER_OF_CARDS);
        List<Card> player2Cards = deckCards.drawnCards(deckId, NUMBER_OF_CARDS);
        deckCards.createPile(PLAYER_1, deckId, player1Cards);
        deckCards.createPile(PLAYER_2, deckId, player2Cards);
        List<Card> player1PileCards = deckCards.getCardsFromPile(PLAYER_1, deckId);
        List<Card> player2PileCards = deckCards.getCardsFromPile(PLAYER_2, deckId);
        printCards(PLAYER_1, player1PileCards);
        printCards(PLAYER_2, player2PileCards);
        Truth.assertThat(player1PileCards.size()).isEqualTo(NUMBER_OF_CARDS);
        Truth.assertThat(player2PileCards.size()).isEqualTo(NUMBER_OF_CARDS);
    }

    private void printCards(String player, List<Card> cards) {
        for (Card card : cards) {
            String pile = String.format("%s: %s - %s", player, card.getValue(), card.getSuit());
            System.out.println(pile);
        }
    }

}
