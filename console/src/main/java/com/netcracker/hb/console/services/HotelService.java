package com.netcracker.hb.console.services;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;
import com.netcracker.hb.entities.hotel.Hotel;
import java.util.HashSet;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class HotelService implements Service<Hotel> {

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();

  @Override
  public void addObject() {

    Hotel hotel = Hotel.builder()
        .floorsID(new HashSet<>())
        .uuid(UUID.randomUUID())
        .build();

    hotelCRUD.saveObject(hotel);

  }

  @Override
  public void changeObject(Hotel hotel) {

  }



}
