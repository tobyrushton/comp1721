import java.util.Scanner;

public class Baccarat {
  private Shoe shoe;
  private BaccaratHand player;
  private BaccaratHand banker;
  private int round = 0;
  private int bankerWins = 0;
  private int playerWins = 0;
  private int ties = 0;

  public Baccarat() {
    shoe = new Shoe(6);
    shoe.shuffle();
  }
  
  public static void main(String[] args) {
    Baccarat game = new Baccarat();
    boolean running = true;
    Scanner scanner = new Scanner(System.in);
    boolean interacive = args.length > 0 && 
      (args[0].equals("-i") || args[0].equals("--interactive"));

    while(running) {
      game.dealNewRound();
      game.printRound();
      game.evaluateRound();

      if(game.shoe.size() < 6) {
        System.out.println("Game over...");
        running = false;
      }

      if(interacive) {
        System.out.print("Play another round? (y/n): ");
        String input = scanner.nextLine();
        if(!input.toLowerCase().equals("y")) {
          running = false;
        }
      }

      // seperate rounds visually
      System.out.println();
    }

    scanner.close();

    game.printResults();
  }

  public void printHands() {
    System.out.println("Player: " + player.toString() + " = " + player.value());
    System.out.println("Banker: " + banker.toString() + " = " + banker.value());
  }

  public int dealPlayer() {
    Card card = shoe.deal();
    player.add((BaccaratCard) card);

    return card.value();
  }

  public int dealBanker() {
    Card card = shoe.deal();
    banker.add((BaccaratCard) card);
    return card.value();
  }

  public void dealNewRound() {
    player = new BaccaratHand();
    banker = new BaccaratHand();
    round++;

    // set up round with 2 cards each
    for (int i = 0; i < 2; i++) {
      dealBanker();
      dealPlayer();
    }
  }

  public void printRound() {
    System.out.println("Round " + round);
    printHands();
  }

  public void printResults() {
    System.out.println(round + " rounds played");
    System.out.println(playerWins + " player wins");
    System.out.println(bankerWins + " banker wins");
    System.out.println(ties + " ties");
  }

  public void evaluateRound() {
    boolean playerNatural = player.isNatural();
    boolean bankerNatural = banker.isNatural();

    if (playerNatural && bankerNatural) {
      ties++;
      System.out.println("Tie");
    } else if(playerNatural) {
      playerWins++;
      System.out.println("Player win!");
    } else if(bankerNatural) {
      bankerWins++;
      System.out.println("Banker win!");
    } else {
      int playerValue = player.value();
      int bankerValue = banker.value();
      int playerThirdCard = -1;
      int bankerThirdCard = -1;

      
      if(playerValue < 6) {
        System.out.println("Dealing third card to player...");
        playerThirdCard = dealPlayer();
      } 
      if (
          playerThirdCard == -1 && bankerValue < 6 || 
          bankerValue < 2 ||
          bankerValue == 3 && playerThirdCard != 8 ||
          bankerValue == 4 && playerThirdCard > 1 && playerThirdCard < 8 ||
          bankerValue == 5 && playerThirdCard > 3 && playerThirdCard < 8 ||
          bankerValue == 6 && playerThirdCard == 6 || playerThirdCard == 7
        ) {
          System.out.println("Dealing third card to banker...");
          bankerThirdCard = dealBanker();
      }  

      playerValue = player.value(); 
      bankerValue = banker.value();


      if(bankerThirdCard != -1 || playerThirdCard != -1) {
        printHands();
      }
      if(playerValue > bankerValue) {
        playerWins++;
        System.out.println("Player win!");
      } else if(playerValue < bankerValue) {
        bankerWins++;
        System.out.println("Banker win!");
      } else {
        ties++;
        System.out.println("Tie");
      }
    }
  }
}
