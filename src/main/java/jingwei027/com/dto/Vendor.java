package jingwei027.com.dto;

import java.util.List;
import jingwei027.com.annotation.NeedEmbed;
import lombok.Data;

@Data
public class Vendor implements GenericDto {

  private String companyName;
  private String tel;

  @NeedEmbed
  private List<Address> addresses;

  @NeedEmbed
  private Person contactPerson;

}
