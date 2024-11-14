package org.example.simulationapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;

public class SimulationController {
    @FXML
    private Canvas canvas;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    private Habitat habitat;
    private boolean isRunning = false;
    private boolean showTime = true;
    private Timeline timeline;
    private long elapsedSeconds = 0;
    private Random random = new Random();

    @FXML
    public void initialize() {
        habitat = new Habitat();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        startButton.setOnAction(e -> startSimulation());
        stopButton.setOnAction(e -> stopSimulation());
        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case B -> startSimulation();
                case E -> stopSimulation();
                case T -> toggleTimeDisplay();
            }
        });
    }

    @FXML
    private void startSimulation() {
        isRunning = true;
        elapsedSeconds = 0;
        habitat.startSimulation();
        timeline.play();
        canvas.requestFocus();
    }

    @FXML
    private void stopSimulation() {
        isRunning = false;
        habitat.stopSimulation();
        timeline.stop();
        drawStatistics();
    }

    private void toggleTimeDisplay() {
        showTime = !showTime;
    }

    private void update() {
        if (!isRunning) return;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        elapsedSeconds++;
        habitat.update(elapsedSeconds);

        // Вероятность генерации грузового и легкового авто
        if (elapsedSeconds % Habitat.TRUCK_GENERATION_INTERVAL == 0 && random.nextDouble() < Habitat.TRUCK_PROBABILITY) {
            habitat.addVehicle(new Truck());
        }
        if (elapsedSeconds % Habitat.CAR_GENERATION_INTERVAL == 0 && random.nextDouble() < Habitat.CAR_PROBABILITY) {
            habitat.addVehicle(new Car());
        }

        for (Vehicle vehicle : habitat.getVehicles()) {
            gc.fillText(vehicle.toString(), Math.random() * canvas.getWidth(), Math.random() * canvas.getHeight());
        }

        // Отображаем время симуляции
        if (showTime) {
            gc.setFont(new Font(16));
            gc.fillText("Time: " + elapsedSeconds + " sec", 10, 20);
        }
    }

    private void drawStatistics() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(new Font(18));
        gc.fillText("Simulation ended", 10, 40);
        gc.fillText("Total vehicles generated: " + habitat.getVehicles().size(), 10, 70);
        gc.fillText("Trucks: " + habitat.getTruckCount(), 10, 100);
        gc.fillText("Cars: " + habitat.getCarCount(), 10, 130);
        gc.fillText("Total time: " + elapsedSeconds + " sec", 10, 160);
    }
}
