package ui;

import model.FlashCard;
import model.FlashCardSet;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// represents gui for editing a flash card set
public class FlashCardSetEdit extends JFrame {

    private FlashCardSet flashCardSet;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JScrollPane scrollPane;
    private JSplitPane splitPane;
    private JButton delete;
    private JButton edit;
    private JButton add;
    private JButton master;
    private JLabel question;
    private JLabel answer;
    private JLabel mastered;
    private JList<String> list;


    // EFFECTS: sets up flashcardset edit gui
    public FlashCardSetEdit(FlashCardSet editSet) {
        super(editSet.getName() + " Set");

        flashCardSet = editSet;

        setSize(400, 300);
        setVisible(true);

        leftPanel = new JPanel(new GridLayout(0, 2));
        rightPanel = new JPanel(new GridLayout(2, 1));

        add(leftPanel, BorderLayout.NORTH);

        splitPane = new JSplitPane();

        setLists();

        add(splitPane);
        splitPane.setEnabled(false);

        splitPane.setRightComponent(rightPanel);

        topPanel = new JPanel(new GridLayout(4, 0));
        bottomPanel = new JPanel(new GridLayout(4, 0));

        rightPanel.add(topPanel);
        rightPanel.add(bottomPanel);

        setLabels();
        setHeadings();
        setButtons();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: sets labels for top right panel
    private void setLabels() {
        question = new JLabel();
        answer = new JLabel();
        mastered = new JLabel();
        topPanel.add(question);
        topPanel.add(answer);
        topPanel.add(mastered);
        cardLabels(null);
    }

    // MODIFIES: this
    // EFFECTS: sets buttons for bottom right panel
    private void setButtons() {
        edit = new JButton("Edit Selected FlashCard");
        delete = new JButton("Delete Selected FlashCard");
        add = new JButton("Add Flashcard to Set");
        master = new JButton("Update Selected Flashcard Mastered");

        add.addActionListener(new AddAction());
        edit.addActionListener(new EditAction());
        delete.addActionListener(new DeleteAction());
        master.addActionListener(new MasterAction());

        bottomPanel.add(add);
        bottomPanel.add(edit);
        bottomPanel.add(master);
        bottomPanel.add(delete);
    }

    // MODIFIES: this
    // EFFECTS: sets JScrollPane with lists of flashcard questions
    private void setLists() {
        try {
            list = new JList<>(makeNamesList(flashCardSet.getFlashCardList()));
        } catch (RuntimeException e) {
            list = new JList<>();
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(list);

        splitPane.setLeftComponent(scrollPane);
        Dimension d = list.getPreferredSize();
        d.width = 100;
        scrollPane.setPreferredSize(d);
        list.addListSelectionListener(new ListListener());
    }

    // MODIFIES: this
    // EFFECTS: creates a String[] creating all flashcard questions
    //          throws RuntimeException if no flashcards exist
    private String[] makeNamesList(List<FlashCard> flashCards) {
        if (flashCards.isEmpty()) {
            throw new RuntimeException();
        }
        String[] names = new String[flashCards.size()];

        int i = 0;
        for (FlashCard flashCard : flashCards) {
            names[i] = " " + flashCard.getQuestion();
            i++;
        }
        return names;
    }

    // MODIFIES: this
    // EFFECTS: sets card labels for given flashcard
    public void cardLabels(FlashCard flashCard) {

        if (flashCard == null) {
            question.setText(" Question: ");
            answer.setText(" Answer: ");
            mastered.setText(" Mastered: ");
        } else {
            question.setText(" Question: " + flashCard.getQuestion());
            answer.setText(" Answer: " + flashCard.getAnswer());
            mastered.setText(" Mastered: " + flashCard.isMastered());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets headings for flashcard set edit gui
    public void setHeadings() {
        JLabel label = new JLabel("    FlashCards");
        JLabel label2 = new JLabel("   Flashcard Options");
        leftPanel.add(label);
        leftPanel.add(label2);
    }


    // action listener for list
    private class ListListener implements javax.swing.event.ListSelectionListener {

        // MODIFIES: this
        // EFFECTS: updates card labels based on selected set in list
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = list.getSelectedIndex();
            FlashCard selected = flashCardSet.getFlashCard(index);
            cardLabels(selected);
        }
    }

    // action listener for add flashcard
    private class AddAction implements ActionListener {

        // MODIFIES: this
        // EFFECTS: initializes add flashcard ui
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddActionUI();

        }
    }

    // action listener for delete flashcard
    private class DeleteAction implements ActionListener {

        // MODIFIES: this
        // EFFECTS: initializes delete flashcard ui if
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedIndex() != -1) {
                new DeleteActionUI(list.getSelectedIndex());
            }
        }
    }

