import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JFrame implements DeletionListener {
   // FileHandler newfile = new FileHandler();
    private String saved = FileHandler.readWord();
    private static String selectedDirectoryPath = "";
    private JTextField direct;
    private JButton deleteButton;
    private JTextArea outputArea;
    private JButton select;

    public Main() {
        setTitle("Screenshot Deleter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon fileIcon = new ImageIcon("fileicon.png");
        Image image = fileIcon.getImage();
        Image resizedImage = image.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH); // Resize to 24x24
        ImageIcon resizedIcon = new ImageIcon(resizedImage);


        JPanel panel = new JPanel();
        panel.setLayout(null);

        deleteButton = new JButton("Delete Screenshots");
        deleteButton.setBounds(10, 60, 150, 25);
        panel.add(deleteButton);

        JLabel dir = new JLabel("Enter Directory:");
        dir.setBounds(10, 20, 100, 25);
        panel.add(dir);

        direct = new JTextField(300);
        direct.setBounds(110, 20, 235, 25);
        direct.setText(saved);
        panel.add(direct);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 100, 360, 150);
        outputArea.setEditable(false);
        panel.add(outputArea);

        JScrollPane scrollBar= new JScrollPane(outputArea);
        scrollBar.setBounds(10, 100, 360, 150);
        panel.add(scrollBar);

        select = new JButton(resizedIcon);
        select.setBounds(345, 20, 24, 24);
        select.setToolTipText("Select Directory");
        panel.add(select);

        select.addActionListener(e ->
                {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int returnval = fileChooser.showOpenDialog(panel);
                    if(returnval==JFileChooser.OPEN_DIALOG) {
                        File selectedDirectory = fileChooser.getSelectedFile();
                        selectedDirectoryPath = selectedDirectory.getAbsolutePath();
                        saved = selectedDirectoryPath;
                        FileHandler.replaceWord(saved);
                        direct.setText(saved);
                    }

                }
        );

        deleteButton.addActionListener(e ->
        {
            selectedDirectoryPath = direct.getText();
            saved = selectedDirectoryPath;
            FileHandler.replaceWord(saved);
            direct.setText(saved);
            onFileDeleted(selectedDirectoryPath);
            deleteScreenshots(selectedDirectoryPath);
            outputArea.setCaretPosition(0);
        }
        );

        add(panel);
    }

    private void deleteScreenshots(String path) {
        File downloadsDirectory = new File(path);
        Screenshot deleter = new Screenshot(this);
        boolean screenshotsFound = deleter.deleteScreenshots(downloadsDirectory);

        if (screenshotsFound) {
            outputArea.insert("Screenshots deleted.",0);
        } else {
            outputArea.insert("No screenshots found : ",0);
        }
    }

    @Override
    public void onFileDeleted(String message) {
        outputArea.insert(message + "\n",0);
    }

    public static void main(String[] args) {

                Main gui = new Main();
                gui.setVisible(true);
            }
        }
