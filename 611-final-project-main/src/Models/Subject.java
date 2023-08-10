package Models;

public interface Subject {
    /**
     * interface method to add observer
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * interface method to remove observer
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * interface method to notify observers
     * @param message
     */
    void notifyObservers(String message);
}
