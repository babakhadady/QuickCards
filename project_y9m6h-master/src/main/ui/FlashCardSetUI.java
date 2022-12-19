package ui;


import model.FlashCardSet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


// represents gui to run a flashcard set
public class FlashCardSetUI extends JFrame {

    private int index;
    private FlashCardSet flashCardSet;
    private JTextField textField;
    private JPanel panel;
    private JButton button;

    // EFFECTS: sets up run gui for flashcard set
    public FlashCardSetUI(FlashCardSet flashCardSet) {
        super(flashCardSet.getName());
        setSize(250, 250);
        setVisible(true);

        this.flashCardSet = flashCardSet;
        index = 0;
        setUpPanelQ();
    }

    // MODIFIES: this
    // EFFECTS: sets up question panels for gui of flashcard set
    private void setUpPanelQ() {

        if (index > flashCardSet.getSize() - 1) {
            setVisible(false);
        } else {
            checkPanel();
            panel = new JPanel();
            textField = new JTextField(10);
            JLabel label = new JLabel("Question: " + flashCardSet.getFlashCard(index).getQuestion());
            add(panel);
            panel.add(label);
            panel.add(textField);
            panel.addMouseListener(new CardMouseListener(0));
            revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: if panel is not null it removes panel from JFrame, revalidates and repaints JFrame
    private void checkPanel() {
        if (panel != null) {
            remove(panel);
            revalidate();
            repaint();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up answer panels for flashcard set gui
    private void setUpPanelA() {
        remove(panel);
        revalidate();
        repaint();
        panel = new JPanel();

        add(panel);
        panel.add(new JLabel("Answer: " + flashCardSet.getFlashCard(index).getAnswer()));
        panel.add(new JLabel("Your Answer: " + textField.getText()));
        button = new JButton("Master FlashCard");
        panel.add(button);
        button.addActionListener(new ButtonAction(index));
        panel.addMouseListener(new CardMouseListener(1));
        index++;
        revalidate();

    }

    // MouseListener for card JPanel
    private class CardMouseListener implements MouseListener {
        private int index;

        // EFFECTS: sets this.i as i (side of flashcard currently on)
        public CardMouseListener(int index) {
            this.index = index;
        }

        // MODIFIES: this
        // EFFECTS: sets up next panel on mouse click based on side of flashcard
        @Override
        public void mouseClicked(MouseEvent e) {
            if (index == 0) {
                setUpPanelA();
            } else {
                setUpPanelQ();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    // ActionListener for flashcard mastery
    private class ButtonAction implements ActionListener {
        private int index;

        // EFFECTS: sets current index of flashcard currently active
        public ButtonAction(int index) {
            this.index = index;
        }

        // MODIFIES: this
        // EFFECTS: sets flashcard as mastered when button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            flashCardSet.getFlashCard(index).setMastered();
            panel.add(new JLabel("Flashcard Mastered!"));
            revalidate();
        }
    }
}


