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

import com.jamezp.util.ObjectHelper.HashCodeBuilder;
import static com.jamezp.util.ObjectHelper.areEqual;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jrp
 */
public class ObjectHelperTest {

    public ObjectHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of areEqual method, of class 
     */
    @Test
    public void testAreEqual_boolean_boolean() {
        System.out.println("areEqual");
        assertTrue(areEqual(true, true));
        assertTrue(areEqual(false, false));
        assertFalse(areEqual(true, false));
        assertFalse(areEqual(false, true));
    }

    /**
     * Test of areEqual method, of class 
     */
    @Test
    public void testAreEqual_char_char() {
        System.out.println("areEqual");
        assertTrue(areEqual('a', 'a'));
        assertTrue(areEqual('X', 'X'));
        assertFalse(areEqual('z', 'Z'));
        assertFalse(areEqual('Z', 'z'));
    }

    /**
     * Test of areEqual method, of class 
     */
    @Test
    public void testAreEqual_long_long() {
        System.out.println("areEqual");
        final Random rand = new Random();
        int intValue = rand.nextInt();
        assertTrue(areEqual(intValue, intValue));
        
        long longValue = rand.nextLong();
        assertTrue(areEqual(longValue, longValue));
    }

    /**
     * Test of areEqual method, of class 
     */
    @Test
    public void testAreEqual_float_float() {
        System.out.println("areEqual");
        final Random rand = new Random();
        float floatValue = rand.nextFloat();
        assertTrue(areEqual(floatValue, floatValue));
    }

    /**
     * Test of areEqual method, of class 
     */
    @Test
    public void testAreEqual_double_double() {
        System.out.println("areEqual");
        final Random rand = new Random();
        double doubleValue = rand.nextDouble();
        assertTrue(areEqual(doubleValue, doubleValue));
    }

    /**
     * Test of areEqual method, of class 
     */
    @Test
    public void testAreEqual_Object_Object() {
        System.out.println("areEqual");
        assertTrue(areEqual("string", "string"));
        assertFalse(areEqual("old", "new"));
        // Test generics using custom Pair
        assertTrue(areEqual(Pair.of("one", "two"), Pair.of("one", "two")));
        assertFalse(areEqual(Pair.of("one", "two"), Pair.of("one", 2)));
    }
    
    @Test
    public void testHashCodeBuilder() {
        System.out.println("HashCodeBuilder");
        int hash1 = HashCodeBuilder.builder().add("s").add(1).add("other").toHashCode();
        int hash2 = HashCodeBuilder.builder().add("s").add(1).add("other").toHashCode();
        assertEquals(hash1, hash2);
        
        hash2 = HashCodeBuilder.builder().add("foo").add("x").toHashCode();
        assertTrue(hash1 != hash2);
    }

}