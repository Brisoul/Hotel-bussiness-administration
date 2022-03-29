package com.netcracker.hb.Dao.crud.hotel;

import com.netcracker.hb.Dao.crud.CRUD;
import com.netcracker.hb.Dao.crud.DatabaseProperties;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import com.netcracker.hb.entities.hotel.Room;
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
public class FloorCRUD implements CRUD<Floor> {

  private static CRUD<Floor> floorCRUD;

  private FloorCRUD() {
  }

  public static synchronized CRUD<Floor> getFloorCRUD() {
    if (floorCRUD == null) {
      floorCRUD = new FloorCRUD();
    }
    return floorCRUD;
  }

  private static final String START = "<Start searching floors....";
  private static final String END = "Floors was found>";
  private static final String ERROR = "Floors was not found>";


  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();
  private static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();


  @Override
  public List<Floor> searchObjects() {
    log.info(START);
    File floorFolderDirectory = new File(DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH);
    String[] floorList = floorFolderDirectory.list();
    List<Floor> floors = new ArrayList<>();
    if (floorList == null) {
      return null;
    }
    for (String floorFolderName : floorList) {
      try (
          FileInputStream fileFloorIn = new FileInputStream(
              DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH + floorFolderName);
          ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn)) {
        Floor object = (Floor) objectFloorIn.readObject();
        floors.add(object);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    if (floors.isEmpty()) {
      log.error(ERROR);
    }
    return floors;
  }

  @Override
  public Floor searchObjectNum(int floorNum) {

    log.info(START);
    File floorFolderDirectory = new File(DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH);
    String[] floorList = floorFolderDirectory.list();
    Floor floor = null;
    if (floorList == null) {
      return null;
    }
    for (String floorFolderName : floorList) {
      try (
          FileInputStream fileFloorIn = new FileInputStream(
              DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH + floorFolderName);
          ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn)) {
        Floor object = (Floor) objectFloorIn.readObject();
        //если номер этажа совпадает то передаем
        if (object.getFloorNum() == floorNum) {
          log.info(END);
          floor = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (floor == null) {
      log.error(ERROR);
    }
    return floor;

  }

  @Override
  public Floor searchUUIDObject(UUID uuid) {
    log.info(START);
    if (uuid == null) {
      return null;
    }
    File floorFolderDirectory = new File(DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH);
    String[] floorList = floorFolderDirectory.list();
    Floor floor = null;

    if (floorList == null) {
      return null;
    }
    for (String floorFolderName : floorList) {
      try (
          FileInputStream fileFloorIn = new FileInputStream(
              DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH + floorFolderName);
          ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn)) {
        Floor object = (Floor) objectFloorIn.readObject();
        if (object.getUuid().equals(uuid)) {
          log.info(END);
          floor = object;
        }
      } catch (IOException |
          ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (floor == null) {
      log.error(ERROR);
    }
    return floor;
  }

  @Override
  public String searchFileName(Floor floor) {

    log.info(START);

    File floorFolderDirectory = new File(DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH);
    String[] floorList = floorFolderDirectory.list();
    String fileName = null;

    if (floorList == null) {
      return null;
    }
    for (String floorFolderName : floorList) {
      try (
          FileInputStream fileFloorIn = new FileInputStream(
              DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH + floorFolderName);
          ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn)) {
        Floor object = (Floor) objectFloorIn.readObject();

        if (object.equals(floor)) {
          log.info(END);
          fileName = floorFolderName;
        }

      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (fileName == null) {
      log.error(ERROR);
    }
    return fileName;

  }

  @Override
  public void deleteObject(Floor object) {
    log.info("<Start delete floor...");

    Floor floor = searchObjectNum(object.getFloorNum());
    if (floor != null) {

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
      //удаляем файл
      File deleteFile = new File(
          DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH + searchFileName(floor));
      if (deleteFile.delete()) {
        log.info("Floor was successfully deleted>");
      }
    } else {
      log.warn(ERROR);
    }
  }

  @Override
  public void saveObject(Floor floor) {
    log.info("<Start saving floor...");

    try (
        FileOutputStream fileFloorOut = new FileOutputStream(
            DatabaseProperties.FLOOR_CRUD_ENTITIES_PATH + floor.getUuid() + "-floor.txt");

        ObjectOutputStream objectFloorOut = new ObjectOutputStream(fileFloorOut)) {
      objectFloorOut.writeObject(floor);
      log.info("Success saving floor>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


}
