package sctp.ntu.booking_api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "booking")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bid")
  private Integer bid;

  @Column(name = "seats")
  private int bookedSeats;

  // Constructor
  public Booking(Showtime showtime, User user, int bookedSeats) {
    this.showtime = showtime;
    this.user = user;
    this.bookedSeats = bookedSeats;
  }

  // https://stackoverflow.com/questions/20119142/jackson-multiple-back-reference-properties-with-name-defaultreference
  // JsonBackReference need to be named to avoid Multiple back-reference
  // properties with name 'defaultReference'
  @JsonBackReference(value = "showtime-booking")
  @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.MERGE)
  @JoinColumn(name = "showtime", referencedColumnName = "sid")
  private Showtime showtime;

  @JsonBackReference(value = "user-booking")
  @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.MERGE)
  // https://www.baeldung.com/jpa-joincolumn-vs-mappedby
  @JoinColumn(name = "username", referencedColumnName = "uid")
  private User user;

  // // Getters and Setters
  // public Integer getId() {
  // return bid;
  // }

  // public void setId(Integer bid) {
  // this.bid = bid;
  // }

  // public Integer getSId() {
  // return sid;
  // }

  // public void setSId(Integer sid) {
  // this.sid = sid;
  // }

  // public Integer getUId() {
  // return uid;
  // }

  // public void setUId(Integer uid) {
  // this.uid = uid;
  // }

  // public int getBookSeats() {
  // return bookSeats;
  // }

  // public void setBookSeats(int bookSeats) {
  // this.bookSeats = bookSeats;
  // }
}
