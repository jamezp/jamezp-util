/*
 * The MIT License
 *
 * Copyright 2011 James R. Perkins Jr (JRP).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jamezp.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Simple utilities for Objects.
 *
 * @author James R. Perkins (jrp)
 */
public class ObjectHelper {
    
    /**
     * Checks to see if two booleans are equal.
     * 
     * @param first  the first boolean.
     * @param second the second boolean.
     * @return {@code true} if the first is equal to the second, otherwise
     *         {@code false}.
     */
    public static boolean areEqual(final boolean first, final boolean second) {
        return first == second;
    }
    /**
     * Checks to see if two characters are equal.
     * 
     * @param first  the first character.
     * @param second the second character.
     * @return {@code true} if the first is equal to the second, otherwise
     *         {@code false}.
     */
    public static boolean areEqual(final char first, final char second) {
        return first == second;
    }
    /**
     * Checks to see if two longs are equal. This method is also used for int's,
     * shorts, and bytes.
     * 
     * @param first  the first long.
     * @param second the second long.
     * @return {@code true} if the first is equal to the second, otherwise
     *         {@code false}.
     */
    public static boolean areEqual(final long first, final long second) {
        return first == second;
    }
    /**
     * Checks to see if two floats are equal.
     * 
     * @param first  the first float.
     * @param second the second float.
     * @return {@code true} if the first is equal to the second, otherwise
     *         {@code false}.
     */
    public static boolean areEqual(final float first, final float second) {
        return Float.floatToIntBits(first) == Float.floatToIntBits(second);
    }
    /**
     * Checks to see if two doubles are equal.
     * 
     * @param first  the first double.
     * @param second the second double.
     * @return {@code true} if the first is equal to the second, otherwise
     *         {@code false}.
     */
    public static boolean areEqual(final double first, final double second) {
        return Double.doubleToLongBits(first) == Double.doubleToLongBits(second);
    }
    
    /**
     * Checks to see if two objects are equal. 
     * 
     * <p>
     * Note this method does not handle arrays. The {@link java.util.Arrays}
     * class has utilities for equality of arrays.
     * </p>
     * 
     * @param first  the first optionally {@code null} object.
     * @param second the second optionally {@code null} object.
     * @return {@code true} if the first is equal to the second, otherwise
     *         {@code false}.
     */
    public static boolean areEqual(final Object first, final Object second) {
        return (first == null ? second == null : first.equals(second));
    }

    /**
     * Checks to see if an object is {@code null}, otherwise throws an
     *         {@link IllegalArgumentException}.
     * 
     * @param <T> the type of the object.
     * @param ref the reference to the object.
     * @return the reference if it is not {@code null}.
     */
    public static <T> T checkNonNull(final T ref) {
        if (ref == null) {
            throw new IllegalArgumentException();
        }
        return ref;
    }

    /**
     * Checks to see if an object is {@code null}, otherwise throws an
     *         {@link IllegalArgumentException}.
     * 
     * @param <T>     the type of the object.
     * @param ref     the reference to the object.
     * @param message the message used in the {@code IllegalArgumentException}
     *                constructor.
     * @return the reference if it is not {@code null}.
     */
    public static <T> T checkNonNull(final T ref, final String message) {
        if (ref == null) {
            throw new IllegalArgumentException(message);
        }
        return ref;
    }

    /**
     * Checks to see if an object is {@code null}, otherwise throws an
     *         {@link IllegalArgumentException}.
     * 
     * @param <T>     the type of the object.
     * @param ref     the reference to the object.
     * @param message the message  format used in the
     *                {@code IllegalArgumentException} constructor.
     * @param args    the arguments used to format the message.
     * @return the reference if it is not {@code null}.
     */
    public static <T> T checkNonNull(final T ref, final String message, final Object... args) {
        if (ref == null) {
            throw new IllegalArgumentException(String.format(message, args));
        }
        return ref;
    }
    
    /**
     * A builder to simplify the building of hash codes.
     * 
     */
    public static class HashCodeBuilder {
        private final int seed;
        private int hash;
        
        /**
         * Private constructor for builder pattern.
         * 
         * @param seed the seed for the hash code.
         */
        private HashCodeBuilder(final int seed) {
            this.seed = seed;
            hash = 17;
        }
        
        /**
         * Creates a new builder with 31 as the seed.
         * 
         * @return the new builder.
         */
        public static HashCodeBuilder builder() {
            return new HashCodeBuilder(31);
        }
        
        /**
         * Creates a new builder with the seed that is passed.
         * 
         * @param seed the seed for the hash code.
         * @return the new builder.
         */
        public static HashCodeBuilder builder(final int seed) {
            return new HashCodeBuilder(seed);
        }
        
        /**
         * Adds the hash code of a boolean to the final hash code value.
         * 
         * @param b a boolean to calculate the hash code of.
         * @return the current builder.
         */
        public HashCodeBuilder add(final boolean b) {
            hash = calc() + (b ? 1 : 0);
            return this;
        }
        
