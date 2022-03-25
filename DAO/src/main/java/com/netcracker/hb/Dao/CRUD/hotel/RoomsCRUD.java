package com.netcracker.hb.Dao.CRUD.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
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
public class RoomsCRUD implements CRUD<Room> {

  private static final CRUD<Room> roomsCRUD = new RoomsCRUD();
  private RoomsCRUD(){}
  public static CRUD<Room> getRoomsCRUD(){
    return roomsCRUD;
  }

  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final IGuestCRUD<Guest> guestCRUD = GuestCRUD.getGuestCRUD();


  @Override
  public List<Room> searchObjects() {
    log.info("<Start searching rooms....");

    File roomFolderDirectory = new File(
        "entSAVE/room_entities");
    String[] roomList = roomFolderDirectory.list();
    List rooms = new ArrayList();
    try {
      for (String roomFolderName : roomList) {

        FileInputStream fileRoomIn = new FileInputStream(
            "entSAVE/room_entities/"
                + roomFolderName);
        ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
        Room object = (Room) objectRoomIn.readObject();
        //если номер комнаты совпадает то передаем
        rooms.add(object);
        objectRoomIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(rooms.isEmpty()){
        log.error("ROOMS NOT FOUND>");
      }
      return rooms;
    }
  }

  @Override
  public Room searchObjectNum(int roomNum) {
    log.info("<Start searching room....");

    File roomFolderDirectory = new File(
        "entSAVE/room_entities");
    String[] roomList = roomFolderDirectory.list();
    Object room = null;
    try {
      for (String roomFolderName : roomList) {

        FileInputStream fileRoomIn = new FileInputStream(
            "entSAVE/room_entities/"
                + roomFolderName);
        ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
        Room object = (Room) objectRoomIn.readObject();
        //если номер комнаты совпадает то передаем
        if (object.getRoomNum() == roomNum) {
          log.info("Room was found>");
          room = object;
        }
        objectRoomIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(room ==null){
        log.error("ROOM NOT FOUND>");
      }
      return (Room) room;
    }


  }

  @Override
  public Room searchUUIDObject(UUID uuid) {

    log.info("<Start searching room....");

    File roomFolderDirectory = new File("entSAVE/room_entities");
    String[] roomList = roomFolderDirectory.list();
    Object room = null;
    try {
      for (String roomFolderName : roomList) {

        FileInputStream fileRoomIn = new FileInputStream(
            "entSAVE/room_entities/"
                + roomFolderName);
        ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
        Room object = (Room) objectRoomIn.readObject();
        //если ююайди комнаты совпадает то передаем
        if (object.getUuid().equals(uuid)) {
          log.info("Room was found>");
          room = object;
        }
        objectRoomIn.close();

      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(room ==null){
        log.error("ROOM NOT FOUND>");
      }
      return (Room) room;
    }


  }

  @Override
  public String searchFileName(Room room) {
    log.info("<Start searching file name of room...");
    File roomFolderDirectory = new File(
        "entSAVE/room_entities");
    String[] roomList = roomFolderDirectory.list();
    String fileName = null;
    try {
      for (String roomFolderName : roomList) {

        FileInputStream fileRoomIn = new FileInputStream(
            "entSAVE/room_entities/"
                + roomFolderName);
        ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
        Room object = (Room) objectRoomIn.readObject();
        //если номер комнаты совпадает то передаем
        if (object.equals(room)) {
          log.info("File name of room was found>");
          fileName = roomFolderName;
        }
        objectRoomIn.close();

      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if(fileName == null){
        log.error("FILE NAME OF ROOM NOT FOUND>");
      }
      return fileName;
    }


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
    for(UUID uuidEmployee : room.getEmployeeID()){
      Employee employee = employeeCRUD.searchUUIDObject(uuidEmployee);
      employee.deleteRoomsID(room.getUuid());
      employeeCRUD.saveObject(employee);
    }

    for(UUID uuidGuest : room.getGuestID()){
      Guest guest = guestCRUD.searchUUIDObject(uuidGuest);
      guest.setRoomID(null);
      guestCRUD.saveObject(guest);
    }

    object = searchObjectNum(room.getRoomNum());
    //удаляем файл
    File deleteFile = new File("entSAVE/room_entities/" + searchFileName(object));
    if (deleteFile != null) {
      log.info("Room was successfully deleted>" );
      deleteFile.delete();
    } else{
      log.warn("NOTHING WAS DELETED, FILE ROOM NOT FOUND>");
    }
  }

  @Override
  public void saveObject(Room room) {
    log.info("<Start saving room...");
    int roomNum = room.getRoomNum();
    try {

      FileOutputStream fileRoomOut = new FileOutputStream(
          "entSAVE/room_entities/" + room.getUuid() + "-room.txt");
      ObjectOutputStream objectRoomOut = new ObjectOutputStream(fileRoomOut);
      objectRoomOut.writeObject(room);
      log.info("Success saving room>");
      objectRoomOut.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

}
