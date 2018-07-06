package jingwei027.com.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jingwei027.com.core.test.BaseTest;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("EmbedParser")
public class EmbedParserTest extends BaseTest {

  @Test
  public void privateConstructor() throws Exception
  {
    privateConstructor(EmbedParser.class);
  }

  @Nested
  @DisplayName("parseToKeySet")
  class parseToKeySet {

    @Test
    @DisplayName("'description'")
    public void testPositiveCase01()
    {
      final Set<String> embedKeySet = EmbedParser.parseToKeySet("description");
      assertNotNull(embedKeySet); // [description]
      assertEquals(1, embedKeySet.size());
      assertTrue(embedKeySet.contains("description"));
    }

    @Test
    @DisplayName("'description,contentPreview,vendor'")
    public void testPositiveCase02()
    {
      final Set<String> embedKeySet = EmbedParser.parseToKeySet("description,contentPreview,vendor");
      assertNotNull(embedKeySet);
      assertEquals(3, embedKeySet.size()); // [vendor, description, contentPreview]
      assertTrue(embedKeySet.contains("description"));
      assertTrue(embedKeySet.contains("contentPreview"));
      assertTrue(embedKeySet.contains("vendor"));
    }

    @Test
    @DisplayName("'description,contentPreview,vendor'")
    public void testPositiveCase03()
    {
      final Set<String> embedKeySet = EmbedParser.parseToKeySet("description,contentPreview,vendor");
      assertNotNull(embedKeySet);
      assertEquals(3, embedKeySet.size());
      assertTrue(embedKeySet.contains("description"));
      assertTrue(embedKeySet.contains("contentPreview"));
      assertTrue(embedKeySet.contains("vendor"));
    }

    @Test
    @DisplayName("'description ,, ,  ,vendor' [empty field name is allowed and whitespace will be trim]")
    public void testPositiveCase04()
    {
      final Set<String> embedKeySet = EmbedParser.parseToKeySet("description ,, ,  ,vendor");
      assertNotNull(embedKeySet); // [vendor, description]
      assertEquals(2, embedKeySet.size());
      assertTrue(embedKeySet.contains("description"));
      assertTrue(embedKeySet.contains("vendor"));
    }

    @Test
    @DisplayName("'description,vendor(addresses)' [2 level case]")
    public void testPositiveCase05()
    {
      final Set<String> embedKeySet = EmbedParser.parseToKeySet("description,vendor(addresses)");
      assertNotNull(embedKeySet); // [vendor, vendor.addresses, description]
      assertEquals(3, embedKeySet.size());
      assertTrue(embedKeySet.contains("description"));
      assertTrue(embedKeySet.contains("vendor"));
      assertTrue(embedKeySet.contains("vendor.addresses"));
    }

    @Test
    @DisplayName("'description,vendor(addresses),authors(address(simpleAddress))' [3 level case]")
    public void testPositiveCase06()
    {
      final Set<String> embedKeySet = EmbedParser.parseToKeySet("description,vendor(addresses),authors(address(simpleAddress))");
      assertNotNull(embedKeySet); // [vendor, vendor.addresses, description, authors.address, authors, authors.address.simpleAddress]
      assertEquals(6, embedKeySet.size());
      assertTrue(embedKeySet.contains("description"));
      assertTrue(embedKeySet.contains("vendor"));
      assertTrue(embedKeySet.contains("vendor.addresses"));
      assertTrue(embedKeySet.contains("authors"));
      assertTrue(embedKeySet.contains("authors.address"));
      assertTrue(embedKeySet.contains("authors.address.simpleAddress"));
    }

    @Test
    @DisplayName("'description,vendor(addresses),authors(address(simpleAddress)),contentPreview' [3 level case]")
    public void testPositiveCase07()
    {
      final Set<String> embedKeySet = EmbedParser.parseToKeySet("description,vendor(addresses),authors(address(simpleAddress,fullAddress)),contentPreview");
      assertNotNull(embedKeySet); // [vendor, vendor.addresses, description, contentPreview, authors.address, authors.address.fullAddress, authors, authors.address.simpleAddress]
      assertEquals(8, embedKeySet.size());
      assertTrue(embedKeySet.contains("description"));
      assertTrue(embedKeySet.contains("vendor"));
      assertTrue(embedKeySet.contains("vendor.addresses"));
      assertTrue(embedKeySet.contains("authors"));
      assertTrue(embedKeySet.contains("authors.address"));
      assertTrue(embedKeySet.contains("authors.address.simpleAddress"));
      assertTrue(embedKeySet.contains("authors.address.fullAddress"));
      assertTrue(embedKeySet.contains("contentPreview"));
    }

    @Test
    @DisplayName("'description())' [X] '(' and ')' count must the same")
    public void testNegativeCase01()
    {
      Assertions.assertThrows(RuntimeException.class, () -> {
        EmbedParser.parseToKeySet("description())");
      });
    }

    @Test
    @DisplayName("'description,vendor[addresses]' [X] '[' or ']' not allowed")
    public void testNegativeCase02()
    {
      Assertions.assertThrows(RuntimeException.class, () -> {
        EmbedParser.parseToKeySet("description,vendor[addresses]");
      });
    }
  }

}
