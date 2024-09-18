package org.example.csc311lab03group;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Car implements KeyListener {

    private ImageView carImg;

    public Car(ImageView carImg) {
        this.carImg = carImg;
        System.out.println("made car");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();
        carImg.setLayoutX(carImg.getLayoutX() + 10);
        System.out.println(key);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}

