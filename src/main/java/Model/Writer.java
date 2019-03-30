package Model;

import Controller.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;


/**
 * Class for writing the player scores and saved games into an xml file.
 */
public class Writer {
    /**
     * The logger of this object.
     */
    private static Logger logger = LoggerFactory.getLogger(Writer.class);
    /**
     * The {@code DocumnetBuilder} of this object.
     * @see DocumentBuilder
     */
    private DocumentBuilder db;
    /**
     * Returns a new player element.
     * @param doc the document to append this player element to
     * @param name the name of the player to append
     * @param score the score of the player to append
     * @param boardSize the board size of the played game's score
     * @return a player element
     * @see Element
     */
    private Element CreatePlayer(Document doc, String name, long score, int boardSize) {

        Element player = doc.createElement("player");

        Attr username = doc.createAttribute("username");
        player.setAttributeNode(username);

        username.setValue(name);

        player.appendChild(CreateGameMode(doc, score, boardSize));

        return player;
    }
    /**
     * Returns a new gamemode element.
     * @param doc the document to append this gamemode element to
     * @param score the score of the gamemode to append
     * @param boardSize the board size of the gamemode
     * @return a gamemode element
     * @see Element
     */
    private Element CreateGameMode(Document doc, long score, int boardSize){
        Element gamemode = doc.createElement("gamemode");
        Element highscore = doc.createElement("highscore");
        Element avgscore = doc.createElement("avgscore");

        Attr gamesPlayed = doc.createAttribute("gamesPlayed");
        Attr bS=doc.createAttribute("bS");

        avgscore.setAttributeNode(gamesPlayed);
        gamemode.setAttributeNode(bS);

        gamemode.appendChild(highscore);
        gamemode.appendChild(avgscore);

        highscore.appendChild(doc.createTextNode(String.valueOf(score)));
        avgscore.appendChild(doc.createTextNode(String.valueOf(score)));

        gamesPlayed.setValue(String.valueOf(1));
        bS.setValue(String.valueOf(boardSize));

        return gamemode;
    }
    /**
     * Modifies an existing player element.
     * @param doc the document to modify this player in
     * @param score the new score of this player
     * @param boardSize the board size of the player's new score
     * @param rootList the children of the modifiable player element
     * @see Element
     */
    private void ModifyPlayer(NodeList rootList, Document doc, long score, int boardSize){
        boolean contains = false;
        for(int i=0; i< rootList.getLength(); i++){
            int temp = Integer.parseInt(rootList.item(i).getAttributes().getNamedItem("bS").getTextContent());
            if(temp==boardSize){
                ModifyGameMode(rootList.item(i).getChildNodes(), score);
                contains = true;
            }
        }
        if(!contains) {
            Element gamemode = CreateGameMode(doc, score, boardSize);
            rootList.item(0).getParentNode().appendChild(gamemode);
        }
    }
    /**
     * Modifies an existing gamemode element.
     * @param score the score of this gamemode
     * @param rootList the children of the modifiable gamemode element
     * @see Element
     */
    private void ModifyGameMode(NodeList rootList, long score){
        for(int i=0; i<rootList.getLength(); i++){
            if(rootList.item(i).getNodeName().equals("highscore")){
                if(Integer.parseInt(rootList.item(i).getTextContent()) < score)
                    rootList.item(i).setTextContent(String.valueOf(score));
            }
            if(rootList.item(i).getNodeName().equals("avgscore")){
                int tempAvg = Integer.parseInt(rootList.item(i).getTextContent())*Integer.parseInt(rootList.item(i).getAttributes().getNamedItem("gamesPlayed").getTextContent());
                int tempBs = Integer.parseInt(rootList.item(i).getAttributes().getNamedItem("gamesPlayed").getTextContent());
                rootList.item(i).getAttributes().getNamedItem("gamesPlayed").setTextContent(String.valueOf(tempBs+1));
                rootList.item(i).setTextContent(String.valueOf((tempAvg+score)/Integer.parseInt(rootList.item(i).getAttributes().getNamedItem("gamesPlayed").getTextContent())));
            }
        }
    }
    /**
     * Writes the score of the currently playing player's finished game into an xml database containing all of the players scores.
     * @param score the score of this game
     * @param boardSize the size of this game's board
     */
    public void WriteScore(long score, int boardSize) {
        try {
            File input = new File(System.getProperty("user.home") + File.separator + "Project_2048" + File.separator
                                            + "userdata" + File.separator + "playerScores.xml");
            Document doc;
            if(input.exists() && !input.isDirectory())
                doc = db.parse(input);
            else
                doc= db.newDocument();

            NodeList rootList = doc.getElementsByTagName("players");
            Node root;
            if(rootList.getLength()!=0) {
                boolean thereWasNoChild = true;
                root = rootList.item(0);
                NodeList list = root.getChildNodes();
                for(int i=0; i<list.getLength(); i++){
                    if(list.item(i).getNodeName().equals("player")){
                        if(list.item(i).getAttributes().getNamedItem("username").getTextContent().equals(Main.currentUser)){
                            ModifyPlayer(list.item(i).getChildNodes(), doc, score, boardSize);
                            thereWasNoChild = false;
                        }
                    }
                }
                if(thereWasNoChild)
                    root.appendChild(CreatePlayer(doc, Main.currentUser, score, boardSize));
            }
            else {
                root = doc.createElement("players");
                doc.appendChild(root);
                root.appendChild(CreatePlayer(doc, Main.currentUser, score, boardSize));
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(input.toString()));

            t.transform(source, result);

        } catch (SAXException | IOException | TransformerException e) {
            logger.error("Error in writing playerScore.xml!", e);
        }
    }
    /**
     * Writes the current player's ongoing game into an xml file to be loaded later.
     * @param board the board of the game to save
     * @see Board
     */
    public void SaveGame(Board board){
        try {
            Document doc = db.newDocument();
            Element root = doc.createElement("save");
            doc.appendChild(root);
            Element gamemode=doc.createElement("gamemode");
            Element boardstate=doc.createElement("boardstate");
            Element actscore=doc.createElement("score");

            root.appendChild(gamemode);
            root.appendChild(boardstate);
            root.appendChild(actscore);

            gamemode.appendChild(doc.createTextNode(String.valueOf(board.Size())));
            actscore.appendChild(doc.createTextNode(String.valueOf(board.GetScore())));

            for (int i=0; i<board.Size(); i++) {
                for (int j = 0; j < board.Size(); j++) {
                    Element tile=doc.createElement("tile");
                    tile.appendChild(doc.createTextNode(String.valueOf(board.Tiles[i][j].GetValue())));
                    boardstate.appendChild(tile);
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();

            DOMSource source = new DOMSource(doc);

            File f = new File (System.getProperty("user.home") + File.separator + "Project_2048"
                                        + File.separator + "userdata" + File.separator + Main.currentUser);

            if(!f.exists())
                if(f.mkdir())
                    System.out.println(f);

            StreamResult result = new StreamResult(new File(Main.savefile));

            t.transform(source, result);

        } catch (TransformerException e) {
            logger.error("Error in writing a save file!", e);
        }
    }
    /**
     * Constructs a {@code Writer} object.
     */
    public Writer(){
        try {
            db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Error loading DocumentBuilder!", e);
        }
    }
}

