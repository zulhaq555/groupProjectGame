package com.TileMap;

import com.main.Main;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class TileMap {

    // Position
    private double x, y;

    // Bounds
    private int xMin, yMin, xMax, yMax;

    private double smoothScroll;

    // map
    private int[][] map;
    private int tileSize, numRows, numCols, width, height;

    // tileSet
    private Texture tileSet = new Texture();
    //private Sprite[][] sprite = new Sprite[numTilesAcross][numTilesHigh];
    private int numTilesAcross, numTilesHigh;
    private Tiles[][] tiles;

    // drawing
    private int rowOffSet, colOffSet, numRowsToDraw, numColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = (Main.HEIGHT * Main.SCALE) / tileSize;
        numColsToDraw = (Main.WIDTH * Main.SCALE) / tileSize;
        smoothScroll = 0.07;

    }

    /**Loads Tileset into memory*/
    public void loadTiles(String s){

        try {

            tileSet.loadFromFile(Paths.get(s));
            numTilesAcross = tileSet.getSize().x / tileSize;
            numTilesHigh = tileSet.getSize().y / tileSize;

            tiles = new Tiles[numTilesHigh][numTilesAcross];
            Sprite[][] sprite = new Sprite[numTilesHigh][numTilesAcross];

            for (int col = 0; col < numTilesAcross; col++) {
                for (int row = 0; row < numTilesHigh; row++) {
                    /*Need to fix the tiling issue, window not showing correct tiles, need to sort the numbers*/

                    sprite[row][col] = new Sprite(tileSet);
                    sprite[row][col].setTextureRect(new IntRect(col * tileSize, row * tileSize, tileSize, tileSize));
                    tiles[row][col] = new Tiles(sprite[row][col], Tiles.BLOCKED);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String s) {
        try {
            InputStream in = new FileInputStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            xMin = Main.WIDTH * Main.SCALE - width;
            xMax = 0;
            yMin = Main.HEIGHT * Main.SCALE - height;
            yMax = 0;

            String blank = "\\s+";
            for (int row = 0; row < numRows; row++) {

                String line = br.readLine();
                String[] tokens = line.split(blank);

                for (int col = 0; col < numCols; col++) {

                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTileSize()
    {
        return tileSize;
    }

    public int getXPosition()
    {
        return (int)x;
    }

    public int getYPosition()
    {
        return (int)y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getType(int row, int col)
    {
        int rowCol = map[row][col];
        int r = rowCol / numTilesAcross;
        int c = rowCol % numTilesAcross;
        if(rowCol == -1){
            return Tiles.NORMAL;
        }else{
            return tiles[r][c].getType();
        }
    }

    public void setPosition(double x, double y)
    {
        this.x += (x - this.x) * smoothScroll;
        this.y += (y - this.y) * smoothScroll;
        fixBounds();

        colOffSet = (int)-this.x / tileSize;
        rowOffSet = (int)-this.y / tileSize;
    }

    private void fixBounds()
    {
        if (x < xMin){x = xMin;}
        if (y < yMin){y = yMin;}
        if (x > xMax){x = xMax;}
        if (y > yMax){y = yMax;}
    }

    public void draw(RenderWindow window) {
        for (int row = rowOffSet; row < rowOffSet + numRowsToDraw; row++) {
            if (row >= numRows) {
                break;
            }

            for (int col = colOffSet; col < colOffSet + numColsToDraw; col++) {
                if (col >= numCols) {
                    break;
                }

                if (map[row][col] == -1) {
                    continue;
                }

                int rowCol = map[row][col];
                int r = rowCol / numTilesAcross;
                int c = rowCol % numTilesAcross;

                tiles[r][c].draw(window, tiles[r][c].getImage(), (int)x + (col*tileSize), (int)y + (row*tileSize));

            }
        }
    }
}

