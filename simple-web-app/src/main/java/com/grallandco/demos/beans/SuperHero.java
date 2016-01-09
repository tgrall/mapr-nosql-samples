package com.grallandco.demos.beans;

public class SuperHero {

  private String id;
  private String firstName;
  private String lastName;
  private String alias;
  private String superPower;
  private String city;
  private String publisher;

  public SuperHero() {
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getSuperPower() {
    return superPower;
  }

  public void setSuperPower(String superPower) {
    this.superPower = superPower;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  @Override
  public String toString() {
    return "SuperHero{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", alias='" + alias + '\'' +
            ", superPower='" + superPower + '\'' +
            ", city='" + city + '\'' +
            ", publisher='" + publisher + '\'' +
            '}';
  }
}
