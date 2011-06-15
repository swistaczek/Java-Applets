/*
 * To jest bardzo prosty demonstracyjny applet
 * Ernest Bursa
 */

package applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * A very simple example on how to write an Applet that can also be run as an Application
 * You can javac it (its main() method will be called) or
 * you can invoke it from a .html file in that case it will run as an Applet
 */
public class Applet extends JApplet implements ActionListener {

	JButton[] button;
	JLabel[] label;
        JLabel spacer;
        JLabel thread_label;
        JButton thread_button;
	int[] nbClick;
        
        // Metoda initializująca applet (JComponnet)
        // Zostanie wywołana automatycznie przy uruchomieniu appletu
        // Lub gdy program zostanie wykonany jako okienkowy wykona go metoda main()
	public void init() {
		// utworzenie przyicsków i przynależących etykiet
		button = new JButton[6];
		label = new JLabel[button.length];
		nbClick = new int[button.length];
                
                thread_button = new JButton("Odliczaj do 10");
                thread_button.addActionListener(this);
                thread_label = new JLabel("     ");
                
		// as a gridLayout of number of buttons X 2 for their corresponding labels]
		setLayout(new GridLayout(button.length+1,2));
                add(thread_button);
                add(thread_label);

		for(int i = 0; i < button.length; i++) {
			// utworzenie przycisku
			button[i] = new JButton("Przycisk #" + (i+1));
			button[i].addActionListener(this);
			add(button[i]);
			// i odpowiadającej etykiety
			label[i] = new JLabel("     Ilość kliknięć: 0");
			add(label[i]);
		}
	}
	
        // metoda wywołana w momencie przycisnięcia przycisku
        // wspomniane wcześniej nadpisanie metody actionPerformed()
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// wyszukujemy który przyisk został uruchomiony
		for(int i = 0; i < button.length; i++) {
			// przycisk znaleziony
			if(o == button[i]) {
				nbClick[i]++;		// zwiększenie ilości kliknięć
				label[i].setText("     Ilość kliknięć: " + nbClick[i]);
				break;
			}
		}
                
                if(o == thread_button){
                    System.out.println("test");
                    new SimpleThread(thread_label, thread_button).start();
                }
	}
	
	// kiedy kod uruchamiany jako aplikacja
	public static void main(String[] args) {
		JFrame frame = new JFrame("To jest aplikacja testowa");
		// w przypadku aplikacji należy ustawić rozmiar okna
                // applet pobierze rozmiary ze źródła
		frame.setSize(300, 500);
                frame.setBackground(Color.red);
		// utworzenie appletu
		Applet applet = new Applet();
		// wywołanie metody tworzącej gui
		applet.init();   
		// dodanie appletu do ramki i uaktywnienie go
		frame.add(applet, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