    // action listener for master flashcard
    private class MasterAction implements ActionListener {

        // MODIFIES: this
        // EFFECTS: if a flashcard is selected, updates its flashcard mastery and sets lists
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedIndex() != -1) {
                int index = list.getSelectedIndex();
                FlashCard flashCard = flashCardSet.getFlashCard(index);
                flashCard.updateMastered();
                setLists();
                mastered.setText(" Updated Mastered: " + flashCard.isMastered());
                list.setSelectedIndex(index);
            }
        }
    }

    // action listener for edit flashcard
    private class EditAction implements ActionListener {

        // MODIFIES: this
        // EFFECTS: if a flashcard is selected initializes edit flashcard set ui
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedIndex() != -1) {
                new EditSetUI(list.getSelectedIndex());
            }

        }
    }

    // UI for add flashcard set
    public class AddActionUI extends JFrame {
        private static final int WIDTH = 300;
        private static final int HEIGHT = 100;

        private JTextField question;
        private JTextField answer;
        private JLabel questionLabel;
        private JLabel answerLabel;

        // EFFECTS: sets up add entire flashcard gui
        public AddActionUI() {
            super("Add a FlashCard");
            setSize(WIDTH, HEIGHT);

            JPanel newPanel = new JPanel(new GridLayout(3, 2));
            add(newPanel);
            question = new JTextField(10);
            answer = new JTextField(10);

            questionLabel = new JLabel("Question:");
            answerLabel = new JLabel("Answer:");

            newPanel.add(questionLabel);
            newPanel.add(question);
            newPanel.add(answerLabel);
            newPanel.add(answer);

            JPanel spacing = new JPanel();
            newPanel.add(spacing);
            JButton add = new JButton("Add FlashCard");
            newPanel.add(add);

            add.addActionListener(new AddFlashcardAction());

            setVisible(true);
        }

        // action listener for add flashcard button
        private class AddFlashcardAction implements ActionListener {

            // MODIFIES: this
            // EFFECTS: creates a new flashcard based on textfields, sets gui visible as false and setLists
            @Override
            public void actionPerformed(ActionEvent e) {
                String q = question.getText();
                String a = answer.getText();

                flashCardSet.addFlashCard(new FlashCard(q, a));

                int index = list.getSelectedIndex();

                setVisible(false);
                setLists();
                cardLabels(flashCardSet.getFlashCard(index + 1));
                list.setSelectedIndex(index + 1);
            }
        }
    }

    // gui for delete flashcard action
    private class DeleteActionUI extends JFrame {

        private static final int WIDTH = 450;
        private static final int HEIGHT = 200;

        private int index;
        private FlashCard flashCard;

        // EFFECTS: sets up delete flashcard gui
        public DeleteActionUI(int index) {
            super("Delete FlashCard");

            this.index = index;

            setSize(WIDTH, HEIGHT);

            JPanel newPanel = new JPanel(new GridLayout(4,1));
            add(newPanel);
            JLabel deleteSetText = new JLabel("Would you like to delete the flashcard: ");
            newPanel.add(deleteSetText);

            flashCard = flashCardSet.getFlashCard(index);
            JLabel question = new JLabel("Question: " + flashCard.getQuestion());
            JLabel answer = new JLabel("Answer: " + flashCard.getAnswer());

            newPanel.add(question);
            newPanel.add(answer);

            Panel buttonPanel = new Panel();
            newPanel.add(buttonPanel);
            JButton yesButton = new JButton("Yes");
            JButton noButton = new JButton("No");

            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);

            noButton.addActionListener(new NoActionListener());
            yesButton.addActionListener(new YesActionListener());

            setVisible(true);
        }

        // action listener for yes button
        private class YesActionListener implements ActionListener {

            // MODIFIES: this
            // EFFECTS: deletes flashcard at given index, setsLists and hides delete gui
            @Override
            public void actionPerformed(ActionEvent e) {
                flashCardSet.removeFlashCard(index);
                setLists();
                setVisible(false);
            }
        }

        // action listener for no button
        private class NoActionListener implements ActionListener {

            // MODIFIES: this
            // EFFECTS: hides delete gui
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        }
    }


    // gui for edit flashcard set
    private class EditSetUI {
        private static final int WIDTH = 300;
        private static final int HEIGHT = 75;

        private FlashCard flashCard;
        private JFrame editFrame;
        private JPanel buttonPanel;
        private JButton question;
        private JButton answer;
        private int selectedIndex;

        // EFFECTS: sets up gui for edit set
        public EditSetUI(int selectedIndex) {
            editFrame = new JFrame("FlashCard Edit");
            editFrame.setSize(WIDTH, HEIGHT);
            editFrame.setVisible(true);

            flashCard = flashCardSet.getFlashCard(selectedIndex);
            this.selectedIndex = selectedIndex;


            buttonPanel = new JPanel(new GridLayout(0, 2));
            question = new JButton("Question");
            answer = new JButton("Answer");
            editFrame.add(buttonPanel);

            question.addActionListener(new QuestionButtonAction());
            answer.addActionListener(new AnswerButtonAction());
            buttonPanel.add(question);
            buttonPanel.add(answer);

            editFrame.revalidate();
        }

        // MODIFIES: this
        // EFFECTS: sets gui for question / answer based on user input
        private void setFrame(String action) {
            editFrame.setTitle(action + " Edit");
            buttonPanel.remove(question);
            buttonPanel.remove(answer);
            buttonPanel.revalidate();
            buttonPanel.repaint();

            JTextField textField = new JTextField(10);
            JButton button = new JButton("Change " + action);
            button.addActionListener(new ButtonAction(action, textField));
            buttonPanel.add(textField);
            buttonPanel.add(button);
        }

        // action listener for question select
        private class QuestionButtonAction implements ActionListener {

            // MODIFIES: this
            // EFFECTS: sets frame for question
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrame("Question");

            }
        }

        // action listener for answer select
        private class AnswerButtonAction implements ActionListener {

            // MODIFIES: this
            // EFFECTS: sets frame for answer
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrame("Answer");
            }
        }

        // action listener for confirm button
        private class ButtonAction implements ActionListener {
            private String action;
            private JTextField textField;

            // EFFECTS: sets given text field and action
            public ButtonAction(String action, JTextField textField) {
                this.textField = textField;
                this.action = action;
            }

            // MODIFIES: this
            // EFFECTS: overrides question or answer depending on previous selection, calls setLists and hides edit gui
            @Override
            public void actionPerformed(ActionEvent e) {
                String update = textField.getText();
                if (action.equals("Question")) {
                    flashCard.setQuestion(update);
                } else {
                    flashCard.setAnswer(update);
                }
                editFrame.setVisible(false);
                cardLabels(flashCard);
                setLists();
                list.setSelectedIndex(selectedIndex);


            }
        }
    }
}
