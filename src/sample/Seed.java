package sample;

import java.util.Random;

public class Seed {
    private int[][] tab;
    private int ySize;
    private int xSize;
    private int seeds;


    public Seed(int xSize, int ySize, int seeds, int[][] tab) {
        this.ySize = ySize;
        this.xSize = xSize;
        this.seeds = seeds;
        this.tab = tab;
    }

    int[][] generator() {


        Random rand_x = new Random();
        Random rand_y = new Random();


        for (int i = 1; i < seeds + 1; i++) {

            int x = rand_x.nextInt(xSize);
            int y = rand_y.nextInt(ySize);

            if (tab[y][x] == 0) {

                tab[y][x] = i;

            } else {
                --i;
            }
        }
        return tab;
    }

    int[][] generatorMC() {

        Random rand = new Random();

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                if(tab[i][j]!=-1)
                tab[i][j] = rand.nextInt(seeds)+1;
            }

        }
    return tab;
    }
}
