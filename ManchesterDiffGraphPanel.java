import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

class ManchesterDiffGraphPanel extends JPanel {
    private final String trame;
    private final int xStart = 50;  // Début de l'axe horizontal
    private final int yHigh = 150;   // Niveau haut devient bas
    private final int yMiddle = 100; // Niveau moyen (0V) reste inchangé
    private final int yLow = 50;     // Niveau bas devient haut
    private final int step = 50;    // Largeur de chaque bit

    public ManchesterDiffGraphPanel(String trame) {
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
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(new Color(255, 0, 0));  // Ligne de niveau en rouge vif
        g2d.drawLine(xStart - 20, yHigh, getWidth() - 20, yHigh);   // Ligne niveau haut
        g2d.drawLine(xStart - 20, yMiddle, getWidth() - 20, yMiddle); // Ligne niveau moyen
        g2d.drawLine(xStart - 20, yLow, getWidth() - 20, yLow);     // Ligne niveau bas

        // Initialisation des variables pour le tracé
        int x = xStart; // Position horizontale initiale
        int currentY = yHigh; // Position initiale (inversée)

        for (int i = 0; i < trame.length(); i++) {
            char bit = trame.charAt(i);

            // Ajouter une transition au début du bit '0'
            if (bit == '0') {
                g2d.setColor(new Color(0, 120, 255));  // Couleur bleu clair pour la transition
                g2d.drawLine(x, currentY, x, (currentY == yHigh) ? yLow : yHigh);
                currentY = (currentY == yHigh) ? yLow : yHigh;
            } else {
                // Ajouter une ligne pointillée verticale si pas de transition
                g2d.setColor(new Color(0, 120, 255));  // Bleu clair
                float[] dashPattern = {5, 5}; // Motif pointillé
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
                g2d.drawLine(x, yLow, x, yHigh);
            }

            // Ligne horizontale pour la première moitié du bit
            g2d.setColor(new Color(30, 70, 140));  // Couleur bleue foncée pour le signal
            g2d.setStroke(new BasicStroke(2)); // Ligne épaisse pour le signal
            g2d.drawLine(x, currentY, x + step / 2, currentY);

            // Transition verticale au milieu du bit
            g2d.setColor(new Color(0, 120, 255));  // Couleur bleu clair pour la transition
            g2d.drawLine(x + step / 2, currentY, x + step / 2, (currentY == yHigh) ? yLow : yHigh);
            currentY = (currentY == yHigh) ? yLow : yHigh;

            // Ligne horizontale pour la seconde moitié du bit
            g2d.setColor(new Color(30, 70, 140));  // Couleur bleue foncée pour le signal
            g2d.drawLine(x + step / 2, currentY, x + step, currentY);

            x += step; // Passer au bit suivant
        }

        // Ajouter des étiquettes au milieu des transitions
        x = xStart; // Réinitialiser x pour les étiquettes
        for (int i = 0; i < trame.length(); i++) {
            g2d.setColor(new Color(30, 70, 140));  // Couleur bleue pour les étiquettes
            g2d.drawString(Character.toString(trame.charAt(i)), x + step / 2 - 5, yMiddle + 70); // Centré sous les transitions
            x += step;
        }

        // Ajouter une bordure autour du panneau
        g2d.setColor(new Color(30, 30, 30));  // Bordure sombre
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
    }
}
