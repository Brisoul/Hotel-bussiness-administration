package com.netcracker.hb.console;

import com.netcracker.hb.entities.*;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class console {
    public static void main(String[] args) {


        //создаем отель
        Hotel Belarus = new Hotel();
        //создаем пару этажей
        Floor floor1 = new Floor(1);
        Floor floor2 = new Floor(2);
        Floor floor3 = new Floor(3);
        //привязываем этажи к отелю
        Belarus.setFloor(floor1);
        Belarus.setFloor(floor2);
        Belarus.setFloor(floor3);
        //Создадим несколько гостевых комнат
        Room room100 = new Room(100);
        Room room150 = new Room(150);
        Room room200 = new Room(200);
        Room room250 = new Room(250);
        Room room300 = new Room(300);
        Room room350 = new Room(350);
        //привязываем их к этажам по 2 комнаты на этаж
        floor1.setRoom(room100);
        floor1.setRoom(room150);
        floor2.setRoom(room200);
        floor2.setRoom(room250);
        floor3.setRoom(room300);
        floor3.setRoom(room350);

        //задаем комнатам role
        room100.setRole(Role.ADMIN);
        room150.setRole(Role.GUEST);
        room200.setRole(Role.MANAGER);
        room250.setRole(Role.SERVICE_EMPLOYEE);
        room300.setRole(Role.GUEST);
        room350.setRole(Role.GUEST);


        //итого у нас есть 6 комнат 3 из них гостевые и 3 для работников
        //создадим 3 гостей c 3 разными пакетами и привяжем к ним комнаты
        Guest anatoliy = new Guest(room150);
        anatoliy.setService(Service.BREAKFAST);

        Guest adam = new Guest(room300);
        adam.setService(Service.LUNCH);

        Guest alex = new Guest(room350);
        alex.setService(Service.DINNER);


        //создадим 3 работников с разными ролями привяжим к ним комнаты
        Employee evgeniy = new Employee(Role.ADMIN);
        evgeniy.setRooms(room100);
        evgeniy.setRooms(room200);
        evgeniy.setRooms(room250);


        Employee oleg = new Employee(Role.MANAGER);
        oleg.setRooms(room200);
        oleg.setRooms(room250);
        oleg.setRooms(room300);

        Employee mari = new Employee(Role.SERVICE_EMPLOYEE);
        mari.setRooms(room250);
        mari.setRooms(room300);




















    }

}
