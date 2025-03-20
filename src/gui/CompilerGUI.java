package gui;

import compiler.CodewareCompiler;


import javax.swing.*;
import java.awt.*;

public class CompilerGUI extends JFrame {

    private JTextArea inputArea;
    public static JTextArea outputArea;

    private Thread runThread;


    public CompilerGUI(){
        setTitle("Code-ware");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
        setIconImage(new ImageIcon("src/icon.png").getImage());
    }

    public static void appendOutput(String text){
        SwingUtilities.invokeLater(() -> outputArea.append(text + "\n"));
    }

    private void initComponents(){
        inputArea = new JTextArea();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        outputArea.setBackground(new Color(30, 30, 30));
        outputArea.setForeground(Color.WHITE);
        outputArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));


        JButton compileButton = new JButton("Compile");
        JButton stopButton = new JButton("Stop");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(compileButton);
        buttonPanel.add(stopButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(inputArea), new JScrollPane(outputArea));
        splitPane.setDividerLocation(300);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        compileButton.addActionListener(e -> runCompiler());
        stopButton.addActionListener(e -> stopRun());
    }

    private void runCompiler(){
        if (runThread != null && runThread.isAlive()) {
            outputArea.append("\nProgram is already running.\n");
            return;
        }

        outputArea.setText("");
        String sourceCode = inputArea.getText();

        runThread = new Thread(() -> {
            try {
                new CodewareCompiler(sourceCode);
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> outputArea.append("Error: " + ex.getMessage() + "\n"));
            }
        });
        runThread.start();
    }
    private void stopRun(){
        if (runThread != null && runThread.isAlive()) {
            runThread.interrupt();
            outputArea.append("\nProgram interrupted by user.\n");
        } else {
            outputArea.append("\nNo program is currently running.\n");
        }
    }
}
