package sample;

import javafx.scene.paint.Color;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class InputOutput {


    public static String convertArrayToString(int[][] tab) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if(j==0){
                    stringBuilder.append(tab[i][j]);
                }
                else
                 stringBuilder.append(","+tab[i][j]);
            }
            if(i!=tab.length-1) {
                stringBuilder.append(";");
            }
        }
        return stringBuilder.toString();
    }

    public static int[][] convertStringToArray(String txtString){

    String tabRow[] = txtString.split(";");



    int ySize = tabRow.length;
    int xSize = tabRow[0].split(",").length;

    int tab[][]= new int[ySize][xSize];

    for (int i=0;i<ySize;i++){

        tab[i] = Arrays.stream(tabRow[i].split(",")).mapToInt(Integer::parseInt).toArray();
    }

    return tab;

    }

    public static void save(int[][] tab)
    {
        String str =convertArrayToString(tab);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("save"));

            writer.write(str);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static int[][] loadTxt(String filename){
        try {
            return  convertStringToArray( new Scanner(new File(filename)).useDelimiter("\\Z").next());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
  return  null;
    }


//    public void loadimage() {
//        try {
//            List<Integer> colorMap = new ArrayList<>();
//            BufferedImage bi =  ImageIO.read(new File("test.bmp"));
//            tab = new int[ bi.getHeight()][bi.getWidth()];
//            pane.setWidth(bi.getWidth());
//            pane.setHeight(bi.getHeight());
//            xSize = bi.getWidth();
//            ySize = bi.getHeight();
//
//            for(int y=0; y< bi.getHeight();++y){
//                for(int x=0; x< bi.getWidth();++x) {
//                    if(!colorMap.contains(bi.getRGB(x,y))) {
//                        colorMap.add(bi.getRGB(x, y));
//                        colors.put(colorMap.size(),rgbToColor(bi.getRGB(x, y)));
//                    }
//                    tab[y][x] = colorMap.indexOf(bi.getRGB(x,y)) +1;
//                }
//            }
//            seed = colorMap.size();
//            draw();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    //}

    private Color rgbToColor(int rgb){
        int alpha = (rgb >> 24) & 0xFF;
        int red =   (rgb >> 16) & 0xFF;
        int green = (rgb >>  8) & 0xFF;
        int blue =  (rgb      ) & 0xFF;
        return Color.rgb(red,green,blue);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(convertStringToArray("1,2,3;6,4,5")));
    }


}
