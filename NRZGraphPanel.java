

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;


class NRZGraphPanel extends JPanel {
    private final String trame;
    private final int xStart = 50;  // Début de l'axe horizontal
    private final int yHigh = 50;   // Niveau haut (nV)
    private final int yLow = 150;   // Niveau bas (-nV)
    private final int step = 50;    // Largeur de chaque bit
    private final int yMiddle = 100; // Niveau moyen (0V)


    // Couleurs et styles
    private final Color axisColor = new Color(100, 100, 100);       // Axe et niveaux
    private final Color textColor = new Color(0, 51, 102);   
    public NRZGraphPanel(String trame) {
        this.trame = trame;
        setPreferredSize(new Dimension(trame.length() * step + 100, 300)); // Ajuster la taille selon la trame
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Activer l'anticrénelage pour des traits plus nets
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond dégradé pour un effet esthétique
        GradientPaint gradient = new GradientPaint(0, 0, new Color(180, 210, 255), getWidth(), getHeight(), new Color(240, 240, 255));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

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
        // Initialisation des variables pour le tracé
        int x = xStart; // Position horizontale initiale
        int currentY = (trame.charAt(0) == '1') ? yHigh : yLow;

        for (int i = 0; i < trame.length(); i++) {
            char bit = trame.charAt(i);
            int nextY = (bit == '1') ? yHigh : yLow;

            // Dessiner la ligne horizontale pour le bit
            g2d.setColor(new Color(40, 150, 200)); // Couleur bleue pour le signal
            g2d.setStroke(new BasicStroke(3)); // Ligne épaisse pour le signal
            g2d.drawLine(x, nextY, x + step, nextY);

            // Ligne verticale entre les transitions
            g2d.setColor(new Color(60, 60, 60)); // Gris foncé pour les transitions
            if (i > 0) {
                if (currentY != nextY) {
                    // Transition rigide (ligne continue)
                    g2d.setStroke(new BasicStroke(2)); 
                    g2d.drawLine(x, currentY, x, nextY); 
                } else {
                    // Ligne pointillée si pas de transition
                    float[] dashPattern = {5, 5};
                    g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
                    g2d.drawLine(x, yHigh, x, yLow); // Ligne verticale pointillée
                }
            }

            currentY = nextY; // Mettre à jour le niveau courant
            x += step; // Passer au bit suivant
        }

        // Ajouter des étiquettes sous chaque bit
        x = xStart; // Réinitialiser x pour les étiquettes
        for (int i = 0; i < trame.length(); i++) {
            g2d.setColor(new Color(80, 80, 255)); // Bleu pour les étiquettes
            g2d.drawString(String.valueOf(trame.charAt(i)), x + step / 2 - 5, yLow + 30);
            x += step;
        }

        // Ajouter une bordure autour du panneau
        g2d.setColor(new Color(80, 80, 80)); // Gris foncé pour la bordure
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
    }
}
