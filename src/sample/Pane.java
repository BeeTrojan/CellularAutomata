package sample;

public class Pane {

    private int xSize;
    private int ySize;

    private Cell[][] dots;

    public Pane(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;

        dots = new Cell[xSize][ySize];

        indots();

    }

    private void indots(){
        for (int i = 0; i < xSize; i++){
            for (int n = 0; n < ySize; n++){
                dots[i][n] = new Cell();
            }
        }

    }

}
