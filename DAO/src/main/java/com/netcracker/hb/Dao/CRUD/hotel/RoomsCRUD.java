package com.netcracker.hb.Dao.CRUD.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.DatabaseProperties;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.GuestCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IEmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.IGuestCRUD;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Guest;
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
public class RoomsCRUD implements CRUD<Room> {

  private static CRUD<Room> roomsCRUD;

  private RoomsCRUD() {
  }

  public static synchronized CRUD<Room> getRoomsCRUD() {
    if(roomsCRUD == null){
      roomsCRUD = new RoomsCRUD();
    }
    return roomsCRUD;
  }

  private static final String START = "<Start searching room....";
  private static final String END = "Room was found>";
  private static final String ERROR = "Room was not found>";


  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();


  @Override
  public List<Room> searchObjects() {
    log.info(START);

    File roomFolderDirectory = new File(DatabaseProperties.getRoomCrudEntitiesPath());
    String[] roomList = roomFolderDirectory.list();
    List<Room> rooms = new ArrayList<>();
    assert roomList != null;
    for (String roomFolderName : roomList) {
      try (
          FileInputStream fileRoomIn = new FileInputStream(
              DatabaseProperties.getRoomCrudEntitiesPath() + roomFolderName);
          ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
      ) {
        Room object = (Room) objectRoomIn.readObject();
        rooms.add(object);

      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (rooms.isEmpty()) {
      log.error(ERROR);
    }
    return rooms;
  }

  @Override
  public Room searchObjectNum(int roomNum) {
    log.info(START);

    File roomFolderDirectory = new File(DatabaseProperties.getRoomCrudEntitiesPath());
    String[] roomList = roomFolderDirectory.list();
    Room room = null;
    assert roomList != null;
    for (String roomFolderName : roomList) {
      try (

          FileInputStream fileRoomIn = new FileInputStream(
              DatabaseProperties.getRoomCrudEntitiesPath() + roomFolderName);
          ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
      ) {
        Room object = (Room) objectRoomIn.readObject();
        //если номер комнаты совпадает то передаем
        if (object.getRoomNum() == roomNum) {
          log.info(END);
          room = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (room == null) {
      log.error(ERROR);
    }
    return room;
  }

  @Override
  public Room searchUUIDObject(UUID uuid) {

    log.info(START);
    if(uuid == null){
      return null;
    }

    File roomFolderDirectory = new File(DatabaseProperties.getRoomCrudEntitiesPath());
    String[] roomList = roomFolderDirectory.list();
    Room room = null;
    assert roomList != null;
    for (String roomFolderName : roomList) {
      try (

          FileInputStream fileRoomIn = new FileInputStream(
              DatabaseProperties.getRoomCrudEntitiesPath() + roomFolderName);
          ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
      ) {
        Room object = (Room) objectRoomIn.readObject();
        //если ююайди комнаты совпадает то передаем
        if (object.getUuid().equals(uuid)) {
          log.info(END);
          room = object;
        }
      } catch (ClassNotFoundException | IOException exception) {
        exception.printStackTrace();
      }
    }
    if (room == null) {
      log.error(ERROR);
    }
    return room;
  }

  @Override
  public String searchFileName(Room room) {
    log.info(START);
    File roomFolderDirectory = new File(DatabaseProperties.getRoomCrudEntitiesPath());
    String[] roomList = roomFolderDirectory.list();
    String fileName = null;
    assert roomList != null;
    for (String roomFolderName : roomList) {
      try (

          FileInputStream fileRoomIn = new FileInputStream(
              DatabaseProperties.getRoomCrudEntitiesPath() + roomFolderName);
          ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
      ) {
        Room object = (Room) objectRoomIn.readObject();
        if (object.equals(room)) {
          log.info(END);
          fileName = roomFolderName;
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
  public void deleteObject(Room room) {
    log.info("<Start delete room...");

    Room object = searchObjectNum(room.getRoomNum());

    //удаляем из хешлиста привязанного флура
    Floor floor = floorCRUD.searchUUIDObject(object.getFloorId());
    floor.deleteRooms(object.getUuid());
    floorCRUD.saveObject(floor);

    //удаляем всех гостей и работников
    for (UUID uuidEmployee : room.getEmployeeID()) {
      Employee employee = employeeCRUD.searchUUIDObject(uuidEmployee);
      employee.deleteRoomsID(room.getUuid());
      employeeCRUD.saveObject(employee);
    }

    for (UUID uuidGuest : room.getGuestID()) {
      Guest guest = guestCRUD.searchUUIDObject(uuidGuest);
      guest.setRoomID(null);
      guestCRUD.saveObject(guest);
    }

    object = searchObjectNum(room.getRoomNum());
    //удаляем файл
    File deleteFile = new File(
        DatabaseProperties.getRoomCrudEntitiesPath() + searchFileName(object));
    if (deleteFile.delete()) {
      log.info("Room was successfully deleted>");
    } else {
      log.warn(ERROR);
    }
  }

  @Override
  public void saveObject(Room room) {
    log.info("<Start saving room...");
    try (

        FileOutputStream fileRoomOut = new FileOutputStream(
            DatabaseProperties.getRoomCrudEntitiesPath()
                + room.getUuid() + "-room.txt");
        ObjectOutputStream objectRoomOut = new ObjectOutputStream(fileRoomOut);
    ) {
      objectRoomOut.writeObject(room);
      log.info("Success saving room>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

}
