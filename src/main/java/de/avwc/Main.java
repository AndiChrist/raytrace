package de.avwc;

import de.avwc.gfx.Display;
import de.avwc.main.Scene;
import de.avwc.util.RayTracer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by andichrist on 23.04.17.
 */
public final class Main {

    public static final double EPSILON = 10e-12;

    public static void main(String[] args) {
        Display display = new Display();
        RayTracer.trace(display);

        Main panel = new Main();
        panel.saveImage(display);

        display.openJFrame();
    }

    private void saveImage(Display display) {
        File image = new File("Image.png");

        try {
            ImageIO.write(display.getCanvas(), "PNG", image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Prozedur Bild_Rendern
        Strahl.Ursprung := Augpunkt
        Für jedes (x,y)-Pixel der Rastergrafik
            Strahl.Richtung := [3D-Koordinaten des Pixels der Bildebene] − Augpunkt
            Farbe des (x,y)-Pixels := Farbe_aus_Richtung(Strahl)

    Funktion Farbe_aus_Richtung(Strahl)
        Schnittpunkt := Nächster_Schnittpunkt(Strahl)
        Wenn Schnittpunkt.Gewinner = (kein) dann
            Farbe_aus_Richtung := Hintergrundfarbe
        sonst
            Farbe_aus_Richtung := Farbe_am_Schnittpunkt(Strahl, Schnittpunkt)

    Funktion Nächster_Schnittpunkt(Strahl)
        MaxDistanz := ∞
        Schnittpunkt.Gewinner := (kein)
        Für jedes Primitiv der Szene
            Schnittpunkt.Distanz := Teste_Primitiv(Primitiv, Strahl)
            Wenn Schnittpunkt.Distanz < MaxDistanz dann
                MaxDistanz := Schnittpunkt.Distanz
                Schnittpunkt.Gewinner := Primitiv
        Nächster_Schnittpunkt := Schnittpunkt

    // rekursiv
    Funktion Farbe_am_Schnittpunkt(Strahl, Schnittpunkt)
        Wenn Schnittpunkt.Gewinner.Material = spiegelnd oder transparent dann
            Reflektierter_Anteil := Fresnel(Strahl, Schnittpunkt)
            Farbe := Reflektierter_Anteil × Farbe_aus_Richtung(Reflexionsstrahl)
                   + Gebrochener_Anteil × Farbe_aus_Richtung(Gebrochener Strahl)
        sonst
            Farbe := 0
            Für jede Lichtquelle
                Schattenstrahl := Lichtquelle.Position - Schnittpunkt.Position
                SchattenSchnittpunkt := Nächster_Schnittpunkt(Schattenstrahl)
                Wenn SchattenSchnittpunkt.Gewinner = Lichtquelle dann
                    Farbe := Farbe + Direkte_Beleuchtung(Strahl, Lichtquelle)
        Farbe_am_Schnittpunkt := Farbe

     */
}

