package sctp.ntu.booking_api.entities;

public class Showtime {
    private int id;
    private String date;
    private int idEvents;
    private int totalSeats;
    private int balSeats;

    // Constructor
    public Showtime(int id, String date, int idEvents, int totalSeats, int balSeats) {
        this.id = id;
        this.date = date;
        this.idEvents = idEvents;
        this.totalSeats = totalSeats;
        this.balSeats = balSeats;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdEvents() {
        return idEvents;
    }

    public void setIdEvents(int idEvents) {
        this.idEvents = idEvents;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getBalSeats() {
        return balSeats;
    }

    public void setBalSeats(int balSeats) {
        this.balSeats = balSeats;
    }
}
