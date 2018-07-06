package jingwei027.com.dto;

import jingwei027.com.annotation.NeedEmbed;
import lombok.Data;

@Data
public class Person implements GenericDto {

  private String name;

  private String email;

  private String tel;

  @NeedEmbed
  private Address address;

  @NeedEmbed
  private String introduce;

}
