package Utilities;


import java.io.Serializable;

/**
 * This class is a record class, because it is used just to hold two private fields.
 * <p>
 *     It represents a half-inclusive interval.
 * </p>
 * @param lowerBound the lower bound of the interval - inclusive.
 * @param upperBound the upper bound of the interval - exclusive.
 * @author Matěj Pospíšil
 * @since 1.0 - (pre-release version)
 */
public record Interval(int lowerBound, int upperBound) implements Serializable {
}
