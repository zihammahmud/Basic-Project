import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator extends JFrame implements ActionListener {
    // Components
    private JTextField display;
    private JButton[] numberButtons;
    private JButton addButton, subButton, mulButton, divButton, eqButton, clrButton;

    // Variables for calculation
    private double firstNumber = 0;
    private String operation = "";
    private boolean isOperationClicked = false;

    public SimpleCalculator() {
        // Set up the frame
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(240, 240, 240));
        add(display, BorderLayout.NORTH);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        // Create number buttons (0-9)
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setBackground(new Color(220, 220, 255));
        }

        // Create operation buttons
        addButton = createOperationButton("+", new Color(255, 200, 200));
        subButton = createOperationButton("-", new Color(200, 255, 200));
        mulButton = createOperationButton("*", new Color(200, 200, 255));
        divButton = createOperationButton("/", new Color(255, 255, 200));

        // Create other buttons
        eqButton = new JButton("=");
        eqButton.setFont(new Font("Arial", Font.BOLD, 18));
        eqButton.addActionListener(this);
        eqButton.setBackground(new Color(200, 255, 255));

        clrButton = new JButton("C");
        clrButton.setFont(new Font("Arial", Font.BOLD, 18));
        clrButton.addActionListener(this);
        clrButton.setBackground(new Color(255, 200, 255));

        // Add buttons to panel
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(addButton);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(mulButton);

        buttonPanel.add(clrButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(eqButton);
        buttonPanel.add(divButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createOperationButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.addActionListener(this);
        button.setBackground(color);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle number buttons
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (isOperationClicked) {
                    display.setText("");
                    isOperationClicked = false;
                }
                display.setText(display.getText() + i);
                return;
            }
        }

        // Handle clear button
        if (e.getSource() == clrButton) {
            display.setText("");
            firstNumber = 0;
            operation = "";
            return;
        }

        // Handle operation buttons
        if (e.getSource() == addButton || e.getSource() == subButton ||
                e.getSource() == mulButton || e.getSource() == divButton) {

            try {
                firstNumber = Double.parseDouble(display.getText().trim());
            } catch (NumberFormatException ex) {
                display.setText("Error");
                return;
            }

            if (e.getSource() == addButton) operation = "+";
            else if (e.getSource() == subButton) operation = "-";
            else if (e.getSource() == mulButton) operation = "*";
            else if (e.getSource() == divButton) operation = "/";

            isOperationClicked = true;
            return;
        }

        // Handle equals button
        if (e.getSource() == eqButton) {
            try {
                double secondNumber = Double.parseDouble(display.getText().trim());
                double result = 0;

                switch (operation) {
                    case "+": result = firstNumber + secondNumber; break;
                    case "-": result = firstNumber - secondNumber; break;
                    case "*": result = firstNumber * secondNumber; break;
                    case "/":
                        if (secondNumber != 0) result = firstNumber / secondNumber;
                        else {
                            display.setText("Error");
                            return;
                        }
                        break;
                    default:
                        display.setText("Error");
                        return;
                }

                display.setText(String.valueOf(result));
                operation = "";
                isOperationClicked = true;
            } catch (NumberFormatException ex) {
                display.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleCalculator::new);
    }
}
