package com.entities.enemies;

import com.TileMap.*;
import com.entities.MapEntity;
import org.jsfml.graphics.RenderWindow;

public abstract class Enemy extends MapEntity{

    protected int health, maxHealth, attackDamage;
    protected boolean dead, recDamage;

    protected long damageTimer;

    public Enemy(TileMap tm) {
        super(tm);
    }

    public boolean isDead() {
        return dead;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void hit(int damage) {
        if (dead || recDamage) {
            return;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        if (health == 0)
        {
            dead = true;
        }
        recDamage = true;
        damageTimer = System.nanoTime();
    }

    public abstract void update();


    public abstract void draw(RenderWindow window);
}

