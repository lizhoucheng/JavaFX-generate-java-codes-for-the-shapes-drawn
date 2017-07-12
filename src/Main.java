import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
 
/**
 * reference:http://java-buddy.blogspot.com/2013/04/free-draw-on-javafx-canvas.html
             https://www.tutorialspoint.com/java/java_filewriter_class.htm
 */
public class Main extends Application {
 
    double axisX, axisY, width, height, radius;
    
    static String importString = "import javafx.application.Application;\n"
            + "import javafx.application.Application.launch;\nimport javafx.event.ActionEvent;\n"
            + "import javafx.event.EventHandler;\nimport javafx.scene.Scene;\n"
            + "import javafx.scene.control.Button;\nimport javafx.scene.layout.Pane;\n"
            + "import javafx.scene.layout.StackPane;\nimport javafx.scene.paint.Color;\n"
            + "import javafx.scene.shape.*;\nimport javafx.stage.Stage;\n\n\n";

    static String beginingString = "public class ShowPic extends Application{ \n"
            + "\t@Override \n"
            + "\tpublic void start(Stage primaryStage){ \n"
            + "\t\tPane pane = new Pane(); \n";
    
    static String endString = "\t\tScene scene = new Scene(pane, 400, 400); \n"
            + "\t\tprimaryStage.setTitle(\"homework\"); \n"
            + "\t\tprimaryStage.setScene(scene); \n"
            + "\t\tprimaryStage.show(); \n }"
            + "\tpublic static void main(String[] args) { \n"
            + "\t\tlauch(args); \n"
            + "}}\n";
    
    String lineCode="", rectCode="", circleCode="";
    int lineCount=0, rectCount=0, circleCount=0;

    EventHandler<MouseEvent> usingPressed;
    EventHandler<MouseEvent> usingReleased;
    
    @Override
    public void start(Stage primaryStage) {
 
        Canvas canvas = new Canvas(400, 400);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);
        
        Button generateButton = new Button("Generate");
        generateButton.setTranslateX(-150);
        generateButton.setTranslateY(150);
        Button finishButton = new Button("Finish");
        finishButton.setVisible(false);
        finishButton.setTranslateX(120);
        finishButton.setTranslateY(150);
        Button lineButton = new Button("Line");
        lineButton.setTranslateX(120);
        lineButton.setTranslateY(150);
        Button rectButton = new Button("Rectangle");
        rectButton.setTranslateX(20);
        rectButton.setTranslateY(150);
        Button circleButton = new Button("Circle");
        circleButton.setTranslateX(-70);
        circleButton.setTranslateY(150);
        
        EventHandler<MouseEvent> drawCirclePressed = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                axisX=event.getX();
                axisY=event.getY();
            }
        };
        
        EventHandler<MouseEvent> drawCircleReleased = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                radius=Math.pow(Math.pow(event.getX()-axisX,2) + Math.pow(event.getY()-axisY,2),0.5);
                double centerX=axisX;
                double centerY=axisY;
                double x=axisX-radius;
                double y=axisY-radius;
                graphicsContext.strokeOval(x,y,radius*2,radius*2);
                
                circleCount++;
                String name = "circle"+circleCount;
                circleCode += "\n\t\tCircle "+name+" = new Circle();\n"+
                        "\t\t"+name+".setCenterX("+centerX+");\n"+
                        "\t\t"+name+".setCenterY("+centerY+");\n"+
                        "\t\t"+name+".setRadius("+radius+");\n"+
                        "\t\tpane.getChildren().add("+name+"); \n\n";
            }
        };
         
        EventHandler<MouseEvent> drawRectPressed = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                axisX=event.getX();
                axisY=event.getY();
            }
        };
        
        EventHandler<MouseEvent> drawRectReleased = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
               width=event.getX()-axisX;
               height=event.getY()-axisY;
               graphicsContext.strokeRect(axisX, axisY, width, height);
               
               rectCount++;
               String name = "rect"+rectCount;
               rectCode += "\n\t\tRectangle "+name+" = new Rectangle("+axisX+","+axisY+","+width+","+height+");\n"+
                       "\t\tpane.getChildren().add("+name+"); \n\n";
            }
        };
        
        EventHandler<MouseEvent> drawLinePressed = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                axisX = event.getX();
                axisY = event.getY();
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        };
        
        EventHandler<MouseEvent> drawLineReleased = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
                
                lineCount++;
                String name = "line"+lineCount;
                lineCode = "\n\t\tLine "+name+" = new Line("+axisX+","+axisY+","+event.getX()+","+event.getY()+");\n"+
                        "\t\tpane.getChildren().add("+name+"); \n\n";
            }
        };
        
        lineButton.setOnAction(value -> {
            finishButton.setVisible(true);
            lineButton.setVisible(false);
            rectButton.setVisible(false);
            circleButton.setVisible(false);
            generateButton.setVisible(false);
            
            usingPressed = drawLinePressed;
            usingReleased = drawLineReleased;
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, usingPressed);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, usingReleased);
        });
        
        rectButton.setOnAction(value ->{
            finishButton.setVisible(true);
            lineButton.setVisible(false);
            rectButton.setVisible(false);
            circleButton.setVisible(false);
            generateButton.setVisible(false);
            
            usingPressed = drawRectPressed;
            usingReleased = drawRectReleased;
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, usingPressed);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, usingReleased);
        });
        
        circleButton.setOnAction(value ->{
            finishButton.setVisible(true);
            lineButton.setVisible(false);
            rectButton.setVisible(false);
            circleButton.setVisible(false);
            generateButton.setVisible(false);
            
            usingPressed = drawCirclePressed;
            usingReleased = drawCircleReleased;
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, usingPressed);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, usingReleased);
        });
        
        finishButton.setOnAction(value ->{
            finishButton.setVisible(false);
            lineButton.setVisible(true);
            rectButton.setVisible(true);
            circleButton.setVisible(true);
            generateButton.setVisible(true);
            
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, usingPressed);
            canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED,usingReleased);
        });
        
        generateButton.setOnAction(value ->{
            try{
            File file = new File("hw.java");
            file.createNewFile();
            
            FileWriter writer = new FileWriter(file);
            writer.write(importString+beginingString+circleCode+rectCode+lineCode+endString);
            writer.flush();
            writer.close();
            System.out.println("it is done");
            }catch (IOException e){
            e.printStackTrace();
        }
        });
        
 
        /*canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
 
            }
        });*/
 
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        root.getChildren().add(lineButton);
        root.getChildren().add(rectButton);
        root.getChildren().add(circleButton);
        root.getChildren().add(finishButton);
        root.getChildren().add(generateButton);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("CSCE 314 Javafx Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);  
    }
     
    private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
         
        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
 
        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
         
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
         
    }
     
}

