package com.netcracker.hb.console;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;
import com.netcracker.hb.entities.hotel.Hotel;

public class UtilizeManager {
  private static UtilizeManager utilizeManager;
  private UtilizeManager(){
  }

  public static synchronized UtilizeManager getUtilizeManager() {
    if(utilizeManager ==null){
      utilizeManager = new UtilizeManager();
    }
    return utilizeManager;
  }

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();

  public void utilize(){


    hotelCRUD.deleteObject(hotelCRUD.searchObjectNum(1));
  }

}
