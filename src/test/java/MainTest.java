import org.example.Main;
import org.example.Name;
import org.example.Result;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {
    @Test
    void shouldFilterByFirstCharOfFirstNameAndSort() {
        // Given
        List<Result> results = new ArrayList<>();
        results.add(resultWithFirstName("Sam"));
        results.add(resultWithFirstName("Bob"));
        results.add(resultWithFirstName("Sally"));

        // When
        List<Result> filteredResults = Main.filterUsersByCharAndSort(results, 'S');

        // Then
        assertEquals(2, filteredResults.size());
        assertEquals("Sally", filteredResults.get(0).getName().getFirst());
        assertEquals("Sam", filteredResults.get(1).getName().getFirst());
    }

    @Test
    void shouldFilterCaseSensitively() {
        // Given
        List<Result> results = new ArrayList<>();
        results.add(resultWithFirstName("Sam"));
        results.add(resultWithFirstName("Bob"));
        results.add(resultWithFirstName("Sally"));

        // When
        List<Result> filteredResults = Main.filterUsersByCharAndSort(results, 's');

        // Then
        assertEquals(0, filteredResults.size());
    }

    @Test
    void shouldIgnoreNullResult() {
        // Given
        List<Result> results = new ArrayList<>();
        results.add(resultWithFirstName("Sam"));
        results.add(resultWithFirstName("Bob"));
        results.add(null);

        // When
        List<Result> filteredResults = Main.filterUsersByCharAndSort(results, 'S');

        // Then
        assertEquals(1, filteredResults.size());
        assertEquals("Sam", filteredResults.get(0).getName().getFirst());
    }

    @Test
    void shouldIgnoreEmptyFirstName() {
        // Given
        List<Result> results = new ArrayList<>();
        results.add(resultWithFirstName("Sam"));
        results.add(resultWithFirstName("Bob"));
        results.add(resultWithFirstName(""));

        // When
        List<Result> filteredResults = Main.filterUsersByCharAndSort(results, 'S');

        // Then
        assertEquals(1, filteredResults.size());
        assertEquals("Sam", filteredResults.get(0).getName().getFirst());
    }

    @Test
    void shouldIgnoreNullFirstName() {
        // Given
        List<Result> results = new ArrayList<>();
        results.add(resultWithFirstName("Sam"));
        results.add(resultWithFirstName("Bob"));
        results.add(resultWithFirstName(null));

        // When
        List<Result> filteredResults = Main.filterUsersByCharAndSort(results, 'S');

        // Then
        assertEquals(1, filteredResults.size());
        assertEquals("Sam", filteredResults.get(0).getName().getFirst());
    }

    @Test
    void shouldThrowIfNullInput() {
        assertThrows(NullPointerException.class,
                () -> {
                    Main.filterUsersByCharAndSort(null, 'S');
                });
    }

    private Result resultWithFirstName(String firstName) {
        Name name = new Name();
        name.setFirst(firstName);

        Result result = new Result();
        result.setName(name);
        return result;
    }
}
