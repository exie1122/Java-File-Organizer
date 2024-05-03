import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FBXFileOrganizer {

    public static void main(String[] args) {
        // Set up the GUI frame and panel
        JFrame frame = new JFrame("File Organizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2)); // Set up a grid layout
        frame.add(panel, BorderLayout.CENTER);

        // Directory to organize
        JLabel labelInput = new JLabel("Enter Directory to Organize:");
        JTextField inputText = new JTextField(20);
        panel.add(labelInput); // corresponding windows that I used (gui is very bad tho maybe will improve it as i learn more stuff)
        panel.add(inputText);

        // Target directory
        JLabel labelOutput = new JLabel("Enter Target Directory:");
        JTextField outputText = new JTextField(20);
        panel.add(labelOutput);
        panel.add(outputText);

        // File type
        JLabel labelType = new JLabel("Enter File Type (e.g., zip. No need to include a '.' before a file type):");
        JTextField typeText = new JTextField(20);
        panel.add(labelType);
        panel.add(typeText);

        // Organize button
        JButton organizeButton = new JButton("Organize");
        frame.add(organizeButton, BorderLayout.SOUTH);

        // Action listener for the organize button
        organizeButton.addActionListener(e -> {
            String searchedDirectory = inputText.getText();
            String targetDirectory = outputText.getText();
            String fileType = typeText.getText();
            String finalFileType = fileType.startsWith(".") ? fileType : "." + fileType;

            File dir = new File(searchedDirectory);
            File[] files = dir.listFiles((d, name) -> name.endsWith(finalFileType));

            if (files != null && files.length > 0) {
                for (File file : files) {
                    try {
                        Files.move(Paths.get(file.getAbsolutePath()), Paths.get(targetDirectory, file.getName()));
                        JOptionPane.showMessageDialog(frame, "Moved: " + file.getName());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Failed to move: " + file.getName(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "No " + finalFileType + " files found or error reading the directory.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}