package helper;

import model.tile.Tile;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch implements GraphSearch {

    private Tile[][] tiles;

    public BreadthFirstSearch(Tile[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public boolean reachable(int startRow, int startCol, int targetRow, int targetCol) {
        boolean[][] visitedTiles = new boolean[tiles.length][tiles.length];
        Queue<Tile> searchQueue = new LinkedList<>();

        // Visit initial tile
        visitedTiles[startRow][startCol] = true;
        searchQueue.add(tiles[startRow][startCol]);

        while(!searchQueue.isEmpty()) {
            Tile currentTile = searchQueue.poll();

            // Reachable target tile
            if(currentTile.getRow() == targetRow && currentTile.getCol() == targetCol) {
                return true;
            }

            // Check all adjacent tiles
            List<Tile> adjacentTiles = currentTile.getAdjacentTiles();
            for(Tile adjacentTile : adjacentTiles) {
                int row = adjacentTile.getRow();
                int col = adjacentTile.getCol();

                // Check non-visited adjacent tiles
                if(!visitedTiles[row][col]) {
                    searchQueue.add(adjacentTile);
                    visitedTiles[row][col] = true;
                }
            }
        }

        return false;
    }
}
