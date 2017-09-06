package oop_ca5_tvmaze;

import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shaun
 */
public class PersonStoreTest
{
    
    LinkedList<Person> testPersons = new LinkedList<>();
    
    @Before
    public void setUp()
    {
        PersonStore instance = new PersonStore();
        instance.loadPersonStore();
        HashMap<String, Person> persons = instance.getPersons();
        for(Person p : persons.values())
        {
            testPersons.add(p);
        }
    }

    /**
     * Test of loadPersonStore method, of class PersonStore.
     */
    @Test
    public void testLoadPersonStore()
    {
        System.out.println("loadPersonStore");
        PersonStore instance = new PersonStore();
        instance.loadPersonStore();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPersons method, of class PersonStore.
     */
    @Test
    public void testGetPersons()
    {
        System.out.println("getPersons");
        PersonStore instance = new PersonStore();
        instance.loadPersonStore();
        HashMap<String, Person> result = instance.getPersons();
        
        HashMap<String, Person> expResult = new HashMap<>();
        for(Person p : result.values())
        {
            expResult.put(p.getName(), p);
        }       
        assertEquals(expResult, result);      
    }

    /**
     * Test of searchAPI method, of class PersonStore.
     */
    @Test
    public void testSearchAPI()
    {
        System.out.println("searchAPI");
        
        PersonStore instance = new PersonStore();
        
        String nameUI = "shaun";
        LinkedList<Person> result1 = instance.searchAPI(nameUI);

        //Expecting an empty linkedlist.
        nameUI = "12";
        LinkedList<Person> result2 = instance.searchAPI(nameUI);

        //Expecting an empty linkedlist.
        nameUI = "john123";
        LinkedList<Person> result3 = instance.searchAPI(nameUI);
        
        nameUI = "shaun12";
        LinkedList<Person> result4 = instance.searchAPI(nameUI);
        if (result1.size() < 1)
        {
            fail("API returned no result for '" + nameUI + "'");
        }
        else if (result2.size() > 0)
        {
            fail("API returned results for '" + nameUI + "'");
        }
        else if (result3.size() > 0)
        {
            fail("API returned results for '" + nameUI + "'");
        }
        else if (result4.size() < 1)
        {
            fail("API returned no result for '" + nameUI + "'");
        }
        else
        {
            System.out.println("PASSED");
            System.out.println("TEST 1 RESULT:");
            for (Person p : result1)
            {
                System.out.println(p.toString());
            }
            System.out.println("TEST 2 RESULT: 0 results, success");
            System.out.println("TEST 3 RESULT: 0 results, success");
            System.out.println("TEST 4 RESULT: " + result4.size() + " results, success");
        }       
    }

    /**
     * Test of searchLocalRecords method, of class PersonStore.
     */
    @Test
    public void testSearchLocalRecords()
    {
        System.out.println("searchLocalRecords");
        //Must be valid search name in order for testing to work.
        String searchName = "shaun";
        
        PersonStore instance = new PersonStore();
        instance.loadPersonStore();
        
        LinkedList<Person> result = instance.searchLocalRecords(searchName);
        if (result.size() < 1)
        {
            fail("Did not return results for " + searchName);
        }
        
        for (Person p : result)
        {
            if (!p.getQueryName().equals(searchName))
            {
                System.out.println(p.toString());
                fail("A person's query name did not match the search name '" + searchName + "'");
            }
        }
        
        System.out.println("PASSED");
        for (Person p : result)
        {
            System.out.println(p.toString());
        }       
    }

    /**
     * Test of listPersons method, of class PersonStore.
     */
    @Test
    public void testListPersons()
    {
        try
        {
            System.out.println("listPersons");
            LinkedList<Person> persons = testPersons;
            PersonStore instance = new PersonStore();
            instance.listPersons(persons);
        }
        catch (Exception e)
        {
            fail("An error occurred: " + e);
        }
    }

    /**
     * Test of listPersonsByName method, of class PersonStore.
     */
    @Test
    public void testListPersonsByName()
    {
        try
        {
            System.out.println("listPersonsByName");
            PersonStore instance = new PersonStore();
            instance.loadPersonStore();
            instance.listPersonsByName();
        }
        catch (Exception e)
        {
            fail("An error occurred: " + e);
        }       
    }

    /**
     * Test of listPersonsByID method, of class PersonStore.
     */
    @Test
    public void testListPersonsByID()
    {
        try
        {
            System.out.println("listPersonsByID");
            PersonStore instance = new PersonStore();
            instance.loadPersonStore();
            instance.listPersonsByID();
        }
        catch (Exception e)
        {
            fail("An error occurred: " + e);
        }
    }

    /**
     * Test of listPersonsByRating method, of class PersonStore.
     */
    @Test
    public void testListPersonsByRating()
    {
        try
        {
            System.out.println("listPersonsByRating");
            PersonStore instance = new PersonStore();
            instance.loadPersonStore();
            instance.listPersonsByRating();
        }
        catch (Exception e)
        {
            fail("An error occurred: " + e);
        }
    }
    
    @Test
    public void testWritePersonData()
    {
        try
        {
            System.out.println("writePersonData");
            String fileName = "JUNITtestWritePersonData.dat";
            PersonStore instance = new PersonStore();
            instance.loadPersonStore();
            instance.writePersonData(fileName);
        }
        catch (Exception e)
        {
            fail("An error occurred: " + e);
        }      
    }

    /**
     * Test of exportToHTML method, of class PersonStore.
     */
    @Test
    public void testExportToHTML()
    {
        try
        {
            System.out.println("exportToHMTL");
            PersonStore instance = new PersonStore();
            LinkedList<Person> persons = instance.searchLocalRecords("shaun");
            instance.searchLocalRecords("shaun");
            instance.exportToHMTL(persons);
        }
        catch (Exception e)
        {
            fail("An error occurred: " + e);
        }
    }
    
}
