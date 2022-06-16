package com.sound;

import org.jsfml.audio.*;

import java.io.IOException;
import java.nio.file.Paths;

public class GameSound {
    private String location;
    private SoundBuffer soundBuffer;
    private Sound sound;

    public GameSound(String path) {
        soundBuffer = new SoundBuffer();

        try {
            soundBuffer.loadFromFile(Paths.get(path));
        } catch(IOException ex) {
            //Something went wrong
            System.err.println("Failed to load the sound:");
            ex.printStackTrace();
        }

        sound = new Sound();
        sound.setBuffer(soundBuffer);

    }
    public void playSoundLoop(){
       sound.play();
    }
    public void playSound() {
        if(sound.getStatus() == SoundSource.Status.STOPPED) {
            sound.setVolume(20);
            sound.play();
        }else{
            return;
        }
    }

    public void stopSound(){
        sound.stop();
    }

    public Sound getSound() {
        return sound;
    }
}