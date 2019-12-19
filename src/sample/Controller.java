package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {


    @FXML
    private TextField x;
    @FXML
    private TextField y;
    @FXML
    private TextField seedinuput;
    @FXML
    private TextField number;
    @FXML
    private TextField rd;
    @FXML
    private TextField percent;
    @FXML
    private TextField boardSize;
    @FXML
    private TextField jgb;
    @FXML
    private TextField iteration;
    private Set<Integer> colorsNotClear = new HashSet();
    @FXML
    private TextField hetero;
    @FXML
    private TextField homo;

    @FXML
    private Canvas pane;
    private final int cSize = 1;
    private Map<Integer,Color>    colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);put(0,Color.WHITE);put(-2,Color.RED);}};
    private int[][] tab;
    private int [][] ener;
    private FileChooser fileChooser;
    int xSize;
    int ySize;
    boolean energy;
    int seed;
    int inclusions;
    int r;
    int type;
    int percentInt;
    int iter;
    int homos;
    int heteros;
  //  private Pane space;


    @FXML
    public void initialize(){

    }

    public void size(){

        xSize=Integer.parseInt(x.getText());
        ySize=Integer.parseInt(y.getText());

        if (xSize > 500){
            xSize=500;
        }
        if (ySize > 500){
            ySize=500;
        }
      //  space = new Pane(xSize, ySize);

        adSpaceSize();


    }

    private void adSpaceSize(){

        pane.setWidth(xSize * cSize);
        pane.setHeight(ySize * cSize);
        tab = new int[ySize][xSize];
    }

    public void grain(){
        GrainGrow g = new GrainGrow(tab);
        tab = g.grain();
        draw();

    }
    public void grain2(){
        percentInt=Integer.parseInt(percent.getText());
        GrainGrow g = new GrainGrow(tab,percentInt);
        tab = g.grainMoore2();
        draw();

    }
    public void MonteCarloGrain(){
        iter=Integer.parseInt(iteration.getText());
       tab = new MonteCarlo(tab,iter).grainGrowMC();
       draw();

    }

    public void border(){
        r=Integer.parseInt(boardSize.getText());
        tab = new Inclusions(xSize,ySize,inclusions,tab,r,type).grain();
        draw();

    }
    public void selectedB(){
        r = Integer.parseInt(boardSize.getText());
        for (int i =0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                if (colorsNotClear.contains(tab[i][j]))
                tab = new Inclusions(xSize, ySize, tab, r,i,j ).grainOne();
            }
        }
        draw();

    }

    public void clear(){
        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++){
                if(tab[i][j] !=0 && tab[i][j]!=-1){
                    tab[i][j]=0;
                }
            }
        }
        seed=0;
        colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);put(0,Color.WHITE);}};
        colorsNotClear.clear();
        draw();
    }

    public void clearAll(){
        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++){
                if(tab[i][j] !=0){
                    tab[i][j]=0;
                }
            }
        }
        seed=0;
        colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);put(0,Color.WHITE);}};
        colorsNotClear.clear();
        draw();
    }

    public void square(){
        inclusions=Integer.parseInt(number.getText());
        r=Integer.parseInt(rd.getText());
        type=1;
        tab = new Inclusions(xSize,ySize,inclusions,tab,r,type).generator();
        draw();

    }

    public void circle(){
        inclusions=Integer.parseInt(number.getText());
        r=Integer.parseInt(rd.getText());
        type=0;
        tab = new Inclusions(xSize,ySize,inclusions,tab,r,type).generator();
        draw();
    }

  public void convasClick(MouseEvent me){

      System.out.println(me.getX() - pane.getLayoutX());
      System.out.println(me.getY() - pane.getLayoutY());
      int x = (int) (me.getX() - pane.getLayoutX());
      int y = (int) (me.getY() - pane.getLayoutY());
      int  color = tab[y][x];
      if(colorsNotClear.contains(color)){
          colorsNotClear.remove(color);
      }else{
              colorsNotClear.add(color);
          }


         }

      public void dualPhase(){
          for (int i =0; i < ySize; i++){
              for (int j =0; j < xSize; j++){
                  if(colorsNotClear.contains(tab[i][j])) {
                      tab[i][j] = -2;
                  }

                  else if (tab[i][j]>0)
                       tab[i][j]=0;

              }
          }
            seed=0;
            colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);put(0,Color.WHITE);put(-2,Color.RED);}};
            colorsNotClear.clear();
        draw();
  }
  public void substructure(){
      for (int i =0; i < ySize; i++){
          for (int j =0; j < xSize; j++){
              if(colorsNotClear.contains(tab[i][j]) && tab[i][j] > 0) {
                  tab[i][j] = -tab[i][j]-1;
              }

              else if(tab[i][j] > 0){
                  tab[i][j]=0;
              }
          }
      }
    //  colorsNotClear.clear();
      colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);put(0,Color.WHITE);}};
      seed=0;
      draw();


  }

    public void homo(){
        GraphicsContext gc = pane.getGraphicsContext2D();
        homos=Integer.parseInt(homo.getText());
        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++){
                gc.setFill(Color.rgb(0,0,homos));
                gc.fillRect(i,j,1,1);
            }
        }
        }
    private boolean isOnBorder(int x , int y){
        List<Integer> n =
                Stream.of(a(x-1,y-1),a(x,y-1),a(x+1,y-1),a(x-1,y),a(x+1,y),a(x-1,y+1),a(x,y+1),a(x+1,y+1))
                        .filter(v->v!=null && v!=-1).collect(Collectors.toList());
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
    public void hetero(){
        GraphicsContext gc = pane.getGraphicsContext2D();
        ener = new int[ySize][xSize];
        homos=Integer.parseInt(homo.getText());
        heteros=Integer.parseInt(hetero.getText());
        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++){
                if((isOnBorder(j,i)))
                gc.setFill(Color.rgb(0,0,heteros));
                else{
                    gc.setFill(Color.rgb(0,0,homos));
                }
                gc.fillRect(i,j,1,1);

            }
        }

    }
    public void setEnergy(){

            draw();
    }
    void draw() {

        GraphicsContext gc = pane.getGraphicsContext2D();


        Random rand = new Random();

        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++) {
                   if(!colors.containsKey(tab[i][j])) {
                       Color color = Color.rgb((rand.nextInt(100)),(rand.nextInt(256)),rand.nextInt(256));
                       gc.setFill(color);
                       colors.put(tab[i][j], color);
                   }else{
                       gc.setFill(colors.get(tab[i][j]));
                   }
                   gc.fillRect(j * cSize, i * cSize, cSize, cSize);


            }
        }


    }


    public void save(){
        initializeFileChooserBmp();



        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());


        WritableImage wim = new WritableImage((int)pane.getWidth(), (int)pane.getHeight());


        pane.snapshot(null, wim);


        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(wim, null);
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,null);
        try {
            ImageIO.write(newBufferedImage, "BMP",  (file));

          //  byte[] res  = s.toByteArray();
         //   s.close(); //especially if you are using a different output stream.
         //   BufferedImage image = ImageIO.read( new ByteArrayInputStream( res) );
//            ImageIO.write(image, "BMP", new File("filename.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void saveString(){
        initializeFileChooserTxt();
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());
        InputOutput.save(tab,file);
    }

    public void seed(){

        seed=Integer.parseInt(seedinuput.getText());
        tab = new Seed(xSize,ySize,seed,tab).generator();
        draw();


    }

    public void GenerateMC(){
        seed=Integer.parseInt(seedinuput.getText());
        tab = new Seed(xSize,ySize,seed,tab).generatorMC();
        draw();
    }


    public void loadimage() {
        initializeFileChooserBmp();
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        try {
            List<Integer> colorMap = new ArrayList<>();
            BufferedImage bi =  ImageIO.read(file);
            tab = new int[ bi.getHeight()][bi.getWidth()];
            pane.setWidth(bi.getWidth());
            pane.setHeight(bi.getHeight());
            xSize = bi.getWidth();
            ySize = bi.getHeight();

            for(int y=0; y< bi.getHeight();++y){
                for(int x=0; x< bi.getWidth();++x) {
                    if(!colorMap.contains(bi.getRGB(x,y))) {
                        colorMap.add(bi.getRGB(x, y));
                        colors.put(colorMap.size(),rgbToColor(bi.getRGB(x, y)));
                    }
                    tab[y][x] = colorMap.indexOf(bi.getRGB(x,y)) +1;
                 }
            }
            seed = colorMap.size();
            draw();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadtxt(){
        initializeFileChooserTxt();
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        tab = InputOutput.loadTxt(file);
        pane.setWidth(tab.length);
        pane.setHeight(tab[0].length);
        xSize =tab[0].length;
        ySize = tab.length;
        colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);}};
        seed = getMaxValue(tab);
        draw();
    }

    public static int getMaxValue(int[][] numbers) {
        int maxValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {
            for (int i = 0; i < numbers[j].length; i++) {
                if (numbers[j][i] > maxValue) {
                    maxValue = numbers[j][i];
                }
            }
        }
        return maxValue;
    }


    private Color rgbToColor(int rgb){
        int alpha = (rgb >> 24) & 0xFF;
        int red =   (rgb >> 16) & 0xFF;
        int green = (rgb >>  8) & 0xFF;
        int blue =  (rgb      ) & 0xFF;
        return Color.rgb(red,green,blue);
    }

    private void initializeFileChooserBmp() {

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BMP", "*.bmp"));
        fileChooser.setInitialDirectory(new File("."));
    }

    private void initializeFileChooserTxt() {

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fileChooser.setInitialDirectory(new File("."));
    }


}

