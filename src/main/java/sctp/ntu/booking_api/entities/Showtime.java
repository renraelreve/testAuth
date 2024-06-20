package sctp.ntu.booking_api.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "showtime")
public class Showtime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Integer sid;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_seats")
    private int totalSeats;

    @Column(name = "balance_seats")
    private int balSeats;

    // https://stackoverflow.com/questions/13370221/persistentobjectexception-detached-entity-passed-to-persist-thrown-by-jpa-and-h
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", referencedColumnName = "eid")
    private Event event;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Booking> booking;

    // Constructor
    public Showtime(LocalDate date, int totalSeats, int balSeats, Event event) {
        this.date = date;
        this.totalSeats = totalSeats;
        this.balSeats = balSeats;
        this.event = event;
    }

    public Showtime(LocalDate date, int totalSeats, int balSeats) {
        this.date = date;
        this.totalSeats = totalSeats;
        this.balSeats = balSeats;
    }

    // // Getters and Setters
    // public Integer getId() {
    //     return sid;
    // }

    // public void setId(Integer sid) {
    //     this.sid = sid;
    // }

    // public String getDate() {
    //     return date;
    // }

    // public void setDate(String date) {
    //     this.date = date;
    // }

    // public Integer getEId() {
    //     return eid;
    // }

    // public void setEId(Integer eid) {
    //     this.eid = eid;
    // }

    // public int getTotalSeats() {
    //     return totalSeats;
    // }

    // public void setTotalSeats(int totalSeats) {
    //     this.totalSeats = totalSeats;
    // }

    // public int getBalSeats() {
    //     return balSeats;
    // }

    // public void setBalSeats(int balSeats) {
    //     this.balSeats = balSeats;
    // }
}
