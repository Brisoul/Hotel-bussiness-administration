package com.netcracker.hb.Dao.CRUD.Person;


import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.PersonalCard;
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
public class EmployeeCRUD implements IEmployeeCRUD<Employee> {


  private static final IEmployeeCRUD<Employee> employeeCRUD = new EmployeeCRUD();

  private EmployeeCRUD() {
  }

  public static IEmployeeCRUD<Employee> getIEmployeeCRUD() {
    return employeeCRUD;
  }


  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final CRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();

  @Override
  public Employee searchObjectNameSurname(String name, String surname) {
    log.info("<Start searching employee....");

    File employeeFolderDirectory = new File("entSAVE/employee_entities");
    String[] employeeList = employeeFolderDirectory.list();
    Employee employee = null;
    try {
      for (String employeeFolderName : employeeList) {
        FileInputStream fileEmployeeIn = new FileInputStream("entSAVE/employee_entities/"
            + employeeFolderName);
        ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
        Employee object = (Employee) objectEmployeeIn.readObject();
        //
        if (object.getSurname().equals(surname) && object.getName().equals(name)) {
          log.info("employee was found>");
          employee = object;
        }
        objectEmployeeIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (employee == null) {
        log.error("EMPLOYEE NOT FOUND>");
      }
      return employee;
    }
  }

  @Override
  public Employee searchObjectLogIn(String username, String password) {
    log.info("<Start searching employee....");

    File employeeFolderDirectory = new File("entSAVE/employee_entities");
    String[] employeeList = employeeFolderDirectory.list();
    Employee employee = null;
    try {
      for (String employeeFolderName : employeeList) {
        FileInputStream fileEmployeeIn = new FileInputStream("entSAVE/employee_entities/"
            + employeeFolderName);
        ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
        Employee object = (Employee) objectEmployeeIn.readObject();
        //
        if (object.getUsername().equals(username) && object.getPassword().equals(password)) {
          log.info("employee was found>");
          employee = object;
        }
        objectEmployeeIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (employee == null) {
        log.error("EMPLOYEE NOT FOUND>");
      }
      return employee;
    }
  }

  @Override
  public boolean searchObjectRole(Role role) {
    log.info("<Start searching employee by role...." + role);

    File employeeFolderDirectory = new File("entSAVE/employee_entities");
    String[] employeeList = employeeFolderDirectory.list();
    Boolean searchStatus = false;
    try {
      for (String employeeFolderName : employeeList) {
        FileInputStream fileEmployeeIn = new FileInputStream("entSAVE/employee_entities/"
            + employeeFolderName);
        ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
        Employee object = (Employee) objectEmployeeIn.readObject();
        //
        if (object.getRole() == role) {
          log.info("employee with that role was found>");
          searchStatus = true;
        }
        objectEmployeeIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (searchStatus == false) {
        log.error("EMPLOYEE NOT FOUND>");
      }
      return searchStatus;
    }
  }

  @Override
  public List<Employee> searchObjects() {
    log.info("<Start searching employee....");

    File employeeFolderDirectory = new File("entSAVE/employee_entities");
    String[] employeeList = employeeFolderDirectory.list();
    List employers = new ArrayList();
    try {
      for (String employeeFolderName : employeeList) {
        FileInputStream fileEmployeeIn = new FileInputStream("entSAVE/employee_entities/"
            + employeeFolderName);
        ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
        Employee object = (Employee) objectEmployeeIn.readObject();
        //
        employers.add(object);
        objectEmployeeIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (employers.isEmpty()) {
        log.error("EMPLOYEE NOT FOUND>");
      }
      return employers;
    }
  }

  @Override
  public Employee searchObjectNum(int roomNum) {
    log.info("<Start searching employee....");
    //Ищем ююайдиник работников которые есть в данной комнате
    Room room = roomCRUD.searchObjectNum(roomNum);
    UUID uuid = null;
    for (UUID uuidStrange : room.getEmployeeID()) {
      //TODO можно добавить выбор пользователя по найденным в комнате людям
      uuid = uuidStrange;
    }

    //По ююайдишнику находим и самого работника
    Employee employee = searchUUIDObject(uuid);

    if (employee == null || uuid == null) {
      log.error("EMPLOYEE NOT FOUND>");
    } else {
      log.info("employee was found>");
    }
    return employee;
  }

  @Override
  public Employee searchUUIDObject(UUID uuid) {
    log.info("<Start searching employee....");

    File employeeFolderDirectory = new File("entSAVE/employee_entities");
    String[] employeeList = employeeFolderDirectory.list();
    Employee employee = null;
    try {
      for (String employeeFolderName : employeeList) {
        FileInputStream fileEmployeeIn = new FileInputStream("entSAVE/employee_entities/"
            + employeeFolderName);
        ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
        Employee object = (Employee) objectEmployeeIn.readObject();
        //
        if (object != null && uuid !=null) {
          if (object.getUuid().equals(uuid)) {
            log.info("employee was found>");
            employee = object;
          }
        }
        objectEmployeeIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (employee == null) {
        log.error("EMPLOYEE NOT FOUND>");
      }
      return employee;
    }
  }

  @Override
  public String searchFileName(Employee object) {
    log.info("<Start searching file name of employee...");

    File employeeFolderDirectory = new File("entSAVE/employee_entities");
    String[] employeeList = employeeFolderDirectory.list();
    String fileName = null;
    try {
      for (String employeeFolderName : employeeList) {
        FileInputStream fileEmployeeIn = new FileInputStream("entSAVE/employee_entities/"
            + employeeFolderName);
        ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
        Employee employee = (Employee) objectEmployeeIn.readObject();
        //
        if (object.equals(employee)) {
          log.info("File name of employee was found>");
          fileName = employeeFolderName;
        }
        objectEmployeeIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (fileName == null) {
        log.error("FILE NAME OF EMPLOYEE NOT FOUND>");
      }
      return fileName;
    }
  }

  @Override
  public void deleteObject(Employee object) {
    //удаляем персональную карту

    if (object.getCardID() !=null) {
      PersonalCard personalCard = personalCardCRUD.searchUUIDObject(object.getCardID());
      if (personalCard != null) {
        personalCardCRUD.deleteObject(personalCard);
      } else {
        log.warn("CARD NOT FOUND");
      }
    }

    //убираем из всех комнат за которыми закреплен
    for (UUID roomID : object.getRoomsID()) {
      Room room = roomCRUD.searchUUIDObject(roomID);
      room.deleteEmployee(object.getUuid());
      roomCRUD.saveObject(room);
    }
    // удаляем
    Employee employee = searchUUIDObject(object.getUuid());
    File deleteFile = new File("entSAVE/employee_entities/" + searchFileName(employee));
    if (deleteFile != null) {
      log.info("Employee was successfully deleted>");
      deleteFile.delete();
    } else {
      log.warn("NOTHING WAS DELETED, FILE EMPLOYEE NOT FOUND>");
    }

  }

  @Override
  public void saveObject(Employee employee) {
    log.info("<Start saving employee...");

    try {
      FileOutputStream fileEmployeeOut = new FileOutputStream(
          "entSAVE/employee_entities/" + employee.getUuid() + "-employee.txt");

      ObjectOutputStream objectEmployeeOut = new ObjectOutputStream(fileEmployeeOut);
      objectEmployeeOut.writeObject(employee);
      objectEmployeeOut.close();
      log.info("Success saving employee>");

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
