package com.netcracker.hb.console.services.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.entities.hotel.Hotel;
import java.util.HashSet;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class HotelService implements Service<Hotel> {

  private static final Service<Hotel> hotelService = new HotelService();
  private HotelService(){}
  public static Service<Hotel> getHotelService(){
    return hotelService;
  }

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();

  @Override
  public void addObject() {
    log.info("<Start creating hotel...");

    Hotel hotel = Hotel.builder()
        .floorsID(new HashSet<>())
        .uuid(UUID.randomUUID())
        .build();

    hotelCRUD.saveObject(hotel);

    log.info("End creating hotel>");
  }

  @Override
  public void changeObject(Hotel hotel) {

  }

  @Override
  public void displayObject(Hotel object) {
    log.info("_______________________");
    log.info(hotelCRUD.searchFileName(object));
    log.info("_______________________");
    log.info("Hotel have " + object.getFloorsID().size() + "Floors");
    log.info("_______________________");
  }


}
