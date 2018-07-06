package jingwei027.com.dto;

import jingwei027.com.annotation.NeedEmbed;
import lombok.Data;

@Data
public class Address implements GenericDto {

  private YesNo isAmerica;

  private String country;

  private String city;

  private String zip;

  private String address1;

  private String address2;

  @NeedEmbed
  private String fullAddress;

  @NeedEmbed
  private String simpleAddress;

}
