package org.example.cards.tests;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.example.cards.api.DeckCards;
import org.example.cards.model.Card;
import org.example.cards.model.DrawCards;
import org.example.cards.model.ShuffleCards;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;


@Slf4j
public class CardsTest {

    public static final int INITIAL_NUMBER_OF_CARDS = 52;
    public static final int NUMBER_OF_CARDS = 3;
    public static final String DECK_KEY = "deck_key";
    public static final String PLAYER_1 = "player1";
    public static final String PLAYER_2 = "player2";

    private final DeckCards deckCards = new DeckCards();

    @Test(testName = "Create a Deck", priority = 1)
    public void createDeskTest(ITestContext context) {
        ShuffleCards shuffleCards = deckCards.createDeck();
        context.setAttribute(DECK_KEY, shuffleCards.getDeckId());
        log.info(shuffleCards.toString());
        Truth.assertThat(shuffleCards.isSuccess()).isTrue();
        Truth.assertThat(shuffleCards.isShuffled()).isTrue();
        Truth.assertThat(shuffleCards.getRemaining()).isEqualTo(INITIAL_NUMBER_OF_CARDS);
    }

    @Test(testName = "Create 2 piles with 3 cards", priority = 2)
    public void createPilesTest(ITestContext context) {
        String deckId = (String) context.getAttribute(DECK_KEY);
        List<Card> player1Cards = deckCards.drawnCards(deckId, NUMBER_OF_CARDS).getCards();
        List<Card> player2Cards = deckCards.drawnCards(deckId, NUMBER_OF_CARDS).getCards();
        deckCards.createPile(PLAYER_1, deckId, player1Cards);
        deckCards.createPile(PLAYER_2, deckId, player2Cards);
        List<Card> player1PileCards = deckCards.getCardsFromPile(PLAYER_1, deckId);
        List<Card> player2PileCards = deckCards.getCardsFromPile(PLAYER_2, deckId);
        deckCards.printCards(PLAYER_1, player1PileCards);
        deckCards.printCards(PLAYER_2, player2PileCards);
        Truth.assertThat(player1PileCards.size()).isEqualTo(NUMBER_OF_CARDS);
        Truth.assertThat(player2PileCards.size()).isEqualTo(NUMBER_OF_CARDS);
    }

    @Test(testName = "Not enough cards (negative case",priority = 3)
    public void notEnoughCardsTest(ITestContext context){
        String deckId = (String) context.getAttribute(DECK_KEY);
        DrawCards drawCards = deckCards.drawnCards(deckId, INITIAL_NUMBER_OF_CARDS);
        Truth.assertThat(drawCards.isSuccess()).isFalse();
        Truth.assertThat(drawCards.getDeckId()).isEqualTo(deckId);
        Truth.assertThat(drawCards.getRemaining()).isEqualTo(0);
        Truth.assertThat(drawCards.getError()).contains("Not enough");
    }



}
