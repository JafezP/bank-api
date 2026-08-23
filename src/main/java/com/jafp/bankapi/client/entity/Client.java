package com.jafp.bankapi.client.entity;

import com.jafp.bankapi.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {
  @Column(name = "last_name", nullable = false)
  private String lastName;
  @Column(name = "mother_last_name", nullable = false)
  private String motherLastName;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "second_name")
  private String secondName;
  @Column(name = "dni", nullable = false, unique = true)
  private String dni;
  @Column(name = "email", unique = true)
  private String email;
  @Column(name = "phone_number", unique = true)
  private String phoneNumber;
  @Column(name = "address",  nullable = false)
  private String address;
  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ClientStatus status;

  public Client() {
  }

  public Client(String lastName, String motherLastName, String firstName, String secondName, String dni, String email, String phoneNumber, String address, LocalDate birthDate, ClientStatus status) {
    this.lastName = lastName;
    this.motherLastName = motherLastName;
    this.firstName = firstName;
    this.secondName = secondName;
    this.dni = dni;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.birthDate = birthDate;
    this.status = status;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMotherLastName() {
    return motherLastName;
  }

  public void setMotherLastName(String motherLastName) {
    this.motherLastName = motherLastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public ClientStatus getStatus() {
    return status;
  }

  public void setStatus(ClientStatus status) {
    this.status = status;
  }
}
