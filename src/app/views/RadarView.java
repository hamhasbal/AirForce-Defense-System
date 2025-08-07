package app.views;

import app.services.AircraftGenerator;
import app.services.RadarSimulation;
import app.controllers.HomeController;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class RadarView extends StackPane {

    private final Pane radarPane;
    private final RadarSimulation radarSimulation;
    private final AircraftGenerator aircraftGenerator;

    public RadarView(HomeController homeController) {
        radarPane = new Pane();
        getChildren().add(radarPane);

        radarSimulation = new RadarSimulation(radarPane);
        radarSimulation.drawRadar();
        radarSimulation.startRadarSweep();

        aircraftGenerator = new AircraftGenerator(radarPane, homeController);
        aircraftGenerator.startGenerating();
    }

    public Pane getView() {
        return radarPane;
    }
}
