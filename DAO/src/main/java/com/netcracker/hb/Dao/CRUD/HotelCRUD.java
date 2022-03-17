package com.netcracker.hb.Dao.CRUD;


import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import com.netcracker.hb.entities.hotel.Room;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HotelCRUD implements CRUD {

  private Hotel hotel;
  private Set<Floor> floors;
  private Set<Room> room;


  public int floorChoice() {
    int floorNum, correctChoise;
    Scanner in = new Scanner(System.in);
    do {
      log.info("write number of floors");
      floorNum = in.nextInt();
      if (floorNum <= 0) {
        log.info("0 is not a good choice");
        correctChoise = 0;
      } else if (floorNum > 100) {
        log.info("Dear user, our builders cant make it so big");
        correctChoise = 0;
      } else {
        log.info("start creating floors...");
        correctChoise = 1;
      }

    } while (correctChoise != 1);
    return floorNum;

  }

  @Override
  public void addObject() {

    if (searchObject(1) == null) {

      log.info("Hotel was not created \n"
          + "Start creating base hotel...");

      //создаем базовый пустой отель
      hotel = Hotel.builder()
          .floors(new HashSet<>())
          .uuid(UUID.randomUUID())
          .build();
      log.info("hotel with empty floors created\n"
          + "How many floors do u wanna add to hotel?");

      //пихаем туда этажи
      for (int i = 0; i < floorChoice(); i++) {
        FloorCRUD floorCRUD = new FloorCRUD();
        floorCRUD.addObject();
      }

      //записывае отель в файл
      saveObject(hotel);

    } else{
      log.info("hotel is already created");
    }

  }

  @Override
  public void displayObject() {

  }

  @Override
  public Hotel searchObject(int hotelNum) {

    File hotelFolderDirectory = new File(
        "D:\\прогали\\Netcrack\\Project-Hotel-Administration\\entSAVE\\hotel_entities\\");
    String[] hotelList = hotelFolderDirectory.list();

    Object hotel = null;
    try {
      for (String hotelFolderName : hotelList) {
        FileInputStream fileHotelIn = new FileInputStream(
            "D:\\прогали\\Netcrack\\Project-Hotel-Administration\\entSAVE\\hotel_entities\\"
                + hotelFolderName);
        ObjectInputStream objectHotelIn = new ObjectInputStream(fileHotelIn);

        hotel = objectHotelIn.readObject();
        objectHotelIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      return (Hotel) hotel;
    }
  }

  @Override
  public void deleteObject() {

  }

  @Override
  public void changeObject() {

  }

  public void saveObject(Hotel hotel) {
    try {
      FileOutputStream fileHotelOut = new FileOutputStream(
          "D:\\прогали\\Netcrack\\Project-Hotel-Administration\\entSAVE\\floor_entities\\"
              + "{" + hotel.getUuid() + "}-hotel.txt");

      ObjectOutputStream objectFloorOut = new ObjectOutputStream(fileHotelOut);
      objectFloorOut.writeObject(hotel);
      objectFloorOut.close();
      log.info(
          "The {" + hotel.getUuid() + "}-hotel was successfully written to a file");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }


}
