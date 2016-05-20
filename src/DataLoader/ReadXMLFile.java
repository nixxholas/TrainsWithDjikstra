package DataLoader;

import static DataLoader.DataLoader.stations;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

/**
 * Universal XML Reader for Stations
 *
 * @author Nixholas
 *
 * Source: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public class ReadXMLFile {

    /**
     * Train Station XML Reader
     *
     * logs down each and every station and it's relevant X and Y axis for
     * analysis
     *
     * @param input File path for the XML File
     *
     * @param imported ArrayList of Stations to be populated with.
     */
    public static void read(String input, ArrayList<Station> imported) {

        try {

            File fXmlFile = new File(input);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("features");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    // System.out.println("Station Name : " + eElement.getAttribute("STN_NAM"));
                    String Name = eElement.getElementsByTagName("STN_NAM").item(0).getTextContent();
                    float xAxis = Float.parseFloat(eElement.getElementsByTagName("coordinates").item(0).getTextContent());
                    float yAxis = Float.parseFloat(eElement.getElementsByTagName("coordinates").item(1).getTextContent());
                    Station stn = new Station(Name, xAxis, yAxis);

                    // Element storage for checking
                    //Set<String> stnChecker = stations.keySet();

                    for (Station s : stations.values()) {
                        if (s.getName().equalsIgnoreCase(stn.getName())) {
                            s.setxAxis(stn.getxAxis());
                            s.setyAxis(stn.getyAxis());
                        } else {
                            // If I can add an element in stnChecker, Create a new entry in stations
//                            try {
//                                if (stnChecker.add(Name)) {
//                                    stations.put(Name, new Station(Name, xAxis, yAxis));
//                                }
//                            } catch (Exception ex) {
//
//                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void entityReader(String file) {

        try {

            File fXmlFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("SrchResults");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    // System.out.println("Station Name : " + eElement.getAttribute("STN_NAM"));
                    System.out.println("Station Name : " + eElement.getElementsByTagName("STN_NAM").item(0).getTextContent());
                    System.out.println("X Axis : " + eElement.getElementsByTagName("coordinates").item(0).getTextContent());
                    System.out.println("Y Axis : " + eElement.getElementsByTagName("coordinates").item(1).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
