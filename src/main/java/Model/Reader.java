package Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class for reading the player scores and saved games from an xml file.
 */
public class Reader {
    /**
     * The logger of this class.
     */
    private static Logger logger = LoggerFactory.getLogger(Reader.class);
    /**
     * The {@code DocumentBuilder} of this class.
     * @see DocumentBuilder
     */
    private DocumentBuilder db;
    /**
     * Returns a list of {@code PlayerScore}s from the stored high scores.
     * @see PlayerScore
     * @return a list of {@code PlayerScore}s
     * @param scoretype the type of scores to get (average score or highest score)
     * @param boardSize the game type (board size) to get the high scores for
     */
    public List<PlayerScore> ReadUserScores(String scoretype , int boardSize){
        List<PlayerScore> highscores = new ArrayList<>();
        try {
            File input = new File(System.getProperty("user.home") + File.separator + "Project_2048"
                                    + File.separator + "userdata" + File.separator + "playerScores.xml");
            Document doc;
            if(input.exists() && !input.isDirectory())
                doc = db.parse(input);
            else
                doc= db.newDocument();

            NodeList n1 = doc.getElementsByTagName("gamemode");

            for (int i = 0; i < n1.getLength(); i ++) {
                if(Integer.parseInt(n1.item(i).getAttributes().getNamedItem("bS").getNodeValue())==boardSize) {
                    NodeList children = n1.item(i).getChildNodes();
                    for (int j = 0; j < children.getLength(); j++) {
                        if(children.item(j).getNodeName().equals(scoretype)){
                            PlayerScore temp = new PlayerScore(n1.item(i).getParentNode().getAttributes().getNamedItem("username").getTextContent() ,children.item(j).getTextContent());
                            highscores.add(temp);
                        }
                    }
                }
            }
            highscores = highscores.stream().sorted(Comparator.comparing(e-> Integer.parseInt(((PlayerScore) e).score)).reversed()).collect(Collectors.toList());
        }
        catch (SAXException | IOException e) {
            logger.error("Error in reading the user scores!", e);
        }
        return highscores;
    }
    /**
     * Returns a list a {@code GameLoader} object.
     * @see GameLoader
     * @return a {@code GameLoader} object to load a game from.
     * @param savefile the file to load a game from
     */
    public GameLoader LoadGame(String savefile){
        try{
            File input = new File(savefile);
            Document doc = db.parse(input);

            NodeList n1 = doc.getElementsByTagName("save");

            int gamemode= Integer.parseInt(n1.item(0).getChildNodes().item(0).getTextContent());

            Tile[][] result = new Tile[gamemode][gamemode];

            int pos=0;

            NodeList tiles=n1.item(0).getChildNodes().item(1).getChildNodes();

            for(int i=0; i<result.length; i++){
                for(int j=0; j<result.length; j++){
                    result[i][j]= new Tile (Integer.parseInt(tiles.item(pos).getTextContent()));
                    pos++;
                }
            }

            long score=Integer.parseInt(n1.item(0).getChildNodes().item(2).getTextContent());

            return new GameLoader(result, score, gamemode);

        } catch (IOException | SAXException e) {
            logger.error("Error in loading a saved Game!", e);
        }
        return null;
    }
    /**
     * Returns the password of the database connection.
     * @return password of the database connection
     */
    public String getPassword(){
        Document doc = null;
        try {
            doc = db.parse(getClass().getResourceAsStream("/META-INF/pwd.xml"));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        if (doc != null) {
            NodeList n = doc.getElementsByTagName("pwd");
            return n.item(0).getTextContent();
        }
        return null;
    }
    /**
     * Constructs a {@code Reader} object.
     */
    public Reader(){
        try {
            this.db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Error in creating the DocumentBuilder!", e);
        }
    }

}
