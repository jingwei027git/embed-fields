package jingwei027.com.helper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import jingwei027.com.dto.Address;
import jingwei027.com.dto.Book;
import jingwei027.com.dto.Person;
import jingwei027.com.dto.Vendor;
import jingwei027.com.dto.YesNo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DummyBook {

  public static Book case_all_filled()
  {
    return new Book() {{
      setIsbn("ISBN-001");
      setName("南港樂活傳記");
      setPrice(new BigDecimal("200"));
      setPublishDate(LocalDate.of(2018, 7, 1));
      setContentPreview("2018/07/04 副總貢丸 又說他想戒酒 ... 眾人噓之。 --- (請付費解鎖更多干話) ---");
      setDescription("這是一本描述在南港奮鬥的高級上班族的故事，集「耍廢」「干話」... 等的多種要素 ...");
      setVendor(new Vendor() {{
        setCompanyName("軟趴趴出版社");
        setTel("28825252");
        setAddresses(new ArrayList<Address>() {{
          add(new Address() {{
            setIsAmerica(YesNo.N);
            setCountry("TW");
            setCity("南港");
            setZip("111");
            setAddress1("樂活圈區");
            setAddress2("D棟6樓");
            setFullAddress("台灣,台北,南港,樂活圈區,D棟6樓");
            setSimpleAddress("南港樂活圈區D棟6樓");
          }});
          add(new Address() {{
            setIsAmerica(YesNo.Y);
            setCountry("US");
            setCity("波士頓");
            setZip("99999");
            setAddress1("高爾夫園區");
            setAddress2("A棟2樓");
            setFullAddress("美國,波士頓,高爾夫園區,A棟2樓");
            setSimpleAddress("波士頓高爾夫園區A棟2樓");
          }});
        }});
        setContactPerson(new Person() {{
          setName("mis");
          setEmail("mis@softpower.com.tw");
          setTel("29998888");
          setAddress(new Address() {{
            setIsAmerica(YesNo.N);
            setCountry("TW");
            setCity("南港");
            setZip("111");
            setAddress1("樂活圈區");
            setAddress2("D棟6樓");
            setFullAddress("台灣,台北,南港,樂活圈區,D棟6樓");
            setSimpleAddress("南港樂活圈區D棟6樓");
          }});
          setIntroduce("軟公司清道夫");
        }});
      }});
      setAuthors(new ArrayList<Person>() {{
        add(new Person() {{
          setName("David");
          setEmail("david@softpower.com.tw");
          setTel("22223333");
          setAddress(new Address() {{
            setIsAmerica(YesNo.N);
            setCountry("TW");
            setCity("新北市三重區");
            setZip("241");
            setAddress1("忠孝路二段");
            setAddress2("7樓");
            setFullAddress("台灣,新北市三重區,忠孝路二段,7樓");
            setSimpleAddress("新北市三重區忠孝路二段7樓");
          }});
          setIntroduce("干話殺手");
        }});
        add(new Person() {{
          setName("Blade");
          setEmail("blade@softpower.com.tw");
          setTel("33334444");
          setAddress(new Address() {{
            setIsAmerica(YesNo.N);
            setCountry("TW");
            setCity("新北市板橋區");
            setZip("220");
            setAddress1("有錢路");
            setAddress2("10樓");
            setFullAddress("台灣,新北市板橋區,有錢路,10樓");
            setSimpleAddress("新北市板橋區有錢路10樓");
          }});
          setIntroduce("吃魚專家");
        }});
      }});
      setLanguages(new ArrayList<String>() {{
        add("English");
        add("Chinese");
      }});
    }};
  }

}
