package sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Inclusions {
    private int[][] tab;
    private int ySize;
    private int xSize;
    private int seeds;
    private int r;
    private int type;

    public Inclusions(int xSize, int ySize, int seeds,int[][] tab,int r,int type) {
        this.ySize = ySize;
        this.xSize = xSize;
        this.seeds = seeds;
        this.tab=tab;
        this.r=r;
        this.type=type;
    }

    int[][] generator(){
        Random rand_x = new Random();
        Random rand_y = new Random();


        for (int i=1;i<seeds+1;i++) {

            int x = rand_x.nextInt(xSize);
            int y = rand_y.nextInt(ySize);

            if (tab[y][x] == 0 || isOnBorder(x,y)) {
                if (type == 0) {
                    fillCircle(y,x,r);                }
                else if (type==1){
                    fillSquare(y,x,r);
                }
            }
            else{
                --i;
            }
        }
        return tab;
}



private boolean isInCircle(int r, int y,int x){
        return (x*x) +y*y <= r*r;
}

public int[][] fillCircle(int y,int x,int r) {
    for (int i = y - r; i <= y + r; i++) {
        for (int j = x-r; j<= x+r;j++){
            if (isInCircle(r,Math.abs(y-i),Math.abs(x-j)) && ySize> i &&i>=0 &&j>=0 &&xSize>j){
                tab[i][j]=-1;
            }
        }
    }
    return tab;
}

    public int[][] fillSquare(int y,int x,int r) {
        for (int i = y - r; i <= y + r; i++) {
            for (int j = x-r; j<= x+r;j++){
                if (ySize> i &&i>=0 &&j>=0 &&xSize>j){
                    tab[i][j]=-1;
                }
            }
        }
        return tab;
    }

    private boolean isOnBorder(int x , int y){
        List<Integer> n =
                Stream.of(a(x-1,y-1),a(x,y-1),a(x+1,y-1),a(x-1,y),a(x+1,y),a(x-1,y+1),a(x,y+1),a(x+1,y+1))
                        .filter(v->v!=null && v !=0 && v!=-1).collect(Collectors.toList());
        Map<Integer,Integer> occurences = new HashMap<>();
        n.forEach(v->occurences.compute(v,(key,occ)->occ!=null ? occ + 1 : 1));
        return occurences.size() >1;
    }

    private Integer a(int x,int y){
        if(x<0) return null;
        if(y<0) return null;
        if(x>=tab[0].length) return null;
        if(y>=tab.length) return null;
        return tab[y][x];

    }


}
