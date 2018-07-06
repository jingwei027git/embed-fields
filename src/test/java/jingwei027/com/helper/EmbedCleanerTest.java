package jingwei027.com.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import jingwei027.com.core.test.BaseTest;
import jingwei027.com.dto.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("EmbedCleaner")
public class EmbedCleanerTest extends BaseTest {

  @Test
  public void privateConstructor() throws Exception
  {
    privateConstructor(EmbedCleaner.class);
  }

  @Nested
  @DisplayName("clean")
  class clean {

    @Test
    @DisplayName("'description'")
    public void testPositiveCase01()
    {
      Book book = DummyBook.case_all_filled();
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNotNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNotNull(book.getAuthors());

      EmbedCleaner.clean("description", book);
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNull(book.getVendor());
      assertNull(book.getAuthors());
    }

    @Test
    @DisplayName("'ddddddd'")
    public void testPositiveCase02()
    {
      Book book = DummyBook.case_all_filled();
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNotNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNotNull(book.getAuthors());

      EmbedCleaner.clean("ddddddd", book);
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNull(book.getContentPreview());
      assertNull(book.getDescription());
      assertNull(book.getVendor());
      assertNull(book.getAuthors());
    }

    @Test
    @DisplayName("'description,contentPreview,vendor'")
    public void testPositiveCase03()
    {
      Book book = DummyBook.case_all_filled();
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNotNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNotNull(book.getAuthors());

      EmbedCleaner.clean("description,contentPreview,vendor", book);
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNotNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNull(book.getVendor().getAddresses());
      assertNull(book.getVendor().getContactPerson());
      assertNull(book.getAuthors());
    }

    @Test
    @DisplayName("'description,vendor(addresses),authors'")
    public void testPositiveCase04()
    {
      Book book = DummyBook.case_all_filled();
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNotNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNotNull(book.getAuthors());

      EmbedCleaner.clean("description,vendor(addresses),authors", book);
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNotNull(book.getVendor().getAddresses());
      assertNull(book.getVendor().getContactPerson());
      assertNotNull(book.getAuthors());
    }

    @Test
    @DisplayName("'description,vendor(addresses),authors'")
    public void testPositiveCase04_2()
    {
      final List<Book> books = Arrays.asList(DummyBook.case_all_filled(), DummyBook.case_all_filled());
      for (final Book book : books) {
        assertNotNull(book.getPublishDate()); // without @NeedEmbed
        assertNotNull(book.getContentPreview());
        assertNotNull(book.getDescription());
        assertNotNull(book.getVendor());
        assertNotNull(book.getAuthors());
      }

      EmbedCleaner.clean("description,vendor(addresses),authors", books);
      for (final Book book : books) {
        assertNotNull(book.getPublishDate()); // without @NeedEmbed
        assertNull(book.getContentPreview());
        assertNotNull(book.getDescription());
        assertNotNull(book.getVendor());
        assertNotNull(book.getVendor().getAddresses());
        assertNull(book.getVendor().getContactPerson());
        assertNotNull(book.getAuthors());
      }
    }

    @Test
    @DisplayName("'description,vendor(addresses),authors(address(simpleAddress,a(b(c(d()))))),contentPreview,xxx'")
    public void testPositiveCase05()
    {
      Book book = DummyBook.case_all_filled();
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNotNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNotNull(book.getVendor().getAddresses());
      assertNotNull(book.getVendor().getAddresses().get(0).getCountry());
      assertNotNull(book.getVendor().getAddresses().get(0).getSimpleAddress());
      assertNotNull(book.getVendor().getAddresses().get(0).getFullAddress());
      assertNotNull(book.getAuthors());
      assertNotNull(book.getAuthors().get(0).getAddress());
      assertNotNull(book.getAuthors().get(0).getAddress().getCountry()); // without @NeedEmbed
      assertNotNull(book.getAuthors().get(0).getAddress().getFullAddress());
      assertNotNull(book.getAuthors().get(0).getAddress().getSimpleAddress());

      EmbedCleaner.clean("description,vendor(addresses),authors(address(simpleAddress,a(b(c(d()))))),contentPreview,xxx", book);
      assertNotNull(book.getPublishDate()); // without @NeedEmbed
      assertNotNull(book.getContentPreview());
      assertNotNull(book.getDescription());
      assertNotNull(book.getVendor());
      assertNotNull(book.getVendor().getAddresses());
      assertNotNull(book.getVendor().getAddresses().get(0).getCountry()); // without @NeedEmbed
      assertNull(book.getVendor().getAddresses().get(0).getSimpleAddress());
      assertNull(book.getVendor().getAddresses().get(0).getFullAddress());
      assertNotNull(book.getAuthors());
      assertNotNull(book.getAuthors().get(0).getAddress());
      assertNotNull(book.getAuthors().get(0).getAddress().getCountry()); // without @NeedEmbed
      assertNull(book.getAuthors().get(0).getAddress().getFullAddress());
      assertNotNull(book.getAuthors().get(0).getAddress().getSimpleAddress());
    }
  }

}
