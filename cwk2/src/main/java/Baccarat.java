public class Baccarat {
  private Shoe shoe = new Shoe(6);
  private BaccaratHand player = new BaccaratHand();
  private BaccaratHand banker = new BaccaratHand();

  public Baccarat() {
    shoe.shuffle();
    player.add((BaccaratCard) shoe.deal());
    banker.add((BaccaratCard) shoe.deal());
    player.add((BaccaratCard) shoe.deal());
    banker.add((BaccaratCard) shoe.deal());
  }
  
  public static void main(String[] args) {
    Baccarat game = new Baccarat();
    game.printHands();
  }

  public void printHands() {
    System.out.println("Player: " + player.toString() + " = " + player.value());
    System.out.println("Banker: " + banker.toString() + " = " + banker.value());
  }
}
