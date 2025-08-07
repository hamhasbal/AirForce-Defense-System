package app.models;

public class Pilot {
    private String id;
    private String name;
    private String rank;
    private String assignedAircraftId; // Optional
    private boolean active;

    public Pilot(String id, String name, String rank, String assignedAircraftId, boolean active) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.assignedAircraftId = assignedAircraftId;
        this.active = active;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }

    public String getAssignedAircraftId() { return assignedAircraftId; }
    public void setAssignedAircraftId(String assignedAircraftId) { this.assignedAircraftId = assignedAircraftId; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() { return name; }
}
