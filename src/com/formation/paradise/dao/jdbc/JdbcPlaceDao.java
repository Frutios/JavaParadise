package com.formation.paradise.dao.jdbc;

import com.formation.paradise.dao.CrudDao;
import com.formation.paradise.model.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPlaceDao extends JdbcDao implements CrudDao<Long, Place> {

    @Override
    public Connection getConnection() {
        return super.getConnection();
    }

    @Override
    public Place create(Place placeToCreate) {
        Place createdPlace = null;
        String query = "INSERT INTO place (name) VALUES (?)";
        getConnection();
        try(PreparedStatement pst = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, placeToCreate.getName());
            pst.execute();

            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            Long id = rs.getLong(1);
            createdPlace = findById(id);

            connection.commit();
        } catch (SQLException e1){
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return createdPlace;
    }

    @Override
    public Place findById(Long id) {
        String query = "SELECT * FROM place WHERE id = ?";
        Place foundPlace = null;
        try(PreparedStatement pst = getConnection().prepareStatement(query)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()){
                foundPlace = mapToPlace(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return foundPlace;
    }

    private Place mapToPlace(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        Long id = rs.getLong("id");
        return new Place(name,id);
    }

    @Override
    public List<Place> findAll() {

        List<Place> places = new ArrayList<>();
        String query = "SELECT * FROM place";
        try(Statement st = getConnection().createStatement()){
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                places.add(mapToPlace(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return places;
    }

    @Override
    public boolean update(Place placeToUpdate) {
        int updateRows = 0;
        String query = "UPDATE place SET name = ? WHERE id=?";
        getConnection();
        try(PreparedStatement pst = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            pst.setString(1, placeToUpdate.getName());
            pst.setLong(2, placeToUpdate.getId());

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
        String query = "DELETE FROM place WHERE id=?";
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
