package jingwei027.com.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jingwei027.com.annotation.NeedEmbed;
import lombok.Data;

@Data
public class Book implements GenericDto {

  private String isbn;

  private String name;

  private BigDecimal price;

  private LocalDate publishDate;

  @NeedEmbed
  private String contentPreview;

  @NeedEmbed
  private String description;

  @NeedEmbed
  private Vendor vendor;

  @NeedEmbed
  private List<Person> authors;

  private List<String> languages;

}
