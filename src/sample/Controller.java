package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

    @FXML
    private Canvas pane;
    private final int cSize = 1;
    private Map<Integer,Color>    colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);put(0,Color.WHITE);}};
    private int[][] tab;
    private FileChooser fileChooser;
    int xSize;
    int ySize;

    int seed;
    int inclusions;
    int r;
    int type;
    int percentInt;
    int iter;
  //  private Pane space;

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

    public void clear(){
        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++){
                if(tab[i][j]!= -1){
                    tab[i][j]=0;
                }
            }
        }
        seed=0;
        colors = new HashMap<Integer,Color>(){{put(-1,Color.BLACK);put(0,Color.WHITE);}};
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


    void draw() {

        GraphicsContext gc = pane.getGraphicsContext2D();


        Random rand = new Random();

        for (int i =0; i < ySize; i++){
            for (int j =0; j < xSize; j++) {
                   if(!colors.containsKey(tab[i][j])) {
                       Color color = Color.rgb(rand.nextInt(100),(rand.nextInt(256)),rand.nextInt(256));
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

