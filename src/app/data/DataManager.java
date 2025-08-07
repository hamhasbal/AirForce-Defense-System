package app.data;

import app.models.Aircraft;
import app.models.Pilot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static DataManager instance;

    private List<Aircraft> aircrafts;
    private List<Pilot> pilots;
    private List<String> deploymentLogs;

    private static final String AIRCRAFT_FILE = "aircraft.json";
    private static final String PILOT_FILE = "pilots.json";
    private static final String DEPLOYMENT_LOG_FILE = "deployment_logs.json";

    private DataManager() {
        aircrafts = loadData(AIRCRAFT_FILE, new TypeToken<ArrayList<Aircraft>>(){}.getType());
        pilots = loadData(PILOT_FILE, new TypeToken<ArrayList<Pilot>>(){}.getType());
        deploymentLogs = loadData(DEPLOYMENT_LOG_FILE, new TypeToken<ArrayList<String>>(){}.getType());
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private <T> List<T> loadData(String filename, Type type) {
        if (!Files.exists(Paths.get(filename))) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            List<T> list = gson.fromJson(reader, type);
            return list == null ? new ArrayList<>() : list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private <T> void saveData(String filename, List<T> data) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Aircraft methods
    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void addAircraft(Aircraft aircraft) {
        aircrafts.add(aircraft);
        saveAircraftData();
    }

    public void removeAircraft(Aircraft aircraft) {
        aircrafts.remove(aircraft);
        saveAircraftData();
    }

    public void updateAircraft(Aircraft aircraft) {
        saveAircraftData();
    }

    public void saveAircraftData() {
        saveData(AIRCRAFT_FILE, aircrafts);
    }

    // Pilot methods
    public List<Pilot> getPilots() {
        return pilots;
    }

    public void addPilot(Pilot pilot) {
        pilots.add(pilot);
        savePilotData();
    }

    public void removePilot(Pilot pilot) {
        pilots.remove(pilot);
        savePilotData();
    }

    public void updatePilot(Pilot pilot) {
        savePilotData();
    }

    public void savePilotData() {
        saveData(PILOT_FILE, pilots);
    }

    // Deployment logs
    public List<String> getDeploymentLogs() {
        return deploymentLogs;
    }

    public void addDeploymentLog(String log) {
        deploymentLogs.add(log);
        saveDeploymentLogData();
    }

    public void saveDeploymentLogData() {
        saveData(DEPLOYMENT_LOG_FILE, deploymentLogs);
    }

    // Save all data
    public void saveAll() {
        saveAircraftData();
        savePilotData();
        saveDeploymentLogData();
    }
}
