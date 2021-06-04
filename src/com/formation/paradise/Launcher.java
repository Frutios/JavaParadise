package com.formation.paradise;

import com.formation.paradise.dao.DaoFactory;
import com.formation.paradise.dao.jdbc.JdbcPlaceDao;
import com.formation.paradise.model.Place;
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
        System.out.println("Welcome aboard !");
        System.out.println("What do you want to do ?");
        System.out.println("0 - Display all places");
        System.out.println("1 - Add a place");
        System.out.println("2 - Find a place");
        System.out.println("3 - Edit a place");
        System.out.println("4 - Remove a place");

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
        }



    }


}


