public class BaccaratHand{
    // max amount of cards in hand is 3
    private BaccaratCard[] hand = new BaccaratCard[3];
    private int cardCount = 0; //keeps track of current end of array

    public int size() {
        return cardCount;
    }

    public void add(BaccaratCard card) {
        hand[cardCount++] = card;
    }

    public int value(){
        int total = 0;

        for(int i = 0; i < cardCount; i++) {
            total += hand[i].value();
        }
        
        return total % 10;
    }

    public boolean isNatural(){
        int val = value();

        return cardCount == 2 && (val == 8 || val == 9);
    }

    public String toString(){
        String out = "";

        for(int i = 0; i < cardCount; i++) {
            out += hand[i].toString() + " ";
        }

        return out.trim();
    }
}