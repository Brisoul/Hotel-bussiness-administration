package com.netcracker.hb.Dao.crud.hotel;


import com.netcracker.hb.Dao.crud.CRUD;
import com.netcracker.hb.Dao.crud.DatabaseProperties;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import java.io.File;
import java.io.FileInputStream;
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

  private static CRUD<Hotel> hotelCRUD;

  private HotelCRUD() {
  }

  public static synchronized CRUD<Hotel> getHotelCRUD() {
    if (hotelCRUD == null) {
      hotelCRUD = new HotelCRUD();
    }
    return hotelCRUD;
  }

  private static final String START = "<Start searching hotel....";
  private static final String END = "Hotel was found>";
  private static final String ERROR = "Hotel was not found>";


  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();


  @Override
  public List<Hotel> searchObjects() {
    List<Hotel> hotels = new ArrayList<>();
    hotels.add(searchObjectNum(1));
    return hotels;
  }

  @Override
  public Hotel searchObjectNum(int hotelNum) {
    log.info(START);

    File hotelFolderDirectory = new File(DatabaseProperties.HOTEL_CRUD_ENTITIES_PATH);
    String[] hotelList = hotelFolderDirectory.list();
    Hotel hotel = null;
    if (hotelList == null) {
      return null;
    }
    for (String hotelFolderName : hotelList) {
      try (
          FileInputStream fileHotelIn = new FileInputStream(
              DatabaseProperties.HOTEL_CRUD_ENTITIES_PATH
                  + hotelFolderName);
          ObjectInputStream objectHotelIn = new ObjectInputStream(fileHotelIn)) {

        Hotel object = (Hotel) objectHotelIn.readObject();
        if (object != null) {
          log.info(END);
          hotel = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (hotel == null) {
      log.error(ERROR);
    }
    return hotel;
  }

  @Override
  public Hotel searchUUIDObject(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    return searchObjectNum(1);
  }

  @Override
  public String searchFileName(Hotel object) {
    log.info(START);

    File hotelFolderDirectory = new File(DatabaseProperties.HOTEL_CRUD_ENTITIES_PATH);
    String[] hotelList = hotelFolderDirectory.list();

    String fileName = null;
    if (hotelList == null) {
      return null;
    }
    for (String hotelFolderName : hotelList) {
      fileName = hotelFolderName;
      log.info(END);
    }
    if (fileName == null) {
      log.error(ERROR);
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
    //удаляем файл
    File deleteFile = new File(
        DatabaseProperties.HOTEL_CRUD_ENTITIES_PATH + searchFileName(object));

    if (deleteFile.delete()) {
      log.info("Hotel was successfully deleted>");
    } else {
      log.warn(ERROR);
    }

  }

  @Override
  public void saveObject(Hotel hotel) {
    log.info("<Start saving hotel...");
    try (
        FileOutputStream fileHotelOut = new FileOutputStream(
            DatabaseProperties.HOTEL_CRUD_ENTITIES_PATH
                + hotel.getUuid() + "-hotel.txt");
        ObjectOutputStream objectHotelOut = new ObjectOutputStream(fileHotelOut)) {
      objectHotelOut.writeObject(hotel);
      log.info("Success saving hotel>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }


}
