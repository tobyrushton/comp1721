public class BaccaratCard extends Card{
    public BaccaratCard(Card.Rank r, Card.Suit s) {
        super(r, s);
    }

    public int value() {
        switch(getRank()) {
            case JACK:
            case QUEEN:
            case KING:
            case TEN:
                return 0;
            case ACE:
                return 1;
            default:
                return getRank().getSymbol() - '0';
        }
    }
}