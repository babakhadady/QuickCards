package ui;

import model.FlashCardSet;
import javax.swing.*;
import java.awt.*;

// represents JPanel with a pie graph of flashcard set
public class Graph extends JPanel {

    private FlashCardSet flashCardSet;
    private double arc;
    private double start;

    // EFFECTS: Creates pie graph with labels, sets flashcard set
    public Graph(FlashCardSet flashCardSet) {
        super();
        add(new JLabel("Orange: Not Mastered FlashCards"));
        add(new JLabel("Red: Mastered FlashCards"));
        this.flashCardSet = flashCardSet;
    }

    // MODIFIES: this
    // EFFECTS: sets up graph corresponding to flashcard set
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                arc = (double) flashCardSet.notMasteredSet().size() / (double) flashCardSet.getSize() * 360;
                gr.setColor(Color.ORANGE);
                gr.fillArc(75, 75, 150, 150, 0, (int) arc);
                start = arc;
            } else {
                double size = (double) flashCardSet.getSize() - (double) flashCardSet.notMasteredSet().size();
                arc = size / (double) flashCardSet.getSize() * 360;
                gr.setColor(Color.RED);
                gr.fillArc(75, 75, 150, 150, (int) start, (int) arc);
            }
        }
    }
}