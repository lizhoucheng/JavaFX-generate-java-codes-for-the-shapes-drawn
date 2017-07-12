/*
This program should run perfectly
Name:Zhoucheng Li
UIN: 924008460
Reference: https://stackoverflow.com/questions/20477978/interrupt-a-thread-waiting-for-user-input-and-then-exit-the-app
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Game {
    
    TimeThread timeThread = new TimeThread();
    InputThread inputThread = new InputThread();
    Thread timeThreadRunnable = new Thread(timeThread);
    Thread inputThreadRunnable = new Thread(inputThread);
    
    boolean isPassed;
    String userInput;
    static boolean continued = true;
    static String answer = "";
    
    public void start(){
        timeThreadRunnable.start();
        inputThreadRunnable.start();
    }
    
    public void join() throws InterruptedException{
        timeThreadRunnable.join();
        inputThreadRunnable.join();
    }

    public class TimeThread implements Runnable{
        @Override
        public void run(){
            try {
                
                isPassed = false;
                Thread.sleep(10000);
/*                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                //inputThread.closeBufferReader();
                //inputThreadRunnable.interrupt();
                //inputThreadRunnable.join();*/
                isPassed = true;
                System.out.println("Times up! Please press Enter to check");
                
            } catch (InterruptedException ex) {
                //System.out.println("receive answer");
                //Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    private class InputThread implements Runnable{
        
        //private InputStreamReader isr;
        //private BufferedReader br;
        

        InputThread() {
            
            
           //Scanner s = new Scanner(System.in);
            //if(s.nextLine()!="") userInput = s.nextLine();
        }

        @Override
        public void run() {
            //System.out.println("enter your name: ");
            try{
                //InputStreamReader isr = new InputStreamReader(System.in);
                //BufferedReader br = new BufferedReader(isr);
                Scanner s = new Scanner(System.in);
                userInput = s.nextLine();
                
                if(isPassed)
                    System.out.println(""); //no function
                else{
                    timeThreadRunnable.interrupt();
                    answer = userInput;
                }
            } catch(NullPointerException e) {}
        }
        
        public void closeBufferReader() {
            try {
                System.in.close();
            } catch (IOException e) {
                System.out.println("Oops..somethign went wrong in closeBufferdReader() method");
                System.exit(1);
            }
        }
    }
    
    public static void main(String[] args) throws AWTException, InterruptedException {
        
        String begining = "you have 10 sec for each question. the game will begin in 3 sec";
        String q1 = "\n1 + 0 = ?";
        String q2 = "\n1 + 2 = ?";
        String q3 = "\n1 + 3 = ?";
        int score = 0;
        
        System.out.println(begining);
        
        Thread.sleep(3000);
        
        System.out.println(q1);
        Game round1 = new Game();
        round1.start();
        round1.join();
        
        if(answer.equals("1"))
            System.out.println("right\nScore: "+(++score));
        else
            System.out.println("wrong or no input\ncorrect answer: 1\nScore: "+score);
        Thread.sleep(3000);
        System.out.println(q2);
        Game round2 = new Game();
        round2.start();
        round2.join();
        
        continued = false; //last question
        if(answer.equals("3"))
            System.out.println("right\nScore: "+(++score));
        else
            System.out.println("wrong or no input\ncorrect answer: 3\nScore: "+score);

        Thread.sleep(3000);
        System.out.println(q3);
        Game round3 = new Game();
        round3.start();
        round3.join();
        
        if(answer.equals("4"))
            System.out.println("right\nScore: "+(++score));
        else
            System.out.println("wrong or no input\ncorrect answer: 4\nScore: "+score);
    }
    
}


