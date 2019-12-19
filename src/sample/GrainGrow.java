package sample;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;
public class GrainGrow {

    private int[][] tab;
    private int percent;
    private float perc;
    public GrainGrow(int[][] tab) {
        this.tab = tab;
    }
    public GrainGrow(int[][]tab,int percent){
        this.tab=tab;
        this.percent=percent;
    }

    private int findM(int x, int y){
       List<Integer> n =
               Stream.of(a(x-1,y-1),a(x,y-1),a(x+1,y-1),a(x-1,y),a(x+1,y),a(x-1,y+1),a(x,y+1),a(x+1,y+1))
                       .filter(v->v!=null && v>0).collect(Collectors.toList());
       if(n.size()==0) return 0;
        Map<Integer,Integer> occurences = new HashMap<>();
        n.forEach(v->occurences.compute(v,(key,occ)->occ!=null ? occ + 1 : 1));
         List<Integer> occSorted = occurences.values().stream().sorted().collect(Collectors.toList());
         List<Integer> maxVal = occSorted.stream().filter(v-> v.equals(occSorted.stream().max(Integer::compareTo).get())).collect(Collectors.toList());
        return new ArrayList<>(getKeysByValue(occurences, (maxVal.get(0)))).get( (Math.abs(new Random().nextInt())%maxVal.size()) );
    }

    private int findN(int x, int y){
        List<Integer> n =
                Stream.of(a(x,y-1),a(x-1,y),a(x+1,y),a(x,y+1))
                        .filter(v->v!=null && v>0).collect(Collectors.toList());
        if(n.size()==0) return 0;
        Map<Integer,Integer> occurences = new HashMap<>();
        n.forEach(v->occurences.compute(v,(key,occ)->occ!=null ? occ + 1 : 1));
        List<Integer> occSorted = occurences.values().stream().sorted().collect(Collectors.toList());
        List<Integer> maxVal = occSorted.stream().filter(v-> v.equals(occSorted.stream().max(Integer::compareTo).get())).collect(Collectors.toList());
        if (maxVal.get(0) < 3){
            return 0;
        }
        else {
            Set<Integer> colorsMostOccurences = getKeysByValue(occurences, (maxVal.get(0)));
            return new ArrayList<>(colorsMostOccurences).get((Math.abs(new Random().nextInt()) % maxVal.size()));
        }
    }
    private int findF(int x, int y){
        List<Integer> n =
                Stream.of(a(x-1,y-1),a(x+1,y-1),a(x-1,y+1),a(x+1,y+1))
                        .filter(v->v!=null && v>0).collect(Collectors.toList());
        if(n.size()==0) return 0;
        Map<Integer,Integer> occurences = new HashMap<>();
        n.forEach(v->occurences.compute(v,(key,occ)->occ!=null ? occ + 1 : 1));
        List<Integer> occSorted = occurences.values().stream().sorted().collect(Collectors.toList());
        List<Integer> maxVal = occSorted.stream().filter(v-> v.equals(occSorted.stream().max(Integer::compareTo).get())).collect(Collectors.toList());
        if (maxVal.get(0) < 3){
            return 0;
        }
        else {
            Set<Integer> colorsMostOccurences = getKeysByValue(occurences, (maxVal.get(0)));
            return new ArrayList<>(colorsMostOccurences).get((Math.abs(new Random().nextInt()) % maxVal.size()));
        }
    }

    private Integer a(int x,int y){
    if(x<0) return null;
    if(y<0) return null;
    if(x>=tab[0].length) return null;
    if(y>=tab.length) return null;
    return tab[y][x];

    }

    public int[][] grain(){
            int[][] tmpTab = new int[tab.length][tab[0].length];
            boolean wasZero = true;
            int f = -1000;
            while(wasZero) {
                wasZero = false;
               tmpTab = cloneArr(tab);
                for (int i = 0; i < tab.length; i++) {
                    for (int j = 0; j < tab[0].length; j++) {
                        if (tab[i][j] == 0) {
                            tmpTab[i][j] = findM(j,i);
                            if(findM(j,i)!=0)
                            wasZero = true;
                        }

                    }
                }
                ++f;
                tab = cloneArr(tmpTab);
            }
                return tab;
    }


    public int[][] grainMoore2(){
        int[][] tmpTab = new int[tab.length][tab[0].length];
        boolean wasZero = true;
        while(wasZero) {
            wasZero = false;
            tmpTab = cloneArr(tab);
            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab[0].length; j++) {
                    if (tab[i][j] == 0) {
                        int c = findN(j,i);
                        if(c==0){
                            c = findF(j,i);
                            if(c==0){
                                perc=(float)percent/100;
                                if (checkRand()<perc)
                                c=findM(j,i);
                            }
                        }
                        tmpTab[i][j] = c;
                        wasZero = true;
                    }
                }
            }
            tab = cloneArr(tmpTab);
        }
        return tab;

    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static int[][] cloneArr(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }


     public static float checkRand()
        {
            Random r = new Random();

            return r.nextFloat();
        }
    }




