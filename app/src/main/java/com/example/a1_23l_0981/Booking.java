package com.example.a1_23l_0981;

public class Booking {

    public String bookingId;
    public String userId;
    public String movieName;
    public int seats;
    public int totalPrice;
    public String dateTime;
    public long timestamp;
    public String poster;

    public Booking() {
        // required for Firebase
    }

    public Booking(String bookingId, String userId, String movieName,
                   int seats, int totalPrice, String dateTime, long timestamp, String poster) {
        this.bookingId  = bookingId;
        this.userId     = userId;
        this.movieName  = movieName;
        this.seats      = seats;
        this.totalPrice = totalPrice;
        this.dateTime   = dateTime;
        this.timestamp  = timestamp;
        this.poster     = poster;
    }
    public Booking(String bookingId, String userId, String movieName,
                   int seats, int totalPrice, String dateTime) {

        this.bookingId  = bookingId;
        this.userId     = userId;
        this.movieName  = movieName;
        this.seats      = seats;
        this.totalPrice = totalPrice;
        this.dateTime   = dateTime;

        this.timestamp = System.currentTimeMillis(); // auto set
        this.poster = ""; // default
    }
}