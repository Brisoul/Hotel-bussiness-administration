package com.netcracker.hb.console.services;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.FloorCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class FloorService implements Service<Floor> {

  private static int floorNum;
  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();
  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();

  @Override
  public void addObject() {

    //Создали этаж
    Hotel hotel = hotelCRUD.searchObject(1);
    floorNum += 1;
    Floor floor = Floor.builder()
        .floorNum(floorNum)
        .roomsID(new HashSet<>())
        .uuid(UUID.randomUUID())
        .hotelId(hotel.getUuid())
        .build();
    floorCRUD.saveObject(floor);//сохраняем

    //Связали с отелем
    Set floorsID = hotel.getFloorsID();
    floorsID.add(floor.getUuid());
    hotel.setFloorsID(floorsID);
    hotelCRUD.saveObject(hotel);//сохраняем

  }


  @Override
  public void changeObject(Floor object) {

  }

}
