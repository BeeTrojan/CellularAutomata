package sample;

import java.util.Random;

public class Seed {
    private int[][] tab;
    private int ySize;
    private int xSize;
    private int seeds;


    public Seed(int xSize, int ySize, int seeds) {
        this.ySize = ySize;
        this.xSize = xSize;
        this.seeds = seeds;
    }

    int[][] generator(){

        tab = new int[ySize][xSize];

        Random rand_x = new Random();
        Random rand_y = new Random();


        for (int i=1;i<seeds+1;i++) {

            int x = rand_x.nextInt(xSize);
            int y = rand_y.nextInt(ySize);

            if (tab[y][x] == 0) {

                tab[y][x] = i;

            }
            else{
                i=i-1;
            }
        }
        return tab;
    }

    public static void main(String[] args) {
        Seed s = new Seed(10,5,10);
    }


}
