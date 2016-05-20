/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLoader;

import de.vogella.algorithms.dijkstra.model.Edge;
import de.vogella.algorithms.dijkstra.model.Vertex;
import java.util.*;
import java.io.*;
import java.util.Map.Entry;

/**
 *
 * @author Nixholas
 */
public class DataLoader {

    /**
     * stations hashmap Stores ALL stations
     *
     * statDist hashmap Stores the Station Name as key and its description as a
     * station object
     *
     */
    public static ArrayList<Vertex> bigArray = new ArrayList<>();
    public static ArrayList<Station> interChange = new ArrayList<>();
    // statVertex stores the Station and its Vertex
    public static ArrayList<Vertex> statVertexCCL = new ArrayList<>();
    public static ArrayList<Vertex> statVertexNSL = new ArrayList<>();
    public static ArrayList<Vertex> statVertexEWL = new ArrayList<>();
    public static ArrayList<Vertex> statVertexNEL = new ArrayList<>();
    public static ArrayList<Vertex> statVertexDTL = new ArrayList<>();
    // Stores all station thru the XML data
    public static ArrayList<Station> statStore = new ArrayList<Station>();
    // Stores all station thru the text file
    public static Map<String, Station> stations = new LinkedHashMap<String, Station>();
    public static Map<String, Station> statDist = new LinkedHashMap<String, Station>();
    public static Map<String, String> CCL = new LinkedHashMap<String, String>();
    public static Map<String, String> NSL = new LinkedHashMap<String, String>();
    public static Map<String, String> EWL = new LinkedHashMap<String, String>();
    public static Map<String, String> NEL = new LinkedHashMap<String, String>();
    public static Map<String, String> DTL = new LinkedHashMap<String, String>();

    /**
     * CC1 Dhoby Ghaut NS24 Dhoby Ghaut NE6 Dhoby Ghaut How am I going to show
     * that CC1, NE6 and NS24 are one? Store data to know that both are the same
     * station
     *
     * Must have a data structure that associates the different station codes..
     * together.. so that they are mapped to the same station name... decide
     * which is the key?
     *
     * @param Fileadd
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void loadMRTtxt(String Fileadd, String XMLFile) throws FileNotFoundException, IOException {
        /**
         * Regex System
         *
         * Utilizes regex and scanner to load objects from text.
         */
        String content = new Scanner(new File("MRT.txt")).useDelimiter("\\Z").next();

        // Replaces all of (end)s with nothing.
        // Removes all blank lines in the String
        // Loops the string to split all train systems
        content = content.replaceAll("\\(end\\)|\\(start\\)|(?m)^[ \t]*\r?\n", "");

        /**
         * Dynamic HashMap Loader
         *
         * Loads each row with an array.
         */
        int counter = 2; //We need to load 2 first as its the first station
        String[] lines = content.split(System.getProperty("line.separator"));

        for (String s : lines) {
            try {
                //Trims Train Code, removing all space in between or beside it.
                lines[counter - 1] = lines[counter - 1].replaceAll("\\s", "");

                // Populates each and every HashMap
                if (lines[counter - 1].contains("CC")) {
                    CCL.put(lines[counter - 1], lines[counter]);
                    //statDist.put(lines[counter], new Station(lines[counter - 1], lines[counter]))
                } else if (lines[counter - 1].contains("EW")) {
                    EWL.put(lines[counter - 1], lines[counter]);
                } else if (lines[counter - 1].contains("NS")) {
                    NSL.put(lines[counter - 1], lines[counter]);
                } else if (lines[counter - 1].contains("NE")) {
                    NEL.put(lines[counter - 1], lines[counter]);
                } else if (lines[counter - 1].contains("DT")) {
                    DTL.put(lines[counter - 1], lines[counter]);
                }

                /**
                 * Loads all stations into the stations Hashmap for universal
                 * use.
                 */
                if (lines[counter - 1] != "") {
                    stations.put(lines[counter - 1], new Station(lines[counter], lines[counter - 1]));
                } else {
                    //Ignore any blank lines
                }
                counter += 2; //Increment to the counter for the next station
            } catch (Exception ex) {
                //Ignores an error because a row won't be created
            }
        }
        // Remove any blank elements.
        if (stations.containsKey("")) {
            stations.remove("");
        }

