package com.netcracker.hb.Dao.CRUD.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class FloorCRUD implements CRUD<Floor> {

  private static final CRUD<Floor> floorCRUD = new FloorCRUD();
  private FloorCRUD(){}
  public static CRUD<Floor> getFloorCRUD(){
    return floorCRUD;
  }

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();
  private static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();


  @Override
  public List<Floor> searchObjects() {
    log.info("<Start searching floors....");
    File floorFolderDirectory = new File("entSAVE/floor_entities");
    String[] floorList = floorFolderDirectory.list();
    List floors = new ArrayList();
    try {
      for (String floorFolderName : floorList) {

        FileInputStream fileFloorIn = new FileInputStream("entSAVE/floor_entities/"
            + floorFolderName);
        ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn);
        Floor object = (Floor) objectFloorIn.readObject();
        //если номер этажа совпадает то передаем
        floors.add(object);
        objectFloorIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(floors.isEmpty()){
        log.error("FLOORS NOT FOUND>");
      }
      return floors;
    }
  }

  @Override
  public Floor searchObjectNum(int floorNum) {

    log.info("<Start searching floor....");
    File floorFolderDirectory = new File("entSAVE/floor_entities");
    String[] floorList = floorFolderDirectory.list();
    Floor floor = null;
    try {
      for (String floorFolderName : floorList) {

        FileInputStream fileFloorIn = new FileInputStream("entSAVE/floor_entities/"
            + floorFolderName);
        ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn);
        Floor object = (Floor) objectFloorIn.readObject();
        //если номер этажа совпадает то передаем
        if (object.getFloorNum() == floorNum) {
          log.info("Floor was found>");
          floor = object;
        }
        objectFloorIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(floor ==null){
        log.error("FLOOR NOT FOUND>");
      }
      return floor;
    }

  }

  @Override
  public Floor searchUUIDObject(UUID uuid) {
    log.info("<Start searching floor....");
    File floorFolderDirectory = new File("entSAVE/floor_entities");
    String[] floorList = floorFolderDirectory.list();
    Floor floor = null;
    try {
      for (String floorFolderName : floorList) {

        FileInputStream fileFloorIn = new FileInputStream("entSAVE/floor_entities/"
            + floorFolderName);
        ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn);
        Floor object = (Floor) objectFloorIn.readObject();
        //если номер этажа совпадает то передаем
        if (object.getUuid().equals(uuid)) {
          log.info("Floor was found>");
          floor = object;
        }
        objectFloorIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(floor ==null){
        log.error("FLOOR NOT FOUND>");
      }
      return floor;
    }
  }

  @Override
  public String searchFileName(Floor floor) {

    log.info("<Start searching file name of floor...");

    File floorFolderDirectory = new File("entSAVE/floor_entities");
    String[] floorList = floorFolderDirectory.list();
    String fileName = null;
    try {
      for (String floorFolderName : floorList) {

        FileInputStream fileFloorIn = new FileInputStream("entSAVE/floor_entities/"
            + floorFolderName);
        ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn);
        Floor object = (Floor) objectFloorIn.readObject();

        if (object.equals(floor)) {
          log.info("File name of floor was found>");
          fileName = floorFolderName;
        }
        objectFloorIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(fileName == null){
        log.error("FILE NAME OF FLOOR NOT FOUND>");
      }
      return fileName;
    }

  }

  @Override
  public void deleteObject(Floor object) {
    log.info("<Start delete floor...");

    Floor floor = searchObjectNum(object.getFloorNum());

    //отвязываем от отеля
    Hotel hotel = hotelCRUD.searchUUIDObject(floor.getUuid());
    hotel.deleteFloor(floor.getUuid());
    hotelCRUD.saveObject(hotel);

    //удаляем все внутренние комнаты
    if (floor.getRoomsID() != null) {
      for (UUID uuid : floor.getRoomsID()) {
        roomsCRUD.deleteObject(roomsCRUD.searchUUIDObject(uuid));
      }
    }
    //Сохраняем
    floor = searchObjectNum(object.getFloorNum());

    //todo возможно прийдется удалять еще привязанных гостей
    //удаляем файл
    File deleteFile = new File("entSAVE/floor_entities/" + searchFileName(floor));

    if (deleteFile != null) {
      log.info("Floor was successfully deleted>" );
      deleteFile.delete();
    } else{
      log.warn("NOTHING WAS DELETED, FILE FLOOR NOT FOUND>");
    }
  }

  @Override
  public void saveObject(Floor floor) {
    log.info("<Start saving floor...");

    try {
      FileOutputStream fileFloorOut = new FileOutputStream("entSAVE/floor_entities/"
           + floor.getUuid() + "-floor.txt");

      ObjectOutputStream objectFloorOut = new ObjectOutputStream(fileFloorOut);
      objectFloorOut.writeObject(floor);
      objectFloorOut.close();
      log.info("Success saving floor>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


}
