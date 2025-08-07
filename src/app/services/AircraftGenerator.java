package app.services;

import app.controllers.HomeController;
import app.models.Aircraft;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class AircraftGenerator {

    private final Pane radarPane;
    private final HomeController homeController;
    private final Random random = new Random();

    public AircraftGenerator(Pane radarPane, HomeController homeController) {
        this.radarPane = radarPane;
        this.homeController = homeController;
    }

    public void startGenerating() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> generateAircraft()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void generateAircraft() {
        // Generate random starting point
        double angle = random.nextDouble() * 360;
        double radius = 300 + random.nextDouble() * 100;
        double startX = 450 + radius * Math.cos(Math.toRadians(angle));
        double startY = 300 + radius * Math.sin(Math.toRadians(angle));

        // Create aircraft
        String aircraftId = "ID" + random.nextInt(10000);
        Aircraft aircraft = new Aircraft(aircraftId, "Type-X", "Missiles", true);

        // 10% chance of being unidentified
        boolean isUnidentified = random.nextInt(5) == 0;

        // Create blip
        Circle blip = new Circle(5, isUnidentified ? Color.RED : Color.ORANGE);
        blip.setCenterX(startX);
        blip.setCenterY(startY);
        blip.setEffect(new DropShadow(10, isUnidentified ? Color.RED : Color.ORANGE));

        radarPane.getChildren().add(blip);

        // Animate blip toward radar center
        Timeline move = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(blip.centerXProperty(), startX),
                        new KeyValue(blip.centerYProperty(), startY)
                ),
                new KeyFrame(Duration.seconds(6),
                        new KeyValue(blip.centerXProperty(), 450),
                        new KeyValue(blip.centerYProperty(), 300)
                )
        );
        move.setCycleCount(1);
        move.setOnFinished(ev -> radarPane.getChildren().remove(blip));
        move.play();

        // Log to terminal & notify controller
        if (isUnidentified) {
            System.out.println("⚠️ Unidentified aircraft detected: " + aircraftId);
            homeController.handleUnidentifiedAircraft(aircraft);
        } else {
            System.out.println("✅ Identified aircraft detected: " + aircraftId);
            homeController.handleIdentifiedAircraft(aircraft);
        }
    }
}
