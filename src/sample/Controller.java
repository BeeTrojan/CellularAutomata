package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Controller {


    @FXML
    private TextField x;
    @FXML
    private TextField y;
    @FXML
    private TextField seedinuput;

    @FXML
    private Canvas pane;
    private final int cSize = 2;
    private Map<Integer,Color>    colors = new HashMap<>();
    private int[][] tab;

    int xSize;
    int ySize;

    int seed;

    private Pane space;

    public void size(){

        xSize=Integer.parseInt(x.getText());
        ySize=Integer.parseInt(y.getText());

        space = new Pane(xSize, ySize);

        adSpaceSize();


    }

    private void adSpaceSize(){
        pane.setWidth(xSize * cSize);
        pane.setHeight(ySize * cSize);

    }

    public void grain(){
        GrainGrow g = new GrainGrow(tab);
        tab = g.grain();
        draw();

    }




    void draw() {

        GraphicsContext gc = pane.getGraphicsContext2D();


        Random rand = new Random();

        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++) {
                if(tab[i][j] != 0){
                   if(colors.size()<seed) {
                       Color color = Color.rgb(rand.nextInt(256),(rand.nextInt(256))*(i+1) %256,((i+1)*(1+j))%256);
                       gc.setFill(color);
                       colors.put(tab[i][j], color);
                   }else{
                       gc.setFill(colors.get(tab[i][j]));
                   }
                   gc.fillRect(j * cSize, i * cSize, cSize, cSize);
                }

            }
        }


        }



    public void seed(){
        seed=Integer.parseInt(seedinuput.getText());
        tab = new Seed(xSize,ySize,seed).generator();
        draw();


    }




}
