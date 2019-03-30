package Model;


import javafx.scene.paint.Color;

/**
 * Class representing a tile.
 */
public final class Tile
{
    /**
     * Determines if the operation system is windows.
     */
    private boolean OS=System.getProperty("os.name").contains("Windows");
    /**
     * The numerical value of this tile.
     */
    private int value;
    /**
     * A sliding and merging tile's before merge value.
     */
    public int preValue = 0;
    /**
     * Determines if the tile has just been added to the {@code Board}.
     */
    public boolean justSpawned = false;
    /**
     * Determines if the tile is the result of two merged Tiles.
     */
    public boolean merged = false;
    /**
     * Determines if the tile is sliding somewhere on the {@code Board}.
     */
    public boolean sliding = false;
    /**
     * Stores the tile's previous position and it's new position on the {@code Board}.
     */
    public int movingFromTo[];
    /**
     * Scale value for the spawn animation of the tile.
     */
    public float scaleSpawn = (float) (OS ? 0.1 : 0.05);
    /**
     * Scale change value for the spawn animation of the tile.
     */
    public float spawnChange = (float) (OS ? 3.5 : 1.75);
    /**
     * Scale value for the merge animation of two Tiles.
     */
    public float scaleMerge = 1.0f;
    /**
     * Scale change value for the merge animation of two Tiles.
     */
    public float mergeChange = (float) (OS ? 0.35 : 0.175);
    /**
     * Determines if the tile will merge with an another tile after it finishes sliding.
     */
    public boolean willMergeAfterSlide = false;
    /**
     * Represents the current position of the tile if it is sliding.
     */
    public float movement = 0;
    /**
     * Changes the tile's current position toward it's destination if it is sliding.
     */
    public float movementChange = 0;
    /**
     * Constructs a {@code Tile} object with a given value.
     * @param value The value to set this tile to.
     */
    public Tile(int value) { this.value=value; }
    /**
     * Constructs an empty {@code Tile} object (with value set to zero).
     */
    public Tile(){ value=0; }
    /**
     * Sets the values necessary for the tile's slide animation.
     * @see #movingFromTo
     * @see #movement
     * @see #movementChange
     * @param fromRow from which row
     * @param toRow to which row
     * @param fromColumn which column
     * @param toColumn to which column
     */
    void SetMovement(int fromRow, int fromColumn, int toRow, int toColumn){
        movingFromTo = new int[]{fromRow, fromColumn , toRow, toColumn};
        movement = CalculateSlideSpeed();
        movementChange = OS ? movement * 12 : movement;
    }
    /**
     * Returns the speed of the tile's slide animation.
     * @return the speed of the tile's slide animation
     */
    private float CalculateSlideSpeed() {
        if (movingFromTo != null) {
            if(movingFromTo[0]==movingFromTo[2])
                return (float) ((movingFromTo[3]- movingFromTo[1]));
            else
                return (float) ((movingFromTo[2]- movingFromTo[0]));
        }
        return 0;
    }
    /**
     * Returns if the tile is empty.
     * @return if the tile is empty
     */
    public boolean IsEmpty() {
        return value == 0;
    }
    /**
     * Returns the value of the tile.
     * @return the value of the tile
     */
    public int GetValue(){
        return value;
    }
    /**
     * Returns the color of the tile's text given by it's value or previous value.
     * @return the color of the tile's text
     * @param value the value to determine the tile's text color by.
     */
    public Color GetTextColor(int value) {
        return value < 16 ? Color.color(0.4666, 0.4313, 0.3960) :  Color.color(0.9764, 0.9647, 0.9490);
    }
    /**
     * Returns the color of the tile given by it's value or previous value.
     * @return the color of the tile
     * @param value the value to determine the tile's color by.
     */
    public Color GetBackgroundColor(int value) {
        switch (value) {
            case 2:    return Color.color(0.9333, 0.8941, 0.8549);
            case 4:    return Color.color(0.9294, 0.8784, 0.7843);
            case 8:    return Color.color(0.9490, 0.6941, 0.4745);
            case 16:   return Color.color(0.9607, 0.5843, 0.3882);
            case 32:   return Color.color(0.9647, 0.4862, 0.3725);
            case 64:   return Color.color(0.9647, 0.3686, 0.2313);
            case 128:  return Color.color(0.9294, 0.8117, 0.4470);
            case 256:  return Color.color(0.9294, 0.8000, 0.3803);
            case 512:  return Color.color(0.9294, 0.7843, 0.3137);
            case 1024: return Color.color(0.9294, 0.7725, 0.2470);
            case 2048: return Color.color(0.9294, 0.7607, 0.1803);
        }
        return Color.color(0.8039, 0.7568, 0.7058);
    }
}
