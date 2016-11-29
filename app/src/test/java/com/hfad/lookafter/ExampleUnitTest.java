package com.hfad.lookafter;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isTitleNotEmpty() {

        Book book = new Book("author", "a", "b", "d", false);

        Class c = book.getClass();
        //Method[] methods = c.getDeclaredMethods();
        try {

            Method method = c.getDeclaredMethod("privateMethod");
            method.setAccessible(true);
            Integer result = (Integer) method.invoke(book);

            assertTrue(result == 5);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}