package com.entities;

import com.entities.player.Player;
import org.jsfml.graphics.*;

import java.nio.file.Paths;

public class PlayerStats {

    private Player player;
    private int enemies;
    private Texture texture;
    private Sprite sprite;

    private Color color;
    private Font font = new Font();
    private String[] stats = {"HP:", "Coins:", "Enemies Remaining:"};
    private Text[] text = new Text[stats.length];

    public PlayerStats(Player p){
        player = p;
        try{
            color = Color.WHITE;
            font.loadFromFile(Paths.get("src/com/resources/fonts/fonts.ttf"));


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEnemies(int enemies) {
        this.enemies = enemies;
    }

    public void draw(RenderWindow window){
        for (int i = 0; i < stats.length; i++) {

            if(i == 0){
                text[i] = new Text(stats[i] + player.getLife() + "/" + player.getMaxLife(), font, 20);
            }else if(i==1){
                text[i] = new Text(stats[i] + player.getCoins(), font, 20);
            }else{
                text[i] = new Text(stats[i] + enemies, font, 20);
            }

            text[i].setColor(Color.CYAN);
            text[i].setPosition(50, 50 + (i * 30));
            window.draw(text[i]);
        }
    }
}
