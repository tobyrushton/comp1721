import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author Toby Rushton
 */
public class TrackInfo {
  public static void main(String[] args) {
    if(args.length != 1){
      System.out.println("Usage: java TrackInfo <filename>");
      System.exit(0);
    }

    try{
      Track track = new Track(args[0]);

      DecimalFormat f3dp = new DecimalFormat("0.000");

      System.out.println(track.size() + " points in track");
      System.out.println("Lowest point is " + track.lowestPoint());
      System.out.println("Highest point is " + track.highestPoint());
      System.out.println("Total distance is " + f3dp.format(track.totalDistance()/1000) + " km");
      System.out.println("Average speed is " + f3dp.format(track.averageSpeed()) + " m/s");
    }catch(IOException e){
      System.out.println("Error reading file: " + e.getMessage());
      System.exit(1);
    }
  }
}
