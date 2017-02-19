package generatefilesforform;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;

/**
 *
 * @author geras
 */
public class UIForGenerating
{
    private final String PARAMS_FILE = "src/generatefilesforform/params.txt";

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel inputPanel;
    private JTextField[] textFields;
    private JPanel buttonPannel;

    public UIForGenerating()
    {
        prepareGUI();
    }

    public static void main(String[] args)
    {
        UIForGenerating swingControlDemo = new UIForGenerating();
        swingControlDemo.showGUI();
    }

    private void prepareGUI()
    {
        mainFrame = new JFrame("GenerateFiles");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("", JLabel.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputPanel = new JPanel(new SpringLayout());
        buttonPannel = new JPanel();

        mainFrame.add(headerLabel);
        mainFrame.add(inputPanel);
        mainFrame.add(buttonPannel);
    }

    private void showGUI()
    {
        headerLabel.setText("Input values: ");

        String[] labels =
        {
            "First: ", "Second: ", "Third: "
        };

        int numPairs = labels.length;

        textFields = new JTextField[numPairs];

        //Create and populate the panel.
        for (int i = 0; i < numPairs; i++)
        {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            inputPanel.add(l);
            textFields[i] = new JTextField(10);
            l.setLabelFor(textFields[i]);
            inputPanel.add(textFields[i]);
        }

        fillTextFields(textFields);

        SpringUtilities.makeCompactGrid(inputPanel,
                numPairs, 2, //rows, cols
                6, 6, //initX, initY
                6, 6);       //xPad, yPad

        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");

        createButton.setActionCommand("Create");
        cancelButton.setActionCommand("Cancel");

        createButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());

        buttonPannel.add(createButton);
        buttonPannel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    private void fillTextFields(JTextField[] textFields)
    {
        try( BufferedReader br = new BufferedReader( new FileReader(PARAMS_FILE) ) )
        {
            for (JTextField textField : textFields)
            {
                textField.setText( br.readLine() );
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void writeToParamsFile(JTextField[] textFields)
    {
        try( PrintWriter pw = new PrintWriter(PARAMS_FILE) )
        {
            for (JTextField textField : textFields)
            {
                pw.println(textField.getText());
            }
        }
       catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private class ButtonClickListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();

            if (command.equals("Create"))
            {
                writeToParamsFile(textFields);
            }
            else
            {
                System.exit(0);
            }
        }

    }

}
