/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applet;
import javax.swing.*;
/**
 *
 * @author Ernest
 */
public class SimpleThread extends Thread {
    String text;
    JLabel mainl;
    JButton buttonl;
    public SimpleThread(JLabel label, JButton button) {
        text = "Liczę";
        mainl = label;
        buttonl = button;
    }
    public void run() {
        buttonl.setEnabled(false);
	for (int i = 0; i < 10; i++) {
            mainl.setText("    "+ i + " " + text);
            try {
		sleep((int)(Math.random() * 1000));
	    } catch (InterruptedException e) {}
	}

        mainl.setText("    "+ "Koniec! ");
        buttonl.setEnabled(true);
    }
}
