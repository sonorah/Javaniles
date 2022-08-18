import com.google.common.graph.*;
import java.util.ArrayList;

/**
 *  Class to represent a location node in a graph.
 *
 *  @author  Sonora Halili, Frankie Fan, Cyrine Ben Ayed
 *  @version CSC 212, April 2022
 */
public class Place {
  /** Field to store name of currentlocation*/
  public String locName;

  /** Field to store description of the currentloaction*/
  public String locDesc; 

  /**  constructor */
  public Place(String name, String desc) {
    locName = name;
    locDesc = desc;
  }
  
  /** @return description of place*/
  public String getDesc() {
    return this.locDesc;
  }
  
  /** Setter for desc*/
  public void setDesc(String desc) {
    this.locDesc = desc;
  }

  /** @return name of place*/
  public String getName() {
    return this.locName;
  }
  
  /** Setter for name*/
  public void setName(String name) {
    this.locName = name;
  }

  /** Represents Place object as string */
  public String toString() {
    return locName;
  }

  /** @return a boolean that shows whether or not two places are equal*/
  public boolean equals(Place p2){
    return (this.locName.equals(p2.getName()) && this.locDesc.equals(p2.getDesc()));
  }
  
  /** 
  *  Looks through a given arraylist of arraylists to determine if a place is already there.
  *  @param Arraylist of storedNodes
  *  @return index of the place; empty string if not found/ empty input
  */
  public String isThere (ArrayList <ArrayList<Place> > storedNodes){
    // for an empty arrayList
    if (storedNodes.size()==0){
      return "";
    } else {
      for (ArrayList<Place> onePair : storedNodes) {
        if (onePair.get(0).equals(this)) {
          return Integer.toString(storedNodes.indexOf(onePair))  + ",0";
        } if (onePair.get(1).equals(this)) {
          return Integer.toString(storedNodes.indexOf(onePair))  + ",1";
        }
    
      }
      // for non-detected place
      return "";
    }
  }
  
}
