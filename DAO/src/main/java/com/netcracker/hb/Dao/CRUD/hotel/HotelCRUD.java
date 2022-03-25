package com.netcracker.hb.Dao.CRUD.hotel;


import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j;

@Log4j
public class HotelCRUD implements CRUD<Hotel> {

  private static final CRUD<Hotel> hotelCRUD = new HotelCRUD();

  private HotelCRUD() {
  }

  public static CRUD<Hotel> getHotelCRUD() {
    return hotelCRUD;
  }

  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();


  @Override
  public List<Hotel> searchObjects() {
    List hotels = new ArrayList();
    hotels.add(searchObjectNum(1));
    return hotels;
  }

  @Override
  public Hotel searchObjectNum(int hotelNum) {
    log.info("<Start searching hotel...");

    File hotelFolderDirectory = new File(
        "entSAVE/hotel_entities");
    String[] hotelList = hotelFolderDirectory.list();

    Hotel hotel = null;
    try {
      for (String hotelFolderName : hotelList) {

        FileInputStream fileHotelIn = new FileInputStream(
            "entSAVE/hotel_entities/"
                + hotelFolderName);
        ObjectInputStream objectHotelIn = new ObjectInputStream(fileHotelIn);

        Hotel object = (Hotel) objectHotelIn.readObject();
        if (object != null) {
          log.info("Hotel was found>");
          hotel = object;
        }
        objectHotelIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (hotel == null) {
        log.error("HOTEL NOT FOUND>");
      }
      return hotel;
    }
  }

  @Override
  public Hotel searchUUIDObject(UUID uuid) {
    return searchObjectNum(1);
  }

  @Override
  public String searchFileName(Hotel object) {
    log.info("<Start searching file name of hotel...");

    File hotelFolderDirectory = new File("entSAVE/hotel_entities");
    String[] hotelList = hotelFolderDirectory.list();

    String fileName = null;
    for (String hotelFolderName : hotelList) {
      fileName = hotelFolderName;
      log.info("File name of hotel was found>");
    }
    if (fileName == null) {
      log.error("FILE NAME OF HOTEL NOT FOUND>");
    }
    return fileName;

  }

  @Override
  public void deleteObject(Hotel hotel) {
    log.info("<Start delete hotel...");
    //удаляем все внутренние этажи

    if (hotel.getFloorsID() != null && !hotel.getFloorsID().isEmpty()) {
      for (UUID uuid : hotel.getFloorsID()) {
        floorCRUD.deleteObject(floorCRUD.searchUUIDObject(uuid));

      }
    }

    Hotel object = searchObjectNum(1);
    //todo возможно прийдется удалять еще привязанных гостей
    //удаляем файл
    File deleteFile = new File("entSAVE/hotel_entities/" + searchFileName(object));

    if (deleteFile != null) {
      log.info("hotel was successfully deleted>");
      deleteFile.delete();
    } else {
      log.warn("NOTHING WAS DELETED, FILE HOTEL NOT FOUND>");
    }

  }

  @Override
  public void saveObject(Hotel hotel) {
    log.info("<Start saving hotel...");
    try {
      FileOutputStream fileHotelOut = new FileOutputStream("entSAVE/hotel_entities/"
          + hotel.getUuid() + "-hotel.txt");
      ObjectOutputStream objectHotelOut = new ObjectOutputStream(fileHotelOut);
      objectHotelOut.writeObject(hotel);
      objectHotelOut.close();
      log.info("Success saving hotel>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }


}
