package Dao;

import Entity.Reservation;
import Exception.ReservationException;
import Exception.DatabaseConnectionException;
import java.util.List;

public interface IReservationService {
    void createReservation(Reservation reservationData) throws DatabaseConnectionException;
    Reservation getReservationById(int reservationId) throws ReservationException, DatabaseConnectionException;
    List<Reservation> getReservationsByCustomerId(int customerId) throws DatabaseConnectionException;
    void updateReservation(Reservation reservationData) throws ReservationException, DatabaseConnectionException;
    void cancelReservation(int reservationId) throws ReservationException, DatabaseConnectionException;
}
