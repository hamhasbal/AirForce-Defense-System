package app.models;

public class Aircraft {
    private String id;
    private String type;
    private String armament;
    private boolean available;

    public Aircraft(String id, String type, String armament, boolean available) {
        this.id = id;
        this.type = type;
        this.armament = armament;
        this.available = available;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getArmament() { return armament; }
    public void setArmament(String armament) { this.armament = armament; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return type + " (" + id + ")";
    }
}
