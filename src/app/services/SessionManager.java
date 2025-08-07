package app.services;

public class SessionManager {
    private static boolean unsavedChanges = false;

    // Call this whenever data changes
    public static void setUnsavedChanges(boolean hasChanges) {
        unsavedChanges = hasChanges;
    }

    // Check if there are unsaved changes
    public static boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    // Save user data to persistent storage
    public static void saveData() {
        // TODO: Implement actual saving logic here, e.g.:
        // DataManager.getInstance().saveAll();
        System.out.println("Saving user data...");
        unsavedChanges = false;
    }

    // Authenticate user credentials
    public static boolean authenticate(String username, String password) {
        // Example: hardcoded for demo
        return "admin".equals(username) && "password".equals(password);
    }
}
