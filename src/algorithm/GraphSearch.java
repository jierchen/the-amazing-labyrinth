package algorithm;

import model.tiles.Tile;

public interface GraphSearch {

    boolean reachable(Tile[][] tiles, int startingRow, int startingCol, int targetRow, int targetCol);
}
