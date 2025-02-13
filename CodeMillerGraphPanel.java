import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

class CodeMillerGraphPanel extends JPanel {
    private final String trame;
    private final int xStart = 50;  // Début de l'axe horizontal
    private final int yHigh = 50;   // Niveau haut (nV)
    private final int yLow = 150;   // Niveau bas (-nV)
    private final int step = 50;    // Largeur de chaque bit
    private final int midStep = step / 2;
    private final int yMiddle = 100; // Niveau moyen (0V)


    // Couleurs et styles
    private final Color backgroundColor = new Color(240, 245, 255); // Fond doux
    private final Color axisColor = new Color(100, 100, 100);       // Axe et niveaux
    private final Color signalColor = new Color(0, 102, 204);       // Signal principal
    private final Color transitionColor = new Color(255, 0, 102);   // Transitions
    private final Color dashedLineColor = new Color(150, 150, 150); // Lignes pointillées
    private final Color textColor = new Color(0, 51, 102);          // Texte

    public CodeMillerGraphPanel(String trame) {
        this.trame = trame;
        setPreferredSize(new Dimension(trame.length() * 50 + 100, 350)); // Ajuster la taille selon la trame
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Activer l'anticrénelage pour des graphismes plus nets
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner le fond
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Dessiner les axes
        g2d.setColor(axisColor);
        float[] dashPattern1 = {4, 4};
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern1, 0));
        g2d.drawLine(xStart - 10, yHigh, getWidth(), yHigh); 
        g2d.drawLine(xStart - 20, yMiddle, getWidth() - 20, yMiddle); // Ligne niveau moyen
        g2d.drawLine(xStart - 10, yLow, getWidth(), yLow);   // Ligne bas (-nV)

        // Ajouter les légendes des niveaux
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(textColor);
        g2d.drawString("nV", 15, yHigh - 5);
        g2d.drawString("0V", 15, yMiddle + 5);  // Texte pour niveau moyen
        g2d.drawString("-nV", 15, yLow + 15);

        // Variables pour le tracé
        int currentY = yHigh; // Niveau initial
        int x = xStart;

        for (int i = 0; i < trame.length(); i++) {
            char bit = trame.charAt(i);

            if (bit == '1') {
                // Bit 1 : Transition au milieu
                g2d.setColor(signalColor);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(x, currentY, x + midStep, currentY); // Ligne horizontale (1ère moitié)
                int nextY = (currentY == yHigh) ? yLow : yHigh;   // Inversion au milieu
                g2d.setColor(transitionColor);
                g2d.drawLine(x + midStep, currentY, x + midStep, nextY); // Transition au milieu
                g2d.setColor(signalColor);
                g2d.drawLine(x + midStep, nextY, x + step, nextY);       // Ligne horizontale (2ème moitié)
                currentY = nextY; // Mettre à jour le niveau
            } else if (bit == '0') {
                // Bit 0 : Pas de transition
                g2d.setColor(signalColor);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(x, currentY, x + step, currentY); // Ligne horizontale

                // Si un 0 est suivi d'un autre 0, ajouter une transition à la fin
                if (i < trame.length() - 1 && trame.charAt(i + 1) == '0') {
                    int nextY = (currentY == yHigh) ? yLow : yHigh; // Inversion
                    g2d.setColor(transitionColor);
                    g2d.drawLine(x + step, currentY, x + step, nextY); // Transition à la fin
                    currentY = nextY; // Mettre à jour le niveau
                }
            }

            // Ajouter des lignes pointillées pour délimiter les bits
            g2d.setColor(dashedLineColor);
            float[] dashPattern = {4, 4};
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
            g2d.drawLine(x, yLow + 30, x, yHigh - 30); // Lignes pointillées
            x += step; // Passer au bit suivant
        }

        // Étiqueter les bits en dessous
        x = xStart;
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        for (int i = 0; i < trame.length(); i++) {
            g2d.setColor(textColor);
            g2d.drawString(String.valueOf(trame.charAt(i)), x + step / 2 - 5, yLow + 40);
            x += step;
        }

        // Ajouter une bordure élégante
        g2d.setColor(axisColor);
        g2d.setStroke(new BasicStroke(1));
        // float[] dashPattern = {4, 4};
        // g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));

        g2d.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20); // Coins arrondis
    }
}
