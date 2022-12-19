package ui;


import model.Event;
import model.EventLog;
import model.FlashCardSet;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

// GUI for flashcard Application
public class FlashCardApp {

    private static final String JSON_STORE = "./data/flashcardset.json";

    private JFrame frame;
    private JLabel label;
    private JLabel label2;
    private JPanel topPanel;
    private JPanel rightPanel;
    private JSplitPane splitPane;
    private JScrollPane scrollPane;
    private JList<String> list;
    private JButton edit;
    private JButton rename;
    private JButton run;
    private JButton delete;

    private JsonWriter writer;
    private JsonReader reader;
    private List<FlashCardSet> sets;


    // EFFECTS: sets up gui containing all FlashCard Sets
    public FlashCardApp() {
        init();
        setSplitPane();
        setLists();
        createMenu();
        setButtons();
        setLabels();
        frame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: initializes sets and reader/writer, sets up JFrame
    private void init() {
        sets = new ArrayList<>();
        frame = new JFrame("FlashCard Application");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);

                }
            }
        });
        frame.setVisible(true);
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: sets up SplitPane
    public void setSplitPane() {
        splitPane = new JSplitPane();
        frame.add(splitPane);
        splitPane.setEnabled(false);
        splitPane.setRightComponent(rightPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up scroll pane with all flashcard sets
    public void setLists() {
        try {
            list = new JList<>(makeNamesList(sets));
        } catch (RuntimeException e) {
            list = new JList<>();
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(list);
        splitPane.setLeftComponent(scrollPane);
        Dimension d = list.getPreferredSize();
        d.width = 100;
        scrollPane.setPreferredSize(d);
        scrollPane.setPreferredSize(d);
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons for a flashcard set
    public void setButtons() {
        rightPanel = new JPanel(new GridLayout(2, 2));
        splitPane.setRightComponent(rightPanel);

        rename = new JButton("Rename Set");
        rename.addActionListener(new NameAction());

        edit = new JButton("Edit Set");
        edit.addActionListener(new EditAction());

        run = new JButton("Run Set");
        run.addActionListener(new RunAction());

        delete = new JButton("Delete Set");
        delete.addActionListener(new DeleteAction());

        rightPanel.add(rename);
        rightPanel.add(edit);
        rightPanel.add(run);
        rightPanel.add(delete);
        rightPanel.setSize(300, 400);

    }

    // MODIFIES: this
    // EFFECTS: sets top labels of gui
    public void setLabels() {
        topPanel = new JPanel(new GridLayout(1, 2));
        frame.add(topPanel, BorderLayout.NORTH);

        label = new JLabel("  FlashCard Sets");
        topPanel.add(label);

        label2 = new JLabel("      Set Options");
        topPanel.add(label2);
    }


    // MODIFIES: this
    // EFFECTS: creates JMenuBar with file and edit
    public void createMenu() {
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);

        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        menu.add(file);

        file.add(addMenuItem('S', new SaveFileAction()));
        file.add(new JSeparator());
        file.add(addMenuItem('O', new OpenFileAction()));


        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('E');
        menu.add(edit);

        edit.add(addMenuItem('C', new AddFileAction()));
        edit.add(new JSeparator());
        edit.add(addMenuItem('D', new DeleteAllAction()));
        edit.add(new JSeparator());
        edit.add(addMenuItem('C', new MasteredSetAction()));
        edit.add(new JSeparator());
        edit.add(addMenuItem('C', new GraphAction()));


    }

    // MODIFIES: this
    // EFFECTS: if sets is empty, throws RuntimeException
    //          else creates String[] with Flashcard Set names in sets
    public String[] makeNamesList(List<FlashCardSet> sets) {
        if (sets.isEmpty()) {
            throw new RuntimeException();
        }
        String[] names = new String[sets.size()];

        int i = 0;
        for (FlashCardSet flashCardSet : sets) {
            names[i] = " " + flashCardSet.getName();
            i++;
        }
        return names;
    }


    // MODIFIES: this
    // EFFECTS: creates new JMenuItem with given arguments
    public JMenuItem addMenuItem(char c, AbstractAction abstractAction) {
        JMenuItem menuItem = new JMenuItem(abstractAction);
        menuItem.setMnemonic(c);
        return menuItem;
    }

    // abstract action for save file
    private class SaveFileAction extends AbstractAction {

        JFrame success;

        // EFFECTS: creates save file action with  name
        public SaveFileAction() {
            super("Save Current FlashCards");
        }

        // MODIFIES: this
        // EFFECTS: saves all flashcard sets to file, throw FileNotFoundException if not able to save
        @Override
        public void actionPerformed(ActionEvent e) {

            success = new JFrame();
            success.setSize(300, 100);
            success.setVisible(true);
            JPanel panel = new JPanel();
            success.add(panel);
            try {
                writer.open();
                writer.write(sets);
                writer.close();

                panel.add(new JLabel("Saved all sets to " + JSON_STORE));
                JButton successButton = new JButton("Ok");
                successButton.addActionListener(new SuccessAction());
                panel.add(successButton);
            } catch (FileNotFoundException ex) {
                panel.add(new JLabel("Unable to save sets to " + JSON_STORE));
                JButton successButton = new JButton("Ok");
                successButton.addActionListener(new SuccessAction());
                panel.add(successButton);
            }
        }

        // success button for save
        private class SuccessAction implements ActionListener {

            // MODIFIES: this
            // EFFECTS: hides success gui
            @Override
            public void actionPerformed(ActionEvent e) {
                success.setVisible(false);
            }
        }
    }

    // abstract action for open file
    private class OpenFileAction extends AbstractAction {
        private JFrame edit;

        // EFFECTS: creates open file action with name
        public OpenFileAction() {
            super("Load Previous Save");
        }

        // MODIFIES: this
        // EFFECTS: opens save from file and adds it to sets, throws IOException if unable to load from file
        @Override
        public void actionPerformed(ActionEvent e) {

            edit = new JFrame();
            edit.setSize(300, 100);
            edit.setVisible(true);
            JPanel panel = new JPanel();
            edit.add(panel);

            try {
                List<FlashCardSet> newSets = reader.read();
                sets.addAll(newSets);
                setLists();
                list.setSelectedIndex(sets.size() - 1);

                panel.add(new JLabel("Loaded sets from " + JSON_STORE));
                JButton successButton = new JButton("Ok");
                successButton.addActionListener(new LoadFileAction());
                panel.add(successButton);
            } catch (IOException ex) {
                panel.add(new JLabel("Unable to load from " + JSON_STORE));
                JButton successButton = new JButton("Ok");
                successButton.addActionListener(new LoadFileAction());
                panel.add(successButton);
            }

        }

        // button for load file
        private class LoadFileAction implements ActionListener {

            // MODIFIES: this
            // EFFECTS: hides open file gui
            @Override
            public void actionPerformed(ActionEvent e) {
                edit.setVisible(false);
            }
        }
    }

    // abstract action for rename set
    private class NameAction extends AbstractAction {

        // MODIFIES: this
        // EFFECTS: if flashcard is selected initializes rename gui
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

            if (index != -1) {
                FlashCardSet renameSet = sets.get(index);
                new RenameSetUI(renameSet);
            }
        }
    }

    // abstract action for delete set
    private class DeleteAction extends AbstractAction {

        // MODIFIES: this
        // EFFECTS: if flashcard is selected initializes delete gui
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

            if (index != -1) {
                new DeleteSetUI(index);
            }
        }
    }

    // abstract action for run set
    private class RunAction extends AbstractAction {

        // MODIFIES: this
        // EFFECTS: if flashcard is selected initializes run gui
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

            if (index != -1) {
                new FlashCardSetUI(sets.get(index));
            }
        }
    }


    // abstract action for edit set
    private class EditAction extends AbstractAction {

        // MODIFIES: this
        // EFFECTS: if flashcard is selected initializes edit gui
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

            if (index != -1) {
                FlashCardSet editSet = sets.get(index);
                new FlashCardSetEdit(editSet);
            }
        }
    }

    // abstract action for add file
    private class AddFileAction extends AbstractAction {

        // EFFECTS: creates add file action with name
        public AddFileAction() {
            super("Create New FlashCard Set");
        }

        // MODIFIES: this
        // EFFECTS: initializes add file gui
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddFileUI();
        }
    }

    // abstract action for mastery set
    private class MasteredSetAction extends AbstractAction {

        // EFFECTS: creates master set action with name
        public MasteredSetAction() {
            super("Not Mastered From Selected Set");
        }

        // MODIFIES: this
        // EFFECTS: if flashcard is selected initializes master set gui
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedIndex() != -1) {
                new MasterSetUI(list.getSelectedIndex());
            }
        }
    }

    // abstract action for delete all sets
    private class DeleteAllAction extends AbstractAction {

        // EFFECTS: creates delete all action with name
        public DeleteAllAction() {
            super("Delete All FlashCard Sets");
        }

        // MODIFIES: this
        // EFFECTS: initializes delete all gui
        @Override
        public void actionPerformed(ActionEvent e) {
            new DeleteAllUI();
        }
    }

    // abstract action for graph set
    private class GraphAction extends AbstractAction {

        // EFFECTS: creates graph action with name
        public GraphAction() {
            super("Generate Selected Graph");
        }

        // MODIFIES: this
        // EFFECTS: if flashcard is selected creates JFrame with GraphUI
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if (index != -1) {
                JFrame newFrame = new JFrame("Graph of " + sets.get(index).getName() + " Mastery");
                newFrame.setSize(300, 300);
                Graph pie = new Graph(sets.get(index));
                newFrame.add(pie);
                newFrame.setVisible(true);
                newFrame.revalidate();
                newFrame.repaint();
            }
        }
    }

    // gui for delete set
    public class DeleteSetUI extends JFrame {
        private static final int WIDTH = 450;
        private static final int HEIGHT = 100;

        private int index;

        // EFFECTS: creates gui for delete set of selected index
        public DeleteSetUI(int index) {
            super("Delete FlashCard Set");

            this.index = index;

            setSize(WIDTH, HEIGHT);
            JPanel newPanel = new JPanel(new GridLayout(3, 0));
            add(newPanel);
            JLabel deleteSetText = new JLabel(" Would you like to delete the flashcard set:   "
                    + sets.get(index).getName());
            newPanel.add(deleteSetText);

            JButton noButton = new JButton("No");
            JButton yesButton = new JButton("Yes");

            newPanel.add(yesButton);
            newPanel.add(noButton);

            yesButton.addActionListener(new YesActionListener());
            noButton.addActionListener(new NoActionListener());
            setVisible(true);
        }

        // action listener for yes button
        private class YesActionListener implements ActionListener {

            // MODIFIES: this
            // EFFECTS: removes set at index, hides delete set gui and setLists()
            @Override
            public void actionPerformed(ActionEvent e) {
                sets.remove(index);
                setVisible(false);
                setLists();
            }
        }

        // MODIFIES: this
        // EFFECTS: hides delete set gui
        private class NoActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        }
    }

    // gui for rename set
    public class RenameSetUI extends JFrame {
        private static final int WIDTH = 320;
        private static final int HEIGHT = 100;

        private JTextField textField;
        private String text;
        private FlashCardSet flashCardSet;

        // EFFECTS: creates rename set gui for given flashcard
        public RenameSetUI(FlashCardSet flashCardSet) {
            super("Rename FlashCard Set");
            setSize(WIDTH, HEIGHT);

            JPanel newPanel = new JPanel();
            add(newPanel);
            textField = new JTextField(10);
            newPanel.add(textField);


            JButton rename = new JButton("Rename");
            newPanel.add(rename);

            rename.addActionListener(new RenameSetAction());

            setVisible(true);
            this.flashCardSet = flashCardSet;
        }

        // action listener for rename button
        private class RenameSetAction implements ActionListener {

            // MODIFIES: this
            // EFFECTS: renames flashcard with string in text field, hides rename gui and setLists()
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textField.getText().equals("")) {
                    text = textField.getText();
                    flashCardSet.setName(text);
                    setVisible(false);
                    int index = list.getSelectedIndex();
                    setLists();
                    list.setSelectedIndex(index);
                }
            }
        }
    }

    // gui for add flashcard
    private class AddFileUI extends JFrame {

        private JTextField textField;
        private static final int WIDTH = 250;
        private static final int HEIGHT = 100;

        // EFFECTS: creates new add flashcard gui
        public AddFileUI() {
            super("Create a FlashCard Set");
            setSize(WIDTH, HEIGHT);

            JPanel newPanel = new JPanel();
            add(newPanel);
            textField = new JTextField(10);
            newPanel.add(textField);

            JButton rename = new JButton("Create");
            newPanel.add(rename);

            rename.addActionListener(new CreateSetAction());

            setVisible(true);
        }

        // actionlistener for create set button
        private class CreateSetAction implements ActionListener {
            String text;

            // MODIFIES: this
            // EFFECTS: creates new flashcard from textfield, hides add flashcard gui and selects new flashcard
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textField.getText().equals("")) {
                    text = textField.getText();
                    sets.add(new FlashCardSet(text));

                    setVisible(false);
                    int index = sets.size();
                    setLists();
                    list.setSelectedIndex(index - 1);
                }
            }
        }
    }

    // gui for delete all flashcards
    private class DeleteAllUI extends JFrame {

        private static final int WIDTH = 480;
        private static final int HEIGHT = 88;

        // EFFECTS: creates gui for delete all flashcards
        public DeleteAllUI() {
            super("Delete All FlashCardSets");

            setSize(WIDTH, HEIGHT);
            JPanel newPanel = new JPanel();
            add(newPanel);
            JLabel deleteText = new JLabel("Would you like to delete all flashcard sets?");
            newPanel.add(deleteText);

            JButton yesButton = new JButton("Yes");
            JButton noButton = new JButton("No");

            newPanel.add(yesButton);
            newPanel.add(noButton);

            yesButton.addActionListener(new YesActionListener());
            noButton.addActionListener(new NoActionListener());

            setVisible(true);
        }

        // action listener for yes button
        private class YesActionListener implements ActionListener {

            // MODIFIES: this
            // EFFECTS: clears all sets, hides delete all gui and setLists()
            @Override
            public void actionPerformed(ActionEvent e) {
                sets.clear();
                setVisible(false);
                setLists();
            }
        }

        // action listener for no button
        private class NoActionListener implements ActionListener {

            // MODIFIES: this
            // EFFECTS: hides delete all gui
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        }
    }

    // gui for creating new not mastered set
    private class MasterSetUI extends JFrame {
        private static final int WIDTH = 300;
        private static final int HEIGHT = 100;

        private JTextField textField;
        private int index;


        // EFFECTS: creates gui for not mastered set
        public MasterSetUI(int index) {
            super("Create Not MasterSet");
            this.index = index;
            setSize(WIDTH, HEIGHT);
            setVisible(true);
            JPanel newPanel = new JPanel();
            textField = new JTextField(10);

            add(newPanel);
            JButton button = new JButton("Set Name");
            button.addActionListener(new MasterAction());

            newPanel.add(textField);
            newPanel.add(button);
            revalidate();
        }

        // action listener for master set button
        private class MasterAction implements ActionListener {

            // MODIFIES: this
            // EFFECTS: creates new not mastered set from index of selected flashcard
            @Override
            public void actionPerformed(ActionEvent e) {
                FlashCardSet fc = sets.get(index);

                String name = textField.getText();

                sets.add(new FlashCardSet(fc.notMasteredSet(), name));
                setVisible(false);
                setLists();
                list.setSelectedIndex(index + 1);
            }
        }
    }
}





