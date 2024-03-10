import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Toby Rushton
 */
public class Track {
  private Point points[];
  private int end = 0;

  public Track(){
    points = new Point[1000];
  }

  public Track(String filename) throws IOException {
    points = new Point[1000];
    readFile(filename);
  }

  public void readFile(String filename) throws IOException {
    File file = new File(filename);
    if(!file.exists()){
      throw new FileNotFoundException(filename + " not found");
    }

    end = 0;

    Scanner scanner = new Scanner(file);

    if(scanner.hasNextLine())
      scanner.nextLine(); // Skip header

    while(scanner.hasNextLine()){
      String line = scanner.nextLine();
      String[] parts = line.split(",");

      if(parts.length != 4){
        scanner.close();
        throw new GPSException("Invalid file format");
      }

      ZonedDateTime time = ZonedDateTime.parse(parts[0]);
      double lon = Double.parseDouble(parts[1]);
      double lat = Double.parseDouble(parts[2]);
      double elev = Double.parseDouble(parts[3]);

      Point p = new Point(time, lon, lat, elev);
      add(p);
    }

    scanner.close();
  }

  public void add(Point p){
    points[end] = p;
    end++;
  }

  public Point get(int i){
    if(i < 0 || i >= end){
      throw new GPSException("Index out of range: " + i);
    }

    return points[i];
  }

  public int size(){
    return end;
  }

  public Point lowestPoint(){
    if(end == 0){
      throw new GPSException("No points in track");
    }

    Point lowest = points[0];

    for(int i = 1; i < end; i++){
      if(points[i].getElevation() < lowest.getElevation()){
        lowest = points[i];
      }
    }

    return lowest;
  }

  public Point highestPoint(){
    if(end == 0){
      throw new GPSException("No points in track");
    }

    Point highest = points[0];

    for(int i = 1; i < end; i++){
      if(points[i].getElevation() > highest.getElevation()){
        highest = points[i];
      }
    }

    return highest;
  }

  public double totalDistance(){
    if(end < 2)
      throw new GPSException("Not enough points in track");
    
    double distance = 0;

    for(int i = 0; i < end; i++){
      if(i < end - 1){
        distance += Point.greatCircleDistance(points[i], points[i + 1]);
      }
    }

    return distance;
  }

  public double averageSpeed(){
    if(end < 2)
      throw new GPSException("Not enough points in track");
    
    double distance = totalDistance();

    double time = ChronoUnit.SECONDS.between(points[0].getTime(), points[end - 1].getTime());
    
    return distance / time;
  }
}
