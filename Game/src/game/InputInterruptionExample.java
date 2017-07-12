/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class InputInterruptionExample {

     UserInputThread userInputRunnable;
     Thread userInputThread;
     Thread interrupterThread;

    InputInterruptionExample() {
        this.userInputRunnable = new UserInputThread();
        this.userInputThread = new Thread(userInputRunnable);
        this.interrupterThread = new Thread(new InterrupterThread());
    }

    void startThreads() {
        this.userInputThread.start();
        this.interrupterThread.start();
    }
    private class UserInputThread implements Runnable {
        private InputStreamReader isr;
        private BufferedReader br;

        UserInputThread() {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
        }

        public void run() {
            try {
                System.out.println("enter your name: ");
                try{
                    String userInput = br.readLine();
                } catch(NullPointerException e) {}
            } catch (IOException e) {
                System.out.println("Oops..somethign went wrong.");
                System.exit(1);
            }
        }
        
        public void closeBufferdReader() {
            try {
                System.in.close();
            } catch (IOException e) {
                System.out.println("Oops..somethign went wrong in closeBufferdReader() method");
                System.exit(1);
            }
        }
    }
    private class InterrupterThread implements Runnable {
        public void run() {
            try {
                Thread.sleep(5000);
                userInputRunnable.closeBufferdReader();                 
                userInputThread.interrupt();
                userInputThread.join();
                System.out.println("Successfully interrupted");
            } catch (InterruptedException e) {}
        }
    }
    
    public static void main(String[] args) {
        InputInterruptionExample exampleApp = new InputInterruptionExample();
        exampleApp.startThreads();
    }
}
