// Importing necessary classes and exceptions

import Exceptions.AgeException;
import Exceptions.EmptyException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 *IDFrame class represents a JFrame for collecting user information to create a Personal Identification Card.
 * <p>
 * This class extends JFrame and implements the ActionListener interface.
 * </p>
 *
 *  * NO INFORMATION SAVED *
 *
 * @author Ari Fernandez
 * @since JDK 21
 * @see Main
 * @see SoundPlayer
 * @see Exceptions.AgeException
 * @see Exceptions.EmptyException
 */

// Class declaration extending JFrame and implementing ActionListener interface
public class IDFrame extends JFrame implements ActionListener {
    // Instance variables
    JComboBox<String> dropDownGender;
    String name = "", country = "", gender = "";
    int age = 0;
    JButton submitButton;
    JLabel[] labels;
    ArrayList<JTextArea> textAreas = new ArrayList<>();

    // Constructor
    /**
     * Constructs a new IDFrame with the given title.
     *
     * @param title the title of the frame
     */
    public IDFrame(String title) {
        // Setting up JFrame properties
        this.setTitle(title);
        this.setSize(new Dimension(350, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(5, 2, 15, 15));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0x87CEFA));

        // Creating JLabels for input fields
        JLabel nameLabel = new JLabel("Name: ");
        JLabel ageLabel = new JLabel("Age: ");
        JLabel originLabel = new JLabel("<html>Country<br>of Origin:<html/> ");
        JLabel genderLabel = new JLabel("Gender: ");

        labels = new JLabel[] {nameLabel, ageLabel, originLabel, genderLabel};

        // Setting font for labels
        for(JLabel label : labels) {
            label.setFont(new Font("Consolas", Font.BOLD, 30));
        }

        // Creating submit button
        submitButton = new JButton();
        submitButton.setText("SUBMIT");
        submitButton.addActionListener(this);

        // Creating JTextAreas for input fields
        JTextArea nameArea = new JTextArea();
        JTextArea ageArea = new JTextArea();
        JTextArea originArea = new JTextArea();

        textAreas.add(nameArea);
        textAreas.add(ageArea);
        textAreas.add(originArea);

        // Setting properties for JTextAreas
        for(JTextArea i : textAreas) {
            i.setLineWrap(true);
            i.setBackground(Color.lightGray);
            i.setForeground(Color.WHITE);
            i.setBounds(0,0, 250, 80);
            i.setCaretColor(Color.white);
            i.setFont(new Font(null, Font.PLAIN, 15));
        }

        String[] genders = {"Other", "Male", "Female", "Non-Binary"};
        dropDownGender = new JComboBox<>(genders);
        dropDownGender.addActionListener(this);
        gender = "Other";

        // Adding components to JFrame
        this.add(labels[0]);
        this.add(textAreas.getFirst());
        this.add(labels[1]);
        this.add(textAreas.get(1));
        this.add(labels[2]);
        this.add(textAreas.get(2));
        this.add(labels[3]);
        this.add(dropDownGender);
        this.add(new JLabel());
        this.add(submitButton);
    }

    // Method to check if JTextAreas are empty
    public int isObjEmpty(ArrayList<JTextArea> obj) {
        for (int i = 0; i < obj.size(); i++) {
            String text = obj.get(i).getText().trim();
            if (text.isEmpty()) {
                this.dispose();
                try {
                    throw new EmptyException("Make Sure to Fill Out All Sections");
                } catch (EmptyException e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null,
                            "Make sure to fill out all of the sections", "", JOptionPane.WARNING_MESSAGE);
                }
                return -1;
            } else {
                switch (i) {
                    case 0:
                        name = text;
                        break;
                    case 1:
                        age = Integer.parseInt(text);
                        break;
                    case 2:
                        country = text;
                        break;
                    default:
                        throw new IllegalStateException("Illegal State: " + i);
                }
            }
        }
        return 0;
    }

    // ActionListener interface method
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton) {
            // Checking if JTextAreas are empty
            if(isObjEmpty(textAreas) == -1) {
                return;
            }

            // Checking age requirement
            System.out.println(age);
            if(age < 16) {
                try {
                    throw new AgeException("Too Young to Apply for an ID");
                } catch(AgeException ex) {
                    this.dispose();
                    JOptionPane.showMessageDialog
                            (null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Displaying information
            this.dispose();

            Thread soundThread = new Thread(new SoundPlayer(new
                    File("/Users/arifernan/IdeaProjects/IDCreator/res/yahoo.wav")));
            soundThread.start();

            JOptionPane.showMessageDialog(null, String.format("Your name is %s, you are %d years old," +
                            " you are from %s, and you are a(n) %s", name, age, country, gender),
                    "", JOptionPane.INFORMATION_MESSAGE);
        }
        // Handle drop down gender selection
        else if (e.getSource() == dropDownGender) {
            JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
            gender = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
        }
    }
}