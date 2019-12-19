package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sample.GrainGrow.cloneArr;

public class MonteCarlo {
    private int[][] tab;
    private int iteration;
    private int xSize;
    private int ySize;
    int[][] tTab;


    public MonteCarlo(int[][] tab, int iteration) {
        this.tab = tab;
        this.iteration = iteration;
        xSize = tab.length;
        ySize = tab[0].length;

    }

    private long countN(int x, int y) {
        int color = tab[x][y];
        return
                Stream.of(a(x - 1, y - 1), a(x, y - 1), a(x + 1, y - 1), a(x - 1, y), a(x + 1, y), a(x - 1, y + 1), a(x, y + 1), a(x + 1, y + 1))
                        .filter(v -> v != null && v>0 && v != color).count();

    }

    public int[][] grainGrowMC() {

        for (int i = 0; i < iteration; i++) {
            mc();
          //  tab = cloneArr(tTab);

        }
        return tab;
    }


    private void mc() {
        Random rand_x = new Random();
        List<Point> toVisit = new ArrayList<>();


        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if(tab[i][j] > -1)
                toVisit.add(new Point(i, j));
            }
        }

        int x;

        while (!toVisit.isEmpty()) {
            x = rand_x.nextInt(toVisit.size());
            Point p = toVisit.get(x);
            checkN(p.x, p.y);
            toVisit.remove(x);

            }
        }


    private void checkN(int x, int y) {


        Random random = new Random();

        int xD = (int) countN(x, y);
        List<Point> n = countR(x, y);
        if (n.size() <= 0) return;
        int rn = random.nextInt(n.size());
        Point randomN = n.get(rn > 0 ? rn : 0);
        int p = tab[x][y];
        tab[x][y] = tab[randomN.x][randomN.y];
        if (xD <= countN(randomN.x, randomN.y)) {
            tab[x][y] = p;
        }


    }

    private List<Point> countR(int x, int y) {
        int color = tab[x][y];
        return
                Stream.of(new Point(x - 1, y - 1), new Point(x, y - 1), new Point(x + 1, y - 1), new Point(x - 1, y), new Point(x + 1, y), new Point(x - 1, y + 1), new Point(x, y + 1), new Point(x + 1, y + 1))
                        .filter(v -> b(v) && tab[v.x][v.y] != color && tab[v.x][v.y] >-1).collect(Collectors.toList());
    }


    private Integer a(int x, int y) {
        if (x < 0) return null;
        if (y < 0) return null;
        if (y >= tab[0].length) return null;
        if (x >= tab.length) return null;
        return tab[x][y];

    }


    private boolean b(Point p) {
        if (p.x < 0) return false;
        if (p.y < 0) return false;
        if (p.y >= tab[0].length) return false;
        if (p.x >= tab.length) return false;
        return true;

    }


}
