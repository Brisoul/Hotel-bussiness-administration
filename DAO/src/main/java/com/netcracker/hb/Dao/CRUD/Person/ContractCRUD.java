package com.netcracker.hb.Dao.CRUD.Person;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.persons.Contract;
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
import lombok.Builder;
import lombok.extern.log4j.Log4j;


@Log4j
public class ContractCRUD implements IGuestCRUD<Contract> {


  private static final IGuestCRUD<Contract> contractCRUD = new ContractCRUD();
  private ContractCRUD() {
  }
  public static IGuestCRUD<Contract> getContractCRUD() {
    return contractCRUD;
  }

  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();

  @Override
  public Contract searchObjectNameSurname(String name, String surname) {
    log.info("<Start searching employee contract....");

    File contractFolderDirectory = new File("entSAVE/contract_entities");
    String[] contractList = contractFolderDirectory.list();
    Contract contract = null;
    try {
      for (String contractFolderName : contractList) {
        FileInputStream fileContractIn = new FileInputStream("entSAVE/contract_entities/"
            + contractFolderName);
        ObjectInputStream objectContractIn = new ObjectInputStream(fileContractIn);
        Contract object = (Contract) objectContractIn.readObject();
        Employee employee = employeeCRUD.searchObjectNameSurname(name, surname);

        if (employee.getContractID().equals(object.getUuid())) {
          log.info("employee contract was found>");
          contract = object;
        }
        objectContractIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (contract == null) {
        log.error("EMPLOYEE CONTRACT NOT FOUND>");
      }
      return contract;
    }
  }

  //не должно работать
  @Override
  public boolean searchObjectRole(Role role) {
    return false;
  }

  @Override
  public List<Contract> searchObjects() {
    log.info("<Start searching employee contractы....");

    File contractFolderDirectory = new File("entSAVE/contract_entities");
    String[] contractList = contractFolderDirectory.list();
    List contracts = new ArrayList();
    try {
      for (String contractFolderName : contractList) {
        FileInputStream fileContractIn = new FileInputStream("entSAVE/contract_entities/"
            + contractFolderName);
        ObjectInputStream objectContractIn = new ObjectInputStream(fileContractIn);
        Contract object = (Contract) objectContractIn.readObject();
        contracts.add(object);
        objectContractIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (contracts == null) {
        log.error("EMPLOYEE CONTRACT NOT FOUND>");
      }
      return contracts;
    }
  }

  //не должно работать
  @Override
  public Contract searchObjectNum(int num) {
    //тк у контракта номера нет, то и работать такой поиск не должен

    return null;
  }

  @Override
  public Contract searchUUIDObject(UUID uuid) {
    log.info("<Start searching employee contract....");

    File contractFolderDirectory = new File("entSAVE/contract_entities");
    String[] contractList = contractFolderDirectory.list();
    Contract contract = null;
    try {
      for (String contractFolderName : contractList) {
        FileInputStream fileContractIn = new FileInputStream("entSAVE/contract_entities/"
            + contractFolderName);
        ObjectInputStream objectContractIn = new ObjectInputStream(fileContractIn);
        Contract object = (Contract) objectContractIn.readObject();
        if (object.getUuid().equals(uuid)) {
          log.info("contract was found>");
          contract = object;
        }
        objectContractIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (contract == null) {
        log.error("EMPLOYEE CONTRACT NOT FOUND>");
      }
      return contract;
    }
  }

  @Override
  public String searchFileName(Contract contract) {
    log.info("<Start searching employee contract....");

    File contractFolderDirectory = new File("entSAVE/contract_entities");
    String[] contractList = contractFolderDirectory.list();
    String fileName = null;
    try {
      for (String contractFolderName : contractList) {
        FileInputStream fileContractIn = new FileInputStream("entSAVE/contract_entities/"
            + contractFolderName);
        ObjectInputStream objectContractIn = new ObjectInputStream(fileContractIn);
        Contract object = (Contract) objectContractIn.readObject();
        if (object.equals(contract)) {
          log.info("contract was found>");
          fileName = contractFolderName;
        }
        objectContractIn.close();
      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (fileName == null) {
        log.error("EMPLOYEE CONTRACT NOT FOUND>");
      }
      return fileName;
    }
  }

  @Override
  public void deleteObject(Contract object) {

    //отвязываем от работника
    Employee employee = employeeCRUD.searchUUIDObject(object.getUuid());
    employee.setContractID(null);

    // удаляем
    Contract contract = searchUUIDObject(object.getUuid());
    File deleteFile = new File("entSAVE/contract_entities/"+ searchFileName(contract));
    if (deleteFile != null) {
      log.info("Contract was successfully deleted>" );
      deleteFile.delete();
    } else{
      log.warn("NOTHING WAS DELETED, FILE CONTRACT NOT FOUND>");
    }


  }

  @Override
  public void saveObject(Contract contract) {

    log.info("<Start saving contract...");
    try {
      FileOutputStream fileContractOut = new FileOutputStream(
          "entSAVE/contract_entities/" + contract.getUuid() + "-personalCard.txt");

      ObjectOutputStream objectContractOut = new ObjectOutputStream(fileContractOut);
      objectContractOut.writeObject(contract);
      objectContractOut.close();
      log.info("Success saving contract>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}
