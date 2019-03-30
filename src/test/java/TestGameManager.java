import static java.lang.Math.pow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Model.Board;
import Model.GameManager;
import Model.Tile;
import org.junit.jupiter.api.Test;


public class TestGameManager {

    private GameManager manager;

    TestGameManager(){
        this.manager = new GameManager();
    }

    @Test
    public void newBoardEmptyTest() {

        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for (int i = 0; i<B.Size(); i++) {
                for (int j = 0; j < B.Size(); j++) {
                    assertEquals(0, B.Tiles[i][j].GetValue(), "New board should be empty!");
                }
            }
        }
    }

    @Test
    public void SpaceCheckTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            int w=0;
            while (w<1000){
                for (int i = 0; i < B.Size(); i++) {
                    for (int j = 0; j < B.Size(); j++) {
                        B.Tiles[i][j] = new Tile(8);
                    }
                }
                int random1= (int) (Math.random() % B.Size());
                int random2= (int) (Math.random() % B.Size());
                B.Tiles[random1][random2]=new Tile();
                assertTrue(manager.CheckForSpace(B), "GameManager with one free tile should have space left");
                w++;
            }
        }
    }

    private void filler (Board B){
        for (int i = 0; i < B.Size(); i++) {
            int n;
            boolean multiplyOrDivide=true;
            if(i%2==0)
                n=2;
            else {
                n = (int) pow(2, B.Size());
                multiplyOrDivide=false;
            }
            for (int j = 0; j < B.Size(); j++) {
                B.Tiles[i][j] = new Tile(n);
                if (multiplyOrDivide)
                    n*=2;
                else
                    n/=2;
            }
        }
    }

    @Test
    public void GameOverCheckTest(){
        for(int bS=3; bS<=8; bS++) {
            int i=0;
            while (i<1000) {
                Board B1 = new Board(bS);
                Board B2 = new Board(bS);
                filler(B1);
                filler(B2);
                int random1 = (int) (Math.random() % B1.Size());
                int random2 = (int) (Math.random() % B1.Size());
                int random3 = random1 - 1 < 0 ? random1 + 1 : random1 - 1;
                int random4 = random2 - 1 < 0 ? random2 + 1 : random2 - 1;
                B1.Tiles[random1][random2] = new Tile(B1.Tiles[random3][random2].GetValue());
                B2.Tiles[random1][random2] = new Tile(B2.Tiles[random1][random4].GetValue());
                assertFalse(manager.CheckForGameOver(B1), "GameManager with at least one possible move should have moves left");
                assertFalse(manager.CheckForGameOver(B2), "GameManager with at least one possible move should have moves left");
                i++;
            }
        }
    }

    @Test
    public void TileAddTest(){
        for(int bS=3; bS<=8; bS++) {
            for(int i=0; i<bS; i++) {
                for (int j = 0; j < bS; j++) {
                    Board B = new Board(bS);
                    manager.addTile(B);
                    int check=0;
                    for (Tile[] Tile : B.Tiles) {
                        for (int t = 0; t < bS; t++) {
                            if (!Tile[t].IsEmpty()) {
                                check++;
                            }
                        }
                    }
                    assertEquals(1, check, "Add tile should be able to add a new tile to any free space!");
                }
            }
        }
    }

    @Test
    public void SortRightTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int i=0; i<bS; i++){
                for(int j=0; j<bS-1; j++){
                    B.Tiles[i][j]= new Tile(2);
                    B.Tiles[i][j+1]= new Tile(2);
                    manager.SortRight(B);
                    assertEquals(2, B.Tiles[i][bS-1].GetValue(), "On an empty row Tiles should always slide to the right");
                    assertEquals(2, B.Tiles[i][bS-2].GetValue(), "On an empty row Tiles should always slide to the right");
                }
            }
        }
    }

    @Test
    public void SortLeftTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int i=0; i<bS; i++){
                for(int j=bS-1; j>0; j--){
                    B.Tiles[i][j]= new Tile(2);
                    B.Tiles[i][j-1]= new Tile(2);
                    manager.SortLeft(B);
                    assertEquals(2, B.Tiles[i][0].GetValue(), "On an empty row Tiles should always slide to the left");
                    assertEquals(2, B.Tiles[i][1].GetValue(), "On an empty row Tiles should always slide to the left");
                }
            }
        }
    }

    @Test
    public void SortUpTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int j=0; j<bS; j++){
                for(int i=bS-1; i>0; i--){
                    B.Tiles[i][j]= new Tile(1024);
                    B.Tiles[i-1][j]= new Tile(1024);
                    manager.SortUp(B);
                    assertEquals(1024, B.Tiles[0][j].GetValue(), "On an empty column Tiles should always slide up");
                    assertEquals(1024, B.Tiles[1][j].GetValue(), "On an empty column Tiles should always slide up");
                }
            }
        }
    }

    @Test
    public void SortDownTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int j=0; j<bS; j++){
                for(int i=0; i<bS-1; i++){
                    B.Tiles[i][j]= new Tile(2048);
                    B.Tiles[i+1][j]= new Tile(2048);
                    manager.SortDown(B);
                    assertEquals(2048, B.Tiles[bS-1][j].GetValue(), "On an empty column Tiles should always slide down");
                    assertEquals(2048, B.Tiles[bS-2][j].GetValue(), "On an empty column Tiles should always slide down");
                }
            }
        }
    }

    @Test
    public void RightTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int i=0; i<bS; i++){
                for(int j=0; j<bS-1; j++){
                    B.Tiles[i][bS-1]= new Tile(256);
                    B.Tiles[i][j]= new Tile(256);
                    manager.Right(B);
                    assertEquals(512, B.Tiles[i][bS-1].GetValue(), "256+256 should be 512");
                }
            }
        }
    }

    @Test
    public void LeftTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int i=0; i<bS; i++){
                for(int j=bS-1; j>0; j--){
                    B.Tiles[i][0]= new Tile(256);
                    B.Tiles[i][j]= new Tile(256);
                    manager.Left(B);
                    assertEquals(512, B.Tiles[i][0].GetValue(), "256+256 should be 512");
                }
            }
        }
    }

    @Test
    public void UpTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int j=0; j<bS; j++){
                for(int i=bS-1; i>0; i--){
                    B.Tiles[0][j]= new Tile(512);
                    B.Tiles[i][j]= new Tile(512);
                    manager.Up(B);
                    assertEquals(1024, B.Tiles[0][j].GetValue(), "512+512 should be 1024");
                }
            }
        }
    }

    @Test
    public void DownTest(){
        for(int bS=3; bS<=8; bS++) {
            Board B = new Board(bS);
            for(int j=0; j<bS; j++){
                for(int i=0; i<bS-1; i++){
                    B.Tiles[bS-1][j]= new Tile(512);
                    B.Tiles[i][j]= new Tile(512);
                    manager.Down(B);
                    assertEquals(1024, B.Tiles[bS-1][j].GetValue(), "512+512 should be 1024");
                }
            }
        }
    }
}