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
import java.util.UUID;
import lombok.extern.log4j.Log4j;

@Log4j
public class HotelCRUD implements CRUD<Hotel> {

  private static final CRUD<Hotel> hotelCRUD = new HotelCRUD();
  private HotelCRUD(){}
  public static CRUD<Hotel> getHotelCRUD(){
    return hotelCRUD;
  }

  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();


  @Override
  public Hotel searchObject(int hotelNum) {

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

        hotel = (Hotel)objectHotelIn.readObject();
        objectHotelIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      return hotel;
    }
  }

  @Override
  public Hotel searchUUIDObject(UUID uuid) {
    return searchObject(1);
  }

  @Override
  public String searchFileName(Hotel object) {

    File hotelFolderDirectory = new File("entSAVE/hotel_entities");
    String[] hotelList = hotelFolderDirectory.list();

    String fileName = null;
    for (String hotelFolderName : hotelList) {
      fileName = hotelFolderName;
    }
    return fileName;

  }

  @Override
  public void deleteObject(Hotel hotel) {
    //удаляем все внутренние этажи

    if (hotel.getFloorsID() != null && !hotel.getFloorsID().isEmpty()) {
      for (UUID uuid : hotel.getFloorsID()) {
        floorCRUD.deleteObject(floorCRUD.searchUUIDObject(uuid));

      }
    }

    Hotel object = searchObject(1);
    //todo возможно прийдется удалять еще привязанных гостей
    //удаляем файл
    File deleteFile = new File("entSAVE/hotel_entities/" + searchFileName(object));

    if (deleteFile != null) {
      log.info("file "+searchFileName(object)+" successfully deleted");
      deleteFile.delete();
    }

  }

  @Override
  public void saveObject(Hotel hotel) {
    try {
      FileOutputStream fileHotelOut = new FileOutputStream(
          "entSAVE/hotel_entities/"
              + "{" + hotel.getUuid() + "}-hotel.txt");

      ObjectOutputStream objectHotelOut = new ObjectOutputStream(fileHotelOut);
      objectHotelOut.writeObject(hotel);
      objectHotelOut.close();
      log.info("The "+hotel.getUuid()+"-hotel was successfully written to a file");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }


}
