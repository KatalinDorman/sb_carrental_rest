package homework.sb_carrental_rest.db;

import homework.sb_carrental_rest.model.Car;
import homework.sb_carrental_rest.model.Reservation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class Database {

    SessionFactory sessionFactory;

    public Database() {
        Configuration cfg = new Configuration();
        cfg.configure();

        sessionFactory = cfg.buildSessionFactory();
    }

    public List<Car> getAvailableCarList(LocalDate startDate, LocalDate endDate) {
        List<Car> carList = null;

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery(
                """
                        SELECT c
                        FROM Car c
                        WHERE c.available = ?1
                        AND c.id NOT IN
                            (SELECT r.carId
                             FROM Reservation r
                             WHERE (?2 BETWEEN r.startDate AND r.endDate)
                             OR (?3 BETWEEN r.startDate AND r.endDate))
                        """);
        query.setParameter(1, true);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        carList = query.getResultList();

        tx.commit();
        session.close();

        return carList;
    }

    public Car getCarById(int id) {
        Car car = null;

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        car = session.get(Car.class, id);

        tx.commit();
        session.close();

        return car;
    }

    public void saveNewReservation(Reservation reservation) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.persist(reservation);

        tx.commit();
        session.close();
    }

    public List<Car> getAllCars() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery(
                """
                        SELECT c
                        FROM Car c
                        """);
        List<Car> carList = query.getResultList();

        tx.commit();
        session.close();

        return carList;
    }

    public List<Reservation> getAllReservations() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery(
                """
                        SELECT r
                        FROM Reservation r
                        """);
        List<Reservation> reservationList = query.getResultList();

        tx.commit();
        session.close();

        return reservationList;
    }

    public void saveCar(Car car) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        if (car.getId() == 0) {
            session.persist(car);
        } else {
            session.merge(car);
        }

        tx.commit();
        session.close();
    }

    public List<Car> getAllReservedCars() {
        List<Car> carList = null;

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery(
                """
                        SELECT c
                        FROM Car c
                        WHERE c.id IN
                             (SELECT r.carId
                              FROM Reservation r)
                        """);
        carList = query.getResultList();

        tx.commit();
        session.close();

        return carList;
    }
}
