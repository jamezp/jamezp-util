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

import static com.jamezp.util.ObjectHelper.areEqual;
import com.jamezp.util.ObjectHelper.HashCodeBuilder;
import com.jamezp.util.ObjectHelper.ToStringBuilder;
import java.io.Serializable;

/**
 * This class represents a pair.
 * 
 * @author James R. Perkins (JRP) - Manage, Inc.
 * 
 */
public class Pair<T1, T2> implements Serializable {

    /**
     * Serialized version UID.
     */
    private static final long serialVersionUID = -8869732317374347179L;

    private final T1 first;

    private final T2 second;

    /**
     * Creates a new {@code Pair}.
     * 
     * @param first
     *            the first object
     * @param second
     *            the second object
     */
    public Pair(final T1 first, final T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * This is a static method for convenience in creating a generic {@code
     * Pair}.
     * 
     * @param <A>
     *            the type of the first object
     * @param <B>
     *            the type of the second object
     * @param first
     *            the first object
     * @param second
     *            the second object
     * @return
     */
    public static <T1, T2> Pair<T1, T2> of(T1 first, T2 second) {
        return new Pair<T1, T2>(first, second);
    }

    /**
     * This method will return first object.
     * 
     * @return the first object
     */
    public final T1 first() {
        return first;
    }

    /**
     * This method will return the second object.
     * 
     * @return the second object
     */
    public final T2 second() {
        return second;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final Pair other = (Pair) obj;
        return areEqual(this.first, other.first)
                && areEqual(this.second, other.second);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.builder().add(first).add(second).toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.of(this).add("first", first).add("second",
                second).toString();
    }
}
