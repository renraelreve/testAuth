package sctp.ntu.booking_api.entities;

public class Booking {
    private int id;
    private int idShowtime;
    private int idUser;
    private int bookSeats;

    // Constructor
    public Booking(int id, int idShowtime, int idUser, int bookSeats) {
        this.id = id;
        this.idShowtime = idShowtime;
        this.idUser = idUser;
        this.bookSeats = bookSeats;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdShowtime() {
        return idShowtime;
    }

    public void setIdShowtime(int idShowtime) {
        this.idShowtime = idShowtime;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getBookSeats() {
        return bookSeats;
    }

    public void setBookSeats(int bookSeats) {
        this.bookSeats = bookSeats;
    }
}
