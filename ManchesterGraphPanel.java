import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

class ManchesterGraphPanel extends JPanel {
    private final String trame;
    private final int xStart = 50;  // Début de l'axe horizontal
    private final int yHigh = 50;   // Niveau haut (nV)
    private final int yMiddle = 100; // Niveau moyen (0V)
    private final int yLow = 150;   // Niveau bas (-nV)
    private final int step = 50;    // Largeur de chaque bit
    private final Color axisColor = new Color(100, 100, 100);       // Axe et niveaux


    public ManchesterGraphPanel(String trame) {
        this.trame = trame;
        setPreferredSize(new Dimension(trame.length() * step + 100, 200)); // Ajuster la taille selon la trame
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Activer l'anticrénelage pour des traits plus nets
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond avec un dégradé
        GradientPaint gradient = new GradientPaint(0, 0, new Color(210, 240, 255), getWidth(), getHeight(), new Color(255, 255, 255));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Légendes des niveaux
        g2d.setColor(new Color(30, 30, 30));  // Texte sombre pour les légendes
        g2d.drawString("nV", 10, yHigh - 5); // Texte pour niveau haut
        g2d.drawString("0V", 10, yMiddle + 5);  // Texte pour niveau moyen
        g2d.drawString("-nV", 10, yLow + 15);  // Texte pour niveau bas

        // Tracé des lignes de référence
        // Dessiner les axes
        g2d.setColor(axisColor);
        // g2d.setStroke(new BasicStroke(1.5f));
        float[] dashPattern1 = {4, 4};
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern1, 0));
        // g2d.setStroke(new BasicStroke(1));
        g2d.setColor(new Color(255, 0, 0));  // Ligne de niveau en rouge vif
        g2d.drawLine(xStart - 20, yHigh, getWidth() - 20, yHigh);   // Ligne niveau haut
        g2d.drawLine(xStart - 20, yMiddle, getWidth() - 20, yMiddle); // Ligne niveau moyen
        g2d.drawLine(xStart - 20, yLow, getWidth() - 20, yLow);     // Ligne niveau bas

        // Initialisation des variables pour le tracé
        int x = xStart; // Position horizontale initiale

        for (int i = 0; i < trame.length(); i++) {
            char bit = trame.charAt(i);

            // Déterminer les transitions pour le bit
            int startY = (bit == '0') ? yLow : yHigh;
            int midY = (bit == '0') ? yHigh : yLow;

            // Ligne horizontale pour la première moitié du bit
            g2d.setColor(new Color(30, 70, 140));  // Couleur bleue foncée pour le signal
            g2d.setStroke(new BasicStroke(2)); // Ligne épaisse pour le signal
            g2d.drawLine(x, startY, x + step / 2, startY);

            // Transition verticale au milieu du bit
            g2d.setColor(new Color(0, 120, 255));  // Couleur bleu clair pour la transition
            g2d.drawLine(x + step / 2, startY, x + step / 2, midY);

            // Ligne horizontale pour la seconde moitié du bit
            g2d.setColor(new Color(30, 70, 140));  // Couleur bleue foncée pour le signal
            g2d.drawLine(x + step / 2, midY, x + step, midY);

            // Ajouter de la transition rigide ou pointillée
            if (i < trame.length() - 1 && bit == trame.charAt(i + 1)) {
                // Transition rigide si le bit suivant est identique
                g2d.setColor(new Color(30, 70, 140));  // Bleu foncé pour signal continu
                g2d.setStroke(new BasicStroke(2)); // Ligne épaisse
                g2d.drawLine(x + step, midY, x + step, (bit == '0') ? yLow : yHigh);
            } else {
                // Transition pointillée par défaut
                g2d.setColor(new Color(0, 120, 255));  // Bleu clair pour la transition
                float[] dashPattern = {4, 4};
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
                g2d.drawLine(x , yLow + 30, x,yHigh - 30);
            
            }

            x += step; // Passer au bit suivant
        }

        // Ajouter des étiquettes sous chaque bit
        x = xStart; // Réinitialiser x pour les étiquettes
        for (int i = 0; i < trame.length(); i++) {
            g2d.setColor(new Color(30, 70, 140));  // Couleur bleue pour les étiquettes
            g2d.drawString(Character.toString(trame.charAt(i)), x + step / 2 - 5, yLow + 30);
            x += step;
        }

        // Ajouter une bordure autour du panneau
        g2d.setColor(new Color(30, 30, 30));  // Bordure sombre
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
    }
}
