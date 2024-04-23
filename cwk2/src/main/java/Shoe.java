import java.util.Random;

public class Shoe {
    private Card[] deck;
    private int top;
    private int front = 0;

    public Shoe(int decks) {
        if (decks != 6  && decks != 8) {
            throw new CardException("Invalid number of decks");
        }

        deck = new Card[decks * 52];

        for(int i = 0, j = 0; i < decks; i++) {
            for(Card.Suit s: Card.Suit.values()) {
                for(Card.Rank r: Card.Rank.values()) {
                    deck[j++] = new BaccaratCard(r, s);
                }
            }
        }

        top = deck.length;
    }

    public int size() {
        return top - front;
    }

    public void shuffle() {
        Random rand = new Random();

        for(int i = 0; i < deck.length; i++) {
            int randomIndexToSwap = rand.nextInt(deck.length);
            Card temp = deck[randomIndexToSwap];
            deck[randomIndexToSwap] = deck[i];
            deck[i] = temp;
        }
    }

    public Card deal() {
        if(front == top) {
            throw new CardException("Shoe is empty");
        }

        return deck[front++];
    }
}