        ReadXMLFile.read(XMLFile, statStore);
        createV(stations);
        getDuplicates(stations);
        edgeLinker(statVertexCCL);
        edgeLinker(statVertexNSL);
        edgeLinker(statVertexNEL);
        edgeLinker(statVertexEWL);
        edgeLinker(statVertexDTL);
        
        /**
         * Transfers the vertexes and edges that we've created into
         * one big vertex because the edges are already distinct to their own
         * LINES.
         */
        
        bigArray.addAll(statVertexCCL);
        bigArray.addAll(statVertexNSL);
        bigArray.addAll(statVertexNEL);
        bigArray.addAll(statVertexEWL);
        bigArray.addAll(statVertexDTL);
        
        interchangeLinker(interChange);
        
    }

    /**
     * Method to find replicas of a Station
     *
     */
    public static void getDuplicates(Map<String, Station> imported) {
        Set<String> EntryChecker = new HashSet<String>();
        for (Station s : imported.values()) {
            if (!EntryChecker.add(s.getName())) {
                interChange.add(s);
            } else {
                EntryChecker.add(s.getName());
            }
        }
    }

    /**
     * Creates the edges of each station to link them together as a system
     *
     * Take in array that has vertexes of the station
     *
     * Everytime you find a replica, then do some fuck
     *
     * @param imported
     */
    public static void edgeLinker(ArrayList<Vertex> imported) {
        ArrayList<String> TrainSystem = new ArrayList<String>();
        for (Vertex s : imported) {
            TrainSystem.add(s.name);
        }

        /**
         * For each Station in Train System, initialize another for each loop
         * for each vertex in the train system, set it's edges to it's 
         * next and previous station if they both exist. Else, set the next station
         * or the previous station.
         */
        
        int counter = 0;
        float distance = 10;
        Vertex prevStn = null;
            for (Vertex curVertex : imported) {
                Vertex nextVertex = null;
                if (counter < imported.size()) {
                   nextVertex = imported.get(counter);
                }
                if (counter >= 1 && prevStn != null && counter < imported.size()) {
                    curVertex.adjacencies = new ArrayList<Edge>();
                    curVertex.adjacencies.add(new Edge(prevStn, distance - 5));
                    curVertex.adjacencies.add(new Edge(nextVertex, distance + 5));
                } else {
                    curVertex.adjacencies = new ArrayList<Edge>();
                    curVertex.adjacencies.add(new Edge(nextVertex, distance + 5));
                }
                prevStn = curVertex;
                counter++;
                distance += 5;
                System.out.println();
        }
        //Resets the counter
        counter = 0;

        // Collections.reverse(TrainSystem);
    }
    
    public static void interchangeLinker(ArrayList<Station> imported) {
        
        Set<String> stationChecker = new HashSet<String>();
        
        for (Station s : imported) {
            
            System.out.println(s.getCode());

            // If the stationChecker can't add the station, it means that
            // it has three lines on the same station.
            if (!stationChecker.add(s.getName())) {
                
            } else {
                /**
                 * Else, let's find the 2 station objects that have the same
                 * Station name and link them together.
                 * 
                 * The question is, what do I use to link them together?
                 * 
                 * 
                 * The Iterator linkVertex uses Station Code to find a pair.
                 */
                for (Vertex stn : bigArray) {
                        if (stn.name == s.getCode()) {
                           
                       }
                    
                    
                    if (s.getCode().equalsIgnoreCase(stn.name)) {
                        //stn.adjacencies.add(e)
                    }
                }
            }
        }
    }
    
    public static void createV(Map<String, Station> imported) {
        /**
         * For loop to spawn all the vertex points
         *
         * All of the vertexes are null.
         */
        for (String s : imported.keySet()) {
            if (s.startsWith("CC")) {
                statVertexCCL.add(new Vertex(s));
            } else if (s.startsWith("EW")) {
                statVertexEWL.add(new Vertex(s));
            } else if (s.startsWith("NS")) {
                statVertexNSL.add(new Vertex(s));
            } else if (s.startsWith("NE")) {
                statVertexNEL.add(new Vertex(s));
            } else if (s.startsWith("DT")) {
                statVertexDTL.add(new Vertex(s));
            }
        }
    }

    /**
     * Get's the value of a key
     *
     * @param <T>
     * @param <E>
     * @param map
     * @param value
     * @return
     */
    public static <T, E> T getValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}
