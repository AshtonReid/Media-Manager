import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;

public class Main extends JFrame {
  public static void main(String[] args) {
    new Main(); // Runs the GUI
  }
  public Main() {
    setSize(600, 400);
    setLayout(null);
    Container c = getContentPane();
    c.setBackground(new Color(0, 0, 0, 5));
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}