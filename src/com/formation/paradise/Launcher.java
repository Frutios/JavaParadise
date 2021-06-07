package com.formation.paradise;

import com.formation.paradise.dao.DaoFactory;
import com.formation.paradise.dao.jdbc.JdbcPlaceDao;
import com.formation.paradise.dao.jdbc.JdbcTripDao;
import com.formation.paradise.model.Place;
import com.formation.paradise.model.Trip;
import com.formation.paradise.util.ConnectionManager;

import java.util.List;
import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {

      scan();

      ConnectionManager.closeConnection();

    }

    public static void scan(){

        Scanner sc = new Scanner(System.in);
        JdbcPlaceDao jpd = DaoFactory.getPlaceDao();
        JdbcTripDao jtd = DaoFactory.getTripDao();
        System.out.println("Welcome aboard !");
        System.out.println("What do you want to do ?");
        System.out.println("0 - Display all places");
        System.out.println("1 - Add a place");
        System.out.println("2 - Find a place");
        System.out.println("3 - Edit a place");
        System.out.println("4 - Remove a place");
        System.out.println("5 - Display all trips");
        System.out.println("6 - Add a trip");
        System.out.println("7 - Find a trip");
        System.out.println("8 - Edit a trip");
        System.out.println("9 - Remove a trip");


        int choice = sc.nextInt();

        if (choice == 0){
            List<Place> places = jpd.findAll();
            for (Place p : places){
                System.out.println(p.getId() + " - " + p.getName());
            }
        } else if (choice == 1){
            System.out.println("Add a place please !");
            String name = sc.next();
            Place place = new Place(name);
            jpd.create(place);
            System.out.println("You've added " + place.getName());
        } else if (choice == 2){
            System.out.println("What is the id of the place you are looking for ?");
            Long id = sc.nextLong();
            Place findById = jpd.findById(id);
            System.out.println("The place with the id " + findById.getId() + " is " + findById.getName());
        } else if (choice == 3){
            System.out.println("Rename a place, choose the id of the place");
            Long id = sc.nextLong();
            Place findById = jpd.findById(id);
            System.out.println("Rename the place now !");
            String name = sc.next();
            findById.setName(name);
            jpd.update(findById);
            System.out.println("The place has been renamed with the name " + findById.getName());
        } else if (choice == 4 ){
            System.out.println("Remove a place, choose the id of the place");
            Long id = sc.nextLong();
            Place findById = jpd.findById(id);
            jpd.delete(findById.getId());
            System.out.println("The place " + findById.getName() + " has been removed.");
        } else if (choice == 5){
            List<Trip> trips = jtd.findAll();
            for (Trip t : trips){
                System.out.println(t.getId() + " - Trip from " + t.getDeparture().getName()
                        + " to " + t.getDestination().getName() + " a the cost of " + t.getPrice() + "$");
            }

        } else if (choice == 6){
            System.out.println("Add a trip please, choose a departure place id!");
            List<Place> places = jpd.findAll();
            for (Place p : places){
                System.out.println(p.getId() + " - " + p.getName());
            }
            Long departureId = sc.nextLong();
            Place departurePlaceId = jpd.findById(departureId);
            System.out.println("Choose a destination place id!");
            Long destinationId = sc.nextLong();
            Place destinationPlaceId = jpd.findById(destinationId);
            System.out.println("And the price !");
            float price = sc.nextFloat();

            Trip trip = new Trip(departurePlaceId, destinationPlaceId, price );
            jtd.create(trip);

        } else if (choice == 7){
            System.out.println("What is the id of the trip you are looking for ?");
            Long id = sc.nextLong();
            Trip findById = jtd.findById(id);
            System.out.println("The trip with the id " + findById.getId() + " is "
                    + findById.getDeparture().getName() + " - " + findById.getDestination().getName());

        } else if (choice == 8){
            System.out.println("Change the price, choose the id of the trip");
            Long id = sc.nextLong();
            Trip findById = jtd.findById(id);
            System.out.println("Change the price now !");
            Float price = sc.nextFloat();
            findById.setPrice(price);
            jtd.update(findById);
            System.out.println("The price has been changed for " + findById.getPrice() + "$");
        } else if (choice == 9){
            System.out.println("Remove a trip, choose the id of the trip");
            Long id = sc.nextLong();
            Trip findById = jtd.findById(id);
            jtd.delete(findById.getId());
            System.out.println("The trip " + findById.getDeparture()
                    + " - " + findById.getDestination() + " has been removed.");
        } else {
            sc.close();
        }





    }


}


