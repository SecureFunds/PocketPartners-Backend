package b4u.pocketpartners.backend.groups.domain.model.entities;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.Currencies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

/**
 * Entity class representing a Currency in the system.
 * It is annotated with @Data, @NoArgsConstructor, @AllArgsConstructor, and @With from the Lombok library to automatically generate boilerplate code.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Currency {
    /**
     * The unique identifier for the Currency. It is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The code of the Currency, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Currencies code;

    /**
     * Constructor for creating a Currency with a specific code.
     * @param code The code of the Currency.
     */
    public Currency(Currencies code) {
        this.code = code;
    }

    /**
     * Returns the code of the Currency as a string.
     * @return The code of the Currency.
     */
    public String getCode() {
        return code.name();
    }

    /**
     * Returns a default Currency.
     * @return The default Currency.
     */
    public static Currency getDefaultRole() {
        return new Currency(Currencies.PEN);
    }

    /**
     * Converts a string code to a Currency.
     * @param code The string code.
     * @return The corresponding Currency.
     */
    public static Currency toCurrencyFromCode(String code) {
        return new Currency(Currencies.valueOf(code));
    }

    /**
     * Validates a set of Currencies.
     * @return A list of valid Currencies.
     */
    public static List<Currency> validateCurrencySet() {
        return List.of(new Currency(Currencies.PEN), new Currency(Currencies.USD), new Currency(Currencies.EUR), new Currency(Currencies.YEN));
    }
}