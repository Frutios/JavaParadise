package com.formation.paradise.dao.jdbc;

import com.formation.paradise.dao.CrudDao;
import com.formation.paradise.model.Place;
import com.formation.paradise.model.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTripDao extends JdbcDao implements CrudDao<Long, Trip> {

    @Override
    public Connection getConnection() {
        return super.getConnection();
    }


    @Override
    public Trip create(Trip tripToCreate) {
        Trip createdTrip = null;
        String query = "INSERT INTO trip (departure, destination, price) VALUES (?, ?, ?)";
        getConnection();
        try(PreparedStatement pst = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setLong(1, tripToCreate.getDeparture().getId());
            pst.setLong(2, tripToCreate.getDestination().getId());
            pst.setFloat(3, tripToCreate.getPrice());
            pst.execute();

            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            Long id = rs.getLong(1);
            createdTrip = findById(id);

            connection.commit();
        } catch (SQLException e1){
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return createdTrip;
    }

    @Override
    public Trip findById(Long id) {

        String query = "SELECT * FROM trip WHERE id = ?";
        Trip foundTrip = null;
        try(PreparedStatement pst = getConnection().prepareStatement(query)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()){
                foundTrip = mapToTrip(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return foundTrip;
    }

    private Trip mapToTrip(ResultSet rs) throws SQLException {
        JdbcPlaceDao jpd = new JdbcPlaceDao();
        Place departure = jpd.findById(rs.getLong("departure"));
        Place destination = jpd.findById (rs.getLong("destination"));
        float price = rs.getFloat("price");
        Long id = rs.getLong("id");
        return new Trip(id, destination, departure, price);
    }

    @Override
    public List<Trip> findAll() {
        List<Trip> trips = new ArrayList<>();
        String query = "SELECT * FROM trip";
        try(Statement st = getConnection().createStatement()){
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                trips.add(mapToTrip(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return trips;
    }

    @Override
    public boolean update(Trip tripToUpdate) {
        int updateRows = 0;
        String query = "UPDATE trip SET departure = ?, destination = ?, price = ? WHERE id=?";
        getConnection();
        try(PreparedStatement pst = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            pst.setObject(1, tripToUpdate.getDeparture());
            pst.setObject(2, tripToUpdate.getDestination());
            pst.setFloat(3, tripToUpdate.getPrice());
            pst.setLong(4, tripToUpdate.getId());

            updateRows = pst.executeUpdate();
            connection.commit();
        } catch (SQLException e1){
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return updateRows > 0;
    }

    @Override
    public boolean delete(Long id) {
        boolean isDeleted = false;
        String query = "DELETE FROM trip WHERE id=?";
        getConnection();
        try (PreparedStatement pst = getConnection().prepareStatement(query)){
            pst.setLong(1, id);
            isDeleted = pst.execute();
            connection.commit();
        } catch (SQLException e1){
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return isDeleted;
    }
}
