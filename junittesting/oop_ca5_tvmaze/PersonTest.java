package oop_ca5_tvmaze;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shaun
 */
public class PersonTest
{

    private Person testPerson;

    @Before
    public void setUp()
    {
        testPerson = new Person(45.167934, "luke", "Luke Janco", 20018, "defaultProfile.png", "http://www.tvmaze.com/people/20018/luke-janco", -1, "default comments");
    }

    /**
     * Test of display method, of class Person.
     */
    @Test
    public void testDisplay()
    {
        try
        {
            System.out.println("display");
            Person instance = testPerson;
            instance.display();
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getScore method, of class Person.
     */
    @Test
    public void testGetScore()
    {
        try
        {
            System.out.println("getScore");
            Person instance = testPerson;
            double expResult = 45.167934;
            double result = instance.getScore();
            assertEquals(expResult, result, 0.0);
            assertThat(result, not(12.393));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getId method, of class Person.
     */
    @Test
    public void testGetId()
    {
        try
        {
            System.out.println("getId");
            Person instance = testPerson;
            int expResult = 20018;
            int result = instance.getId();
            assertEquals(expResult, result);
            assertThat(result, not(32939));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getMyRating method, of class Person.
     */
    @Test
    public void testGetMyRating()
    {
        try
        {
            System.out.println("getMyRating");
            Person instance = testPerson;
            int expResult = -1;
            int result = instance.getMyRating();
            assertEquals(expResult, result);
            assertThat(result, not("false comment"));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of editRating method, of class Person.
     */
    @Test
    public void testEditRating()
    {
        try
        {
            System.out.println("editRating");
            int myRating = 2;
            Person instance = testPerson;
            instance.editRating(myRating);
            if (myRating != instance.getMyRating())
            {
                fail("Person instance does not equal myRating after editing.");
            }
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getQueryName method, of class Person.
     */
    @Test
    public void testGetQueryName()
    {
        try
        {
            System.out.println("getQueryName");
            Person instance = testPerson;
            String expResult = "luke";
            String result = instance.getQueryName();
            assertEquals(expResult, result);
            assertThat(result, not("john"));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getName method, of class Person.
     */
    @Test
    public void testGetName()
    {
        try
        {
            System.out.println("getName");
            Person instance = testPerson;
            String expResult = "Luke Janco";
            String result = instance.getName();
            assertEquals(expResult, result);
            assertThat(result, not("John Smith"));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getImageUrl method, of class Person.
     */
    @Test
    public void testGetImageUrl()
    {
        try
        {
            System.out.println("getImageUrl");
            Person instance = testPerson;
            String expResult = "defaultProfile.png";
            String result = instance.getImageUrl();
            assertEquals(expResult, result);
            assertThat(result, not("www.somefakewebsite.com/fakeimage.png"));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getPersonLink method, of class Person.
     */
    @Test
    public void testGetPersonLink()
    {
        try
        {
            System.out.println("getPersonLink");
            Person instance = testPerson;
            String expResult = "http://www.tvmaze.com/people/20018/luke-janco";
            String result = instance.getPersonLink();
            assertEquals(expResult, result);
            assertThat(result, not("www.somefakewebsite.com/person/fake-person"));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of getMyComments method, of class Person.
     */
    @Test
    public void testGetMyComments()
    {
        try
        {
            System.out.println("getMyComments");
            Person instance = testPerson;
            String expResult = "default comments";
            String result = instance.getMyComments();
            assertEquals(expResult, result);
            assertThat(result, not("not the default comment"));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of editComments method, of class Person.
     */
    @Test
    public void testEditComments()
    {
        try
        {
            System.out.println("editComments");
            String myComments = "new comment!";
            Person instance = testPerson;
            instance.editComments(myComments);
            if (!instance.getMyComments().equals("new comment!"))
            {
                fail("Person instance does not equal myComment after editing.");
            }
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of hashCode method, of class Person.
     */
    @Test
    public void testHashCode()
    {
        try
        {
            System.out.println("hashCode");
            Person instance = testPerson;
            int expResult = -378139350;
            int result = instance.hashCode();
            assertEquals(expResult, result);

            expResult = 2810191;
            assertFalse(expResult == result);
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of equals method, of class Person.
     */
    @Test
    public void testEquals()
    {
        try
        {
            System.out.println("equals");
            Object obj = testPerson;
            Person instance = testPerson;

            assertThat(obj, samePropertyValuesAs(instance));

            Person obj2 = new Person(45.167934, "luke", "Luke Janco", 20018, "defaultProfile.png", "http://www.tvmaze.com/people/20018/luke-janco", -1, "default comments");

            assertNotSame(instance, obj2);

            //Changing instance.
            instance.editComments("change");
            //Reverting instance to it's original state.
            instance.editComments("default comments");
            assertThat(obj2, samePropertyValuesAs(instance));
        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

    /**
     * Test of toString method, of class Person.
     */
    @Test
    public void testToString()
    {
        try
        {
            System.out.println("toString");
            Person instance = testPerson;
            String expResult = testPerson.toString();
            String result = instance.toString();
            assertThat(expResult, samePropertyValuesAs(result));

        }
        catch (Exception e)
        {
            fail("Error Occurred: " + e);
        }
    }

}
