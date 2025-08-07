package app.services;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * RadarSimulation draws radar circles and runs radar sweep animation.
 */
public class RadarSimulation {
    public static double currentSweepAngle = 0;

    private final Pane radarPane;
    private Rotate sweepRotate;

    public RadarSimulation(Pane radarPane) {
        this.radarPane = radarPane;
    }

    public void drawRadar() {
        int numberOfCircles = 3;
        int radius = 300;
        int centerX = 450;
        int centerY = 300;

        // Outer circle
        Circle outer = new Circle(centerX, centerY, radius, Color.TRANSPARENT);
        outer.setStroke(Color.LIMEGREEN);
        outer.setStrokeWidth(3);
        radarPane.getChildren().add(outer);

        // Inner circles
        for (int i = 1; i <= numberOfCircles; i++) {
            Circle inner = new Circle(centerX, centerY, radius - i * 80, Color.TRANSPARENT);
            inner.setStroke(Color.LIMEGREEN);
            inner.setStrokeWidth(1);
            inner.setOpacity(0.5);
            radarPane.getChildren().add(inner);
        }

        // Cross lines
        Line hLine = new Line(centerX - radius, centerY, centerX + radius, centerY);
        hLine.setStroke(Color.LIMEGREEN);
        hLine.setOpacity(0.5);
        radarPane.getChildren().add(hLine);

        Line vLine = new Line(centerX, centerY - radius, centerX, centerY + radius);
        vLine.setStroke(Color.LIMEGREEN);
        vLine.setOpacity(0.5);
        radarPane.getChildren().add(vLine);
    }

    public void startRadarSweep() {
        Polygon sweepLine = new Polygon(
                450.0, 300.0,
                750.0, 265.0,
                750.0, 335.0
        );

        Stop[] stops = new Stop[]{
                new Stop(0, Color.TRANSPARENT),
                new Stop(1, Color.LIMEGREEN)
        };
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        sweepLine.setFill(gradient);
        sweepLine.setEffect(new GaussianBlur(5));
        radarPane.getChildren().add(sweepLine);

        sweepRotate = new Rotate(0, 450, 300);
        sweepLine.getTransforms().add(sweepRotate);

        Timeline sweepAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, e -> currentSweepAngle = 0, new KeyValue(sweepRotate.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(5), e -> currentSweepAngle = 360, new KeyValue(sweepRotate.angleProperty(), 360))
        );

        sweepRotate.angleProperty().addListener((obs, oldVal, newVal) -> currentSweepAngle = newVal.doubleValue());

        sweepAnimation.setCycleCount(Timeline.INDEFINITE);
        sweepAnimation.play();
    }
}