        /**
         * Adds the hash code of a character to the final hash code value.
         * 
         * @param c a character to calculate the hash code of.
         * @return the current builder.
         */
        public HashCodeBuilder add(final char c) {
            hash = calc() + (int) c;
            return this;
        }
        
        /**
         * Adds the hash code of an integer to the final hash code value.
         * 
         * <p>
         * Both shorts and bytes use this method to add the hash value.
         * </p>
         * 
         * @param i an integer to calculate the hash code of.
         * @return the current builder.
         */
        public HashCodeBuilder add(final int i) {
            hash = calc() + i;
            return this;
        }
        
        /**
         * Adds the hash code of a long to the final hash code value.
         * 
         * @param lng a long to calculate the hash code of.
         * @return the current builder.
         */
        public HashCodeBuilder add(final long lng) {
            hash = calc() + (int)(lng ^ (lng >>> 32));
            return this;
        }
        
        /**
         * Adds the hash code of a float to the final hash code value.
         * 
         * @param flt a float to calculate the hash code of.
         * @return the current builder.
         */
        public HashCodeBuilder add(final float flt) {
            return add(Float.floatToIntBits(flt));
        }
        
        /**
         * Adds the hash code of a double to the final hash code value.
         * 
         * @param dbl a double to calculate the hash code of.
         * @return the current builder.
         */
        public HashCodeBuilder add(final double dbl) {
            return add(Double.doubleToLongBits(dbl));
        }
        
        /**
         * Adds the hash code of an object to the final hash code value. 
         * 
         * <p>
         * If the {@code obj} is {@code null} the hash code is calculated using
         * {@link #add(int)} with a value of 0. Otherwise the hash code is 
         * calculated using {@link #add(int)} with a value of 
         * {@code obj.hashCode()}, unless the object is an array. In this case
         * the array is processed and this is recursively invoked.
         * </p>
         * 
         * @param obj the object to calculate the hash code of.
         * @return the current builder.
         */
        public HashCodeBuilder add(final Object obj) {
            if (obj == null) {
                add(0);
            } else if(!obj.getClass().isArray()) {
                add(obj.hashCode());
            } else {
                final int len = Array.getLength(obj);
                for (int i = 0; i < len; i++) {
                    final Object arrayObj = Array.get(obj, i);
                    add(arrayObj);
                }
            }
            return this;
        }
        
        /**
         * Returns the calculated hash code.
         * 
         * @return the calculated hash code.
         */
        public int toHashCode() {
            return hash;
        }
        
        /**
         * This method overrides the default {@code Object#hashCode()}, but does
         * not return a proper hash of this builder. Returns the value of 
         * {@code toHashCode()} to insure the incorrect hash code is not returned
         * by mistake.
         * 
         * @return the value of {@link #toHashCode()}.
         */
        @Override
        public int hashCode() {
            return toHashCode();
        }
        
        /**
         * Should never be invoked on this object. Will throw an 
         * {@code AssertionError}.
         * 
         * @param obj an object.
         * @return nothing, always throws an {@code AssertionError}.
         */
        @Override
        public boolean equals(final Object obj) {
            throw new AssertionError("Equals not allowed for this builder.");
        }
        
        /**
         * Calculates the default.
         * @return the default hash value. 
         */
        private int calc() {
            return seed * hash;
        }
        
    }

    /**
     * A builder to build a default {@code Object#toString()} value.
     */
    public static class ToStringBuilder {

        private final List<String> fieldValue = new ArrayList<String>();

        private final String className;

        /**
         * Private constructor for builder pattern.
         * 
         * @param className the class name to prepend the result with.
         */
        private ToStringBuilder(final String className) {
            this.className = className;
        }

        /**
         * Creates a new string builder for the {@code clazz}.
         * 
         * @param clazz the base class for the string result.
         * @return the current builder.
         */
        public static ToStringBuilder of(final Class<?> clazz) {
            return new ToStringBuilder(checkNonNull(clazz.getSimpleName()));
        }

        /**
         * Creates a new string builder for the {@code className}.
         * 
         * @param className the class name to prepend the string value with.
         * @return the current builder.
         */
        public static ToStringBuilder of(final String className) {
            return new ToStringBuilder(checkNonNull(className));
        }

        /**
         * Creates a new string builder for the {@code self}.
         * 
         * @param self the object to create the builder for.
         * @return the current builder.
         */
        public static ToStringBuilder of(final Object self) {
            return of(checkNonNull(self.getClass()));
        }

        /**
         * Adds the field and value to the value returned.
         * 
         * @param field the field for the value.
         * @param value the value of the field.
         * @return the current instance of the builder.
         */
        public ToStringBuilder add(final String field, final Object value) {
            final String result = checkNonNull(field) + "=" + value;
            fieldValue.add(result);
            return this;
        }

        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder();
            result.append(className);
            result.append("{");
            final int size = fieldValue.size();
            int count = 0;
            for (String s : fieldValue) {
                count++;
                result.append(s);
                if (count < size) {
                    result.append(",");
                }
            }
            return result.append("}").toString();
        }
    }
}

