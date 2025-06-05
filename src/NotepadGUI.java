import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NotepadGUI extends JFrame {
    // file explorer
    private JFileChooser fileChooser;

    private JTextArea textArea;
    private File currentFile;

    // swing's built it in library for "undo" and "redo"
    private UndoManager undoManager;

    public NotepadGUI() {
        super("Notepad");
        setSize(400,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // file chooser setup
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        undoManager = new UndoManager();
        addGuiComponents();
    }

    private void addGuiComponents() {
        addToolbar();

        textArea = new JTextArea();
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                // adds each edit that we do in the text area (adding or removing text)
                undoManager.addEdit(e.getEdit());
            }
        });
        add(textArea, BorderLayout.CENTER);
    }

    private void addToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        menuBar.add(addFileMenu());
        menuBar.add(addEditMenu());

        add(toolBar, BorderLayout.NORTH);
    }

    private JMenu addFileMenu() {
        JMenu fileMenu = new JMenu("File");

        // "new" functionality
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset title header
                setTitle("Notepad");

                // reset text area
                textArea.setText("");

                // reset current file
                currentFile = null;
            }
        });
        fileMenu.add(newMenuItem);

        // "open" functionality
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open file explorer
                int result = fileChooser.showOpenDialog(NotepadGUI.this);
                if (result != JFileChooser.APPROVE_OPTION) return;

                try {
                    // reset notepad
                    newMenuItem.doClick();

                    //get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    // update the current file
                    currentFile = selectedFile;

                    // update title header
                    setTitle(selectedFile.getName());

                    // read the file
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    //store user's text
                    StringBuilder fileText = new StringBuilder();
                    String readText;
                    while((readText = bufferedReader.readLine()) != null) {
                        fileText.append(readText + "/n");
                    }
                } catch (Exception e1) {

                }


            }
        });

        fileMenu.add(openMenuItem);

        // "save as" functionality - create and save a text file with user's text
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // open "Save" dialog
                int result = fileChooser.showSaveDialog(NotepadGUI.this);
                if(result != JFileChooser.APPROVE_OPTION) return;

                try {
                    File selectedFile = fileChooser.getSelectedFile();

                    String fileName = selectedFile.getName();
                    if(!fileName.substring(fileName.length() - 4).equalsIgnoreCase(".txt")) {
                        selectedFile = new File(selectedFile.getAbsoluteFile() + ".txt");
                    }

                    // create new file
                    selectedFile.createNewFile();

                    FileWriter fileWriter = new FileWriter(selectedFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(textArea.getText());
                    bufferedWriter.close();
                    fileWriter.close();

                    // update teh title header of gui to the save text file
                    setTitle(fileName);

                    // update current file
                    currentFile = selectedFile;

                    // save functionality
                    JOptionPane.showMessageDialog(NotepadGUI.this, "File Saved");

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        fileMenu.add(saveAsMenuItem);

        // saves the changes made to already created file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if the current file is null then we have to perform save as functionality
                if(currentFile == null) saveAsMenuItem.doClick();

                // if the user chooses to cancel saving the file, prevent the rest of the code from executing
                if(currentFile == null) return;

                try{
                    // write to current file
                    FileWriter fileWriter = new FileWriter(currentFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(textArea.getText());
                    bufferedWriter.close();
                    fileWriter.close();

                }catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
        fileMenu.add(saveMenuItem);

        // exits the program
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotepadGUI.this.dispose();
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu addEditMenu() {
        JMenu editMenu = new JMenu("Edit");

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        editMenu.add(undoMenuItem);

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        editMenu.add(redoMenuItem);

        return editMenu;
    }
}
