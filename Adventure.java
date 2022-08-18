import com.google.common.graph.*; 
import java.io.*;
import java.util.*;


/**
*. Class to read file and and run the game 
*. 
*. @version Spring 2022
*. @author Cyrine Ben Ayed, Sonora Halili, Frankie Fan 
*/
public class Adventure {

  /**
  *. Reads a file and converts it into a graph.
  *. @param filename the name of the file to be read
  *. @return restulting graph
  */
  public static MutableValueGraph<Place, String> readFromText(String filename){

    //build the graph 
    MutableValueGraph<Place, String> g = ValueGraphBuilder.directed().build();
    
    try {
      //holds the scanner
      Scanner input = new Scanner (new File(filename));
    
      //holds the all the nodes
      ArrayList<ArrayList<Place> > nodes = new ArrayList<>();

      // holds all edges
      ArrayList<String > edges = new ArrayList<>();
    
      while (input.hasNext()){
        // get the line
        String line = input.nextLine();

        //holds one pair of nodes 
        ArrayList<Place>  pairNodes = new ArrayList<>();

        // the first place
        String place1_str = line.substring(0, line.indexOf('/'));
        Place place1 = new Place(place1_str.substring(0, place1_str.indexOf(';')), place1_str.substring(place1_str.indexOf(';')+2));   


        // the second place
        String place2_str = line.substring(line.indexOf('/')+ 2, line.indexOf('#'));
        Place place2 = new Place(place2_str.substring(0, place2_str.indexOf(';')), place2_str.substring(place2_str.indexOf(';')+2));  

        //make sure items are not there and add them 
        // Place1
        if (place1.isThere(nodes).equals("")){
          pairNodes.add(place1);
        //if it's there add the already exisitng object 
        } else {
          String pairIndex = place1.isThere(nodes).substring(0,place1.isThere(nodes).indexOf(','));
          String placeIndex = place1.isThere(nodes).substring(place1.isThere(nodes).indexOf(',')+1);
          pairNodes.add(nodes.get(Integer.valueOf(pairIndex)).get(Integer.valueOf(placeIndex)));
        }

        //Place2 
       if (place2.isThere(nodes).equals("")){
          pairNodes.add(place2);
        //if it's there add the already exisitng object 
        } else {
          String pairIndex = place2.isThere(nodes).substring(0,place2.isThere(nodes).indexOf(','));
          String placeIndex = place2.isThere(nodes).substring(place2.isThere(nodes).indexOf(',')+1);
          pairNodes.add(nodes.get(Integer.valueOf(pairIndex)).get(Integer.valueOf(placeIndex)));
        }

        //add inner arraylist to outer arraylist 
        nodes.add(pairNodes);
        
        //add edge to edges 
   edges.add(line.substring(line.indexOf('#')+2));  
      }
      
      //close the scanner 
      input.close();
    

      // make the graph
      for (int i=0; i<edges.size() ; i++){
        g.putEdgeValue(nodes.get(i).get(0), nodes.get(i).get(1), edges.get(i));
      }
      
    } catch (Exception e){
      System.out.println("An error occured; Please retry.");
      System.exit(-1);
    }
    
    //return the graph
    return g;
  }
   
  /**  Method to offer future moves to player
  *   @param current location, graph
  *   @return ArrayList of options
  */
  public static ArrayList<String> offerMoves(Place current,  MutableValueGraph<Place, String> g, ArrayList<String> traversed) {
    // holds the possible locations
    Set <Place> newLocations = g.successors(current);

    // holds the possible moves
    ArrayList<String> options = new ArrayList<>();
    for (Place newLoc: newLocations){
      if (!traversed.contains(g.edgeValue(current, newLoc).get())){
        options.add(g.edgeValue(current, newLoc).get());
      }
      
    }
    
    return options;
  }

  /**  Method to return the destination node based on edge 
  *    @param origin node, data on connecting edge, graph
  *    @return destination node
  */
  public static Place goTo(Place origin, String edge, MutableValueGraph<Place, String> g) { 
    //holds the new locations
    Set <Place> newLocations = g.successors(origin);

    // look for the destination node
    for (Place newLoc: newLocations) {
      if (g.hasEdgeConnecting(origin, newLoc)  && 
         g.edgeValue(origin, newLoc).get().equals(edge)) {
        return newLoc;
      }
    }

    return null;
  }

  /** Method to check if we've reached the final point
  *  @param current location, graph
  *  @return boolean
  */
  public static boolean isFinish (Place current, MutableValueGraph<Place, String> g) {
    return (g.outDegree(current) == 0);
  }

