
import java.awt.*;
import javax.swing.*;

public class MainApp extends JFrame {

    private JTextField trameField;
    private JComboBox<String> comboBox;
    private JPanel graphPanel;

    public MainApp() {
        super("Encodage NRZ");
        init();
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void init() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Encodage de Trame NRZ", JLabel.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout());

        JLabel trameLabel = new JLabel("Trame:");
        trameField = new JTextField(10);
        String[] choices = {"NRZ", "NRZI", "MANCHESTER", "MANCHESTER DIFFERENCIEL", "CODE MILLER"};
        comboBox = new JComboBox<>(choices);
        JButton validateButton = new JButton("Valider");
        JButton clearButton = new JButton("Effacer");

        clearButton.addActionListener(e -> {
            trameField.setText("");
            graphPanel.removeAll();
            graphPanel.revalidate();
            graphPanel.repaint();
        });

        validateButton.addActionListener(e -> {
            String selectedChoice = (String) comboBox.getSelectedItem();
            String trame = trameField.getText();
            if (!trame.matches("[01]+")) {
                JOptionPane.showMessageDialog(this, "La trame doit contenir uniquement des 0 et des 1.",
                        "Erreur de validation", JOptionPane.ERROR_MESSAGE);
                return;
            }
            switch (selectedChoice) {
                case "NRZ" ->
                    executeNRZ(trame);
                case "NRZI" ->
                    executeNRZI(trame);
                case "MANCHESTER" ->
                    executeManchester(trame);
                case "MANCHESTER DIFFERENCIEL" ->
                    executeManchesterDifferential(trame);
                case "CODE MILLER" ->
                    executeCodeMiller(trame);
            }
        });

        inputPanel.add(trameLabel);
        inputPanel.add(trameField);
        inputPanel.add(comboBox);
        inputPanel.add(validateButton);
        inputPanel.add(clearButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        graphPanel = new JPanel(new BorderLayout());
        graphPanel.setPreferredSize(new Dimension(800, 300));
        graphPanel.setBackground(Color.WHITE);
        panel.add(graphPanel, BorderLayout.CENTER);

        add(panel);
    }

    private void executeNRZ(String trame) {
        graphPanel.removeAll();
        NRZGraphPanel nrzGraphPanel = new NRZGraphPanel(trame);
        JScrollPane scrollPane = new JScrollPane(nrzGraphPanel);
        JLabel titleLabel = new JLabel("Graphique NRZ", JLabel.CENTER);
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(titleLabel, BorderLayout.NORTH);
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        graphPanel.add(containerPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }

    private void executeNRZI(String trame) {
        graphPanel.removeAll();
        NRZIGraphPanel nrziGraphPanel = new NRZIGraphPanel(trame);
        JScrollPane scrollPane = new JScrollPane(nrziGraphPanel);
        JLabel titleLabel = new JLabel("Graphique NRZI", JLabel.CENTER);
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(titleLabel, BorderLayout.NORTH);
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        graphPanel.add(containerPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }


    private void executeManchester(String trame) {
        graphPanel.removeAll();
        ManchesterGraphPanel manchesterGraphPanel = new ManchesterGraphPanel(trame);
        JScrollPane scrollPane = new JScrollPane(manchesterGraphPanel);
        JLabel titleLabel = new JLabel("Graphique Manchester", JLabel.CENTER);
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(titleLabel, BorderLayout.NORTH);
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        graphPanel.add(containerPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }
    private void executeManchesterDifferential(String trame) {
        graphPanel.removeAll();
        ManchesterDiffGraphPanel manchesterDiffGraphPanel = new ManchesterDiffGraphPanel(trame);
        JScrollPane scrollPane = new JScrollPane(manchesterDiffGraphPanel);
        JLabel titleLabel = new JLabel("Graphique Manchester Différentiel", JLabel.CENTER);
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(titleLabel, BorderLayout.NORTH);
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        graphPanel.add(containerPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }

    private void executeCodeMiller(String trame) {
        graphPanel.removeAll();
        CodeMillerGraphPanel codeMillerGraphPanel = new CodeMillerGraphPanel(trame);
        JScrollPane scrollPane = new JScrollPane(codeMillerGraphPanel);
        JLabel titleLabel = new JLabel("Graphique Code Miller", JLabel.CENTER);
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(titleLabel, BorderLayout.NORTH);
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        graphPanel.add(containerPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}
