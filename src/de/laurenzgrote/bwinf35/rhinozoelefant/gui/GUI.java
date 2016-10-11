package de.laurenzgrote.bwinf35.rhinozoelefant.gui;

import de.laurenzgrote.bwinf35.rhinozoelefant.Bild;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {

    private static final FileFilter filter = new FileNameExtensionFilter("PNG-Dateien",  "png");


    private Bild bild;
    private BufferedImage ursprungsbild;

    private ImagePanel originalBild, endBild;
    private ZwischenbildWahlPanel zwischenbildWahlPanel;

    private JButton bildLaden, bildExportieren;

    public GUI() {
        // Titel der GUI setzen
        super ("Rhinozelfant: Laurenz Grote");

        // Was muss das muss!
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);

        // In das JFrame kommt ein Bilderpanel + Knopfpanel rein
        Container container = this.getContentPane();
        container.add(getCenterPanel(), BorderLayout.CENTER);
        container.add(getSouthPanel(), BorderLayout.SOUTH);

        // GUI gerendert, anzeigen!
        this.setVisible(true);
    }

    private JPanel getCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        originalBild = new ImagePanel();
        originalBild.setBorder(BorderFactory.createTitledBorder("Originalbild"));

        zwischenbildWahlPanel = new ZwischenbildWahlPanel();
        zwischenbildWahlPanel.setBorder(BorderFactory.createTitledBorder("Zwischenbilder"));

        endBild = new ImagePanel();
        endBild.setBorder(BorderFactory.createTitledBorder("Endbild"));

        centerPanel.add(originalBild);
        centerPanel.add(zwischenbildWahlPanel);
        centerPanel.add(endBild);

        return centerPanel;
    }

    private JPanel getSouthPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1,2));

        bildLaden = new JButton("Bilddatei laden");
        bildExportieren = new JButton("Bilddatei exportieren");

        southPanel.add(bildLaden);
        southPanel.add(bildExportieren);

        registriereOeffnenListener();
        registriereExportierenListener();

        return southPanel;
    }

    private void registriereOeffnenListener() {
        bildLaden.addActionListener(actionEvent -> {

            final JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(filter);
            int returnVal = jFileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File quelle = jFileChooser.getSelectedFile();
                try {
                    ursprungsbild = ImageIO.read(quelle);
                    originalBild.setBackgroundImage(ursprungsbild);
                    bild = new Bild(quelle, true); // In der GUI brauchen wir DEBUG
                    zwischenbildWahlPanel.setGleichfarbigeBild(bild.getDebugGleichfarbige());
                    endBild.setBackgroundImage(bild.getGefiltertesBild());
                } catch (IOException e) {
                    System.err.println("I/O-Error beim Laden der Bilddatei!");
                    System.exit(-1);
                }
            } else {
                System.err.println("Bitte Quelldatei angeben!");
                System.exit(-1);
            }
        });
    }
    private void registriereExportierenListener() {
        bildExportieren.addActionListener(actionEvent -> {

        });
    }

}