  /** Method to parse the input
   *. @param the user's response 
   *. @param Arraylist of the available choices
   *. @return the corresponding edge
  */ 
  public static String parseInput (String response, ArrayList <String> possibilities ){
    try {
      // when the user doesn't input anything
      if (response.length() == 0 ){
        return "";

      //process a help command 
      } else if (response.toUpperCase().equals("HELP")){
        // for clarity
        System.out.println();
        System.out.println("**** help **** \n read the input and type in your choice.");
        System.out.println("Try to make the choice match the given options \n or type in a verb and a noun as in --Sit Down--");
        System.out.println();
        return "help";

      //process an exit command 
      } else if (response.toUpperCase().equals("EXIT") || response.toUpperCase().equals("QUIT")){
        System.exit(0);
        return "";

      //process a manual command
      } else if (response.toUpperCase().equals("MANUAL")){
        // for clarity
        System.out.println();
        System.out.println("**** manual **** \n Type help for game directions \n Type exit or quit to exit the game");
        System.out.println();
        return "manual";

      // when it's not a key word
      } else {
        // loop through the possible destinations
        for (int i=0; i<possibilities.size(); i++){
          //holds the verb
          String verb = possibilities.get(i).substring(0, possibilities.get(i).indexOf(' '));
         
          //holds the rest of the sentence
          String rest = possibilities.get(i).substring(possibilities.get(i).indexOf(' ')+1);
          
  
          // if we found a close match
          if (verb.toUpperCase().equals(response.substring(0, response.indexOf(' ')).toUpperCase()) 
              && (rest.toUpperCase().contains(response.substring( response.indexOf(' ')+1).toUpperCase()))){
            return possibilities.get(i);
          }
        }
        return "";
      } 
    } catch (StringIndexOutOfBoundsException e) {
      return "";
    }
  }
  
  /** Main method to run game */
  public static void main (String[] args){
    // make the graph
    MutableValueGraph<Place, String> g = readFromText ("graph.txt");

    // holds the current node
    Place current = g.nodes().toArray(new Place[0])[0];

    //holds the scanner
    Scanner input = new Scanner(System.in);

    //holds the traversed paths
    ArrayList<String> traversedRoutes = new ArrayList<String>();


    // start the game
    while (true){
      //stop if we reached the end of the game
      if (isFinish(current, g)) {
        System.out.println(current.getDesc());
        System.out.println("Game over");
        break;

      // keep going if the game is not over yet
      } else {
        // print the description of the current place
        System.out.println(current.getDesc());

        //print available moves
        ArrayList <String> possibilities = offerMoves(current, g, traversedRoutes);
        System.out.println("---------------------------------------------------");
        System.out.println("You can: ");
        for (int i=0; i<possibilities.size(); i++){
          if (!traversedRoutes.contains(possibilities.get(i))){
            System.out.println(">> " + possibilities.get(i));
          }
        }
        String userMove = input.nextLine();

        // get the user's choice
        boolean acceptableMove = false;
        
        //make sure it's a right choice
        while (!acceptableMove){
          //accept an exact match input
          if (possibilities.contains(userMove)) {
            current = goTo(current,userMove, g);
            acceptableMove = true;

            // if they clicked on Play again remove all paths 
            if (userMove.equals("Play again")){
              traversedRoutes.clear();
            } else {
              // add the edge to traversed routes
              traversedRoutes.add(userMove);

            }
            
          //try to find a close match
          } else {
            //holds the edge that is a close match to their input
            String closestEdge = parseInput(userMove, possibilities);

            // if no close match was found
            if (closestEdge.equals("")) {
              System.out.println("It doesn't seem like you could do that, please try again. Make sure your response has two words: a verb and a noun (or matches the given options)");
              userMove = input.nextLine();

            // process the help or manual command
            } else if (closestEdge.equals("help") || closestEdge.equals("manual") ){
              userMove = input.nextLine();
            
            // confirm the close match with the user
            } else {
              System.out.println("Did you mean "+ closestEdge + "?");
              String yesOrNo = input.nextLine();
              if (yesOrNo.toUpperCase().equals("YES")){
                acceptableMove = true;
                current = goTo(current,closestEdge, g);

                // if they clicked on Play again remove all paths 
                if (closestEdge.equals("Play again")){
                  traversedRoutes.clear();
                } else {
                  // add the edge to traversed routes
                  traversedRoutes.add(closestEdge);
                }
                
              //if not ask them to try again
              } else {
                System.out.println("I am not sure I understand, please try again.");
                userMove = input.nextLine();
              }
            }
          }
        }
      }
    }
  }
}