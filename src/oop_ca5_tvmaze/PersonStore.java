package oop_ca5_tvmaze;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Luke & Shaun
 */
public class PersonStore
{

    private HashMap<String, Person> persons;

    public PersonStore()
    {
        setPersons(new HashMap<>());
    }

    public void loadPersonStore()
    {
        readPersonData("persons.dat");
    }

    public HashMap<String, Person> getPersons()
    {
        return persons;
    }

    private void setPersons(HashMap<String, Person> persons)
    {
        this.persons = persons;
    }

    public LinkedList<Person> searchAPI(String nameUI)
    {
        String apiURL = "http://api.tvmaze.com/search/people?q=" + nameUI;
        LinkedList<Person> searchResults = new LinkedList<>();
        InputStream in = null;
        try
        {
            URL url = new URL(apiURL);  // an API returning JSON
            in = url.openStream();
            //Reads in JSON from input source.
            JsonReader reader = Json.createReader(in);
            //Converts the JSON into an array.
            JsonArray array = reader.readArray();
            for (int i = 0; i < array.size(); i++)
            {
                //Get JSON element from array.
                JsonObject wholeObject = array.getJsonObject(i);
                //Get "score" from the whole object.
                double personScore = wholeObject.getJsonNumber("score").doubleValue();
                //Get "person" object from the whole object, to allow us to extract data inside the "person" object. 
                JsonObject personObject = wholeObject.getJsonObject("person");
                //Get "name" from "person" object.
                String personName = personObject.getJsonString("name").getString();
                //Get "id" from "person" object.
                int personID = personObject.getJsonNumber("id").intValue();
                //Get "url" from "person" object, this is the person's URL link to the website.
                String personLink = personObject.getJsonString("url").getString();
                String personImageURL = "";
                //Checks if "image" contains anything. (simply checks if not null)
                if (!(personObject.isNull("image")))
                {
                    //Get "image" object from "person".
                    JsonObject imageObject = personObject.getJsonObject("image");
                    //Get "medium" image URL.
                    personImageURL = imageObject.getJsonString("medium").getString();
                }
                Person p = new Person(personScore, nameUI, personName, personID, personImageURL, personLink, -1, "default comments");
                persons.put(personName, p); // add to hashmap storage
                searchResults.add(p); // add to return list
            }
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.out);
                }
            }
        }
        return searchResults;
    }

    public LinkedList<Person> searchLocalRecords(String searchName)
    {
        LinkedList<Person> localSearchResults = new LinkedList<>();
        for (Person p : persons.values())
        {
            if (p.getName().toLowerCase().contains(searchName.toLowerCase())) // changed from .startsWith(searchName.toLowerCase())
            {
                localSearchResults.add(p);
            }
        }
        return localSearchResults;
    }

    public void listPersons(LinkedList<Person> persons)
    {
        for (Person p : persons)
        {
            p.display();
        }
    }

    public void listPersonsByName()
    {
        List<Person> sortedByName = new LinkedList<>(persons.values());
        Collections.sort(sortedByName, new PersonNameComparator());
        for (Person p : sortedByName)
        {
            p.display();
        }
    }

    public void listPersonsByID()
    {
        List<Person> sortedById = new LinkedList<>(persons.values());
        Collections.sort(sortedById, new PersonIdComparator());
        for (Person p : sortedById)
        {
            p.display();
        }
    }

    public void listPersonsByRating()
    {
        List<Person> sortedByRating = new LinkedList<>(persons.values());
        Collections.sort(sortedByRating, new PersonRatingComparator());
        for (Person p : sortedByRating)
        {
            p.display();
        }
    }

    private void readPersonData(String fileName)
    {
        DataInputStream in = null;
        try
        {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            while (in.available() != 0)
            {
                // name (key)
                String keyName = StringUtility.unpad(in.readUTF(), StringUtility.PadString);
                // Person object (value)
                double score = in.readDouble();
                String searchName = StringUtility.unpad(in.readUTF(), StringUtility.PadString);
                String name = StringUtility.unpad(in.readUTF(), StringUtility.PadString);
                int id = in.readInt();
                String imageURL = StringUtility.unpad(in.readUTF(), StringUtility.PadString);
                String personURL = StringUtility.unpad(in.readUTF(), StringUtility.PadString);
                int myRating = in.readInt();
                String myComments = StringUtility.unpad(in.readUTF(), StringUtility.PadString);

                Person p = new Person(score, searchName, name, id, imageURL, personURL, myRating, myComments);
                persons.put(keyName, p);
            }
            in.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("The file was not found on the system.");
        }
        catch (EOFException e)
        {
            System.out.println("End of file reached.");
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    public void writePersonData(String fileName)
    {
        DataOutputStream out = null;
        try
        {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            for (Map.Entry<String, Person> e : persons.entrySet())
            {
                // name (key)
                out.writeUTF(StringUtility.pad(e.getKey(), StringUtility.StandardPadLength, StringUtility.PadString));
                // Person object (value)
                out.writeDouble(e.getValue().getScore());
                out.writeUTF(StringUtility.pad(e.getValue().getQueryName(), StringUtility.StandardPadLength, StringUtility.PadString));
                out.writeUTF(StringUtility.pad(e.getValue().getName(), StringUtility.StandardPadLength, StringUtility.PadString));
                out.writeInt(e.getValue().getId());
                out.writeUTF(StringUtility.pad(e.getValue().getImageUrl(), 100, StringUtility.PadString));
                out.writeUTF(StringUtility.pad(e.getValue().getPersonLink(), 100, StringUtility.PadString));
                out.writeInt(e.getValue().getMyRating());
                out.writeUTF(StringUtility.pad(e.getValue().getMyComments(), 100, StringUtility.PadString) + "\n");
            }
            out.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("The file was not found on the system.");
        }
        catch (IOException e)
        {
            e.printStackTrace(System.out);
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    private void displayEditMenuOptions()
    {
        System.out.println("\n******************************************");
        System.out.println(String.format("%-8s%-33s%-24s", "*", "TV Maze API -- Edit Menu", "*"));
        System.out.println("******************************************");
        System.out.println("\nSelect one of the following options:");
        System.out.println("0 - Exit to main menu");
        System.out.println("1 - Edit rating");
        System.out.println("2 - Edit comments");
        System.out.print("\nOption choice: ");
    }

    public void editPerson(String personName)
    {
        if (persons.containsKey(personName))
        {
            int option = -1;
            while (option != 0)
            {
                displayEditMenuOptions();
                option = Util.inputNumber();
                switch (option)
                {
                    case 0:
                        System.out.println("\nReturning to main menu...");
                        break;
                    case 1:
                        editPersonsRating(personName);
                        break;
                    case 2:
                        editPersonsComments(personName);
                        break;
                    default:
                        System.out.println("\nInvalid option entered.");
                        break;
                }
            }
        }
        else
        {
            System.out.println("\nPerson does not exist in local storage.");
            System.out.println("Hint - Search is case sensitive and requires the full name e.g Sweeney MacArthur, James Remar etc.");
        }
    }

    private void editPersonsRating(String personName)
    {
        System.out.print("\nSelect new rating (1-5): ");
        int ratingInput = Util.inputNumber();
        while (ratingInput < 1 || ratingInput > 5)
        {
            System.out.print("\nRating must be between 1 and 5, try again: ");
            ratingInput = Util.inputNumber();
        }
        persons.get(personName).editRating(ratingInput);
        System.out.println("\nThe rating for " + personName + " has been set to " + ratingInput);
    }

    private void editPersonsComments(String personName)
    {
        System.out.print("\nEnter your comment: ");
        String commentInput = Util.inputString();
        persons.get(personName).editComments(commentInput);
        System.out.println("\nThe comment for " + personName + " has been saved.");
    }

    public void exportToHMTL(LinkedList<Person> persons)
    {
        if (!persons.isEmpty())
        {
            DataOutputStream dos = null;
            try
            {
                File f = createHTMLFile();
                dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f), 2048)); // changed from (f, true) to (f). Do we need 2048? Maybe we should research this???
                dos.writeBytes("<!DOCTYPE html>" + System.lineSeparator());
                dos.writeBytes("<html>" + System.lineSeparator());
                String htmlStyle = "<style>\n"
                        + "\n"
                        + "header, footer {\n"
                        + "    padding: 1em;\n"
                        + "    color: white;\n"
                        + "    background-color: black;\n"
                        + "    clear: left;\n"
                        + "    text-align: center;\n"
                        + "}\n"
                        + "\n"
                        + "nav {\n"
                        + "    float: left;\n"
                        + "    max-width: 160px;\n"
                        + "    margin: 0;\n"
                        + "    padding: 1em;\n"
                        + "}\n"
                        + "\n"
                        + "nav ul {\n"
                        + "    list-style-type: none;\n"
                        + "    padding: 0;\n"
                        + "}\n"
                        + "   \n"
                        + "nav ul a {\n"
                        + "    text-decoration: none;\n"
                        + "}\n"
                        + "\n"
                        + "#personDiv {\n"
                        + "    margin-left: 500px;\n"
                        + "    border-left: 1px solid gray;\n"
                        + "    padding: 1em;\n"
                        + "    overflow: auto;\n"
                        + "    text-align: right;\n"
                        + "}\n"
                        + "\n"
                        + "#personDiv, img\n"
                        + "{\n"
                        + "    float: left; margin: 0px 15px 15px 0px;\n"
                        + "}\n"
                        + "\n"
                        + "            #container, div {\n"
                        + "  float: left;\n"
                        + "  width: 100%;\n"
                        + "}</style>";
                dos.writeBytes("<head>" + System.lineSeparator());
                dos.writeBytes(htmlStyle + System.lineSeparator());
                dos.writeBytes("</head>" + System.lineSeparator());
                dos.writeBytes("<body>" + System.lineSeparator());
                dos.writeBytes("<div class=\"container\">" + System.lineSeparator());
                dos.writeBytes(System.lineSeparator());
                dos.writeBytes("<header>" + System.lineSeparator());
                dos.writeBytes("<h1>Persons</h1>" + System.lineSeparator());
                dos.writeBytes("</header>" + System.lineSeparator());
                dos.writeBytes(System.lineSeparator());
                for (Person person : persons)
                {
                    dos.writeBytes("<div class='personDiv'>" + System.lineSeparator());
                    if (person.getImageUrl() == null || person.getImageUrl().equals(""))
                    {
                        dos.writeBytes("<img src='defaultProfile.png'>" + System.lineSeparator());
                    }
                    else
                    {
                        dos.writeBytes("<img src='" + person.getImageUrl() + "'>" + System.lineSeparator());
                    }
                    dos.writeBytes("<h1>" + person.getName() + "</h1>" + System.lineSeparator());
                    dos.writeBytes("<b>Link to biography: </b> <a href='" + person.getPersonLink() + "'>here</a>." + System.lineSeparator());
                    //Checks if person has been rated.
                    if (person.getMyRating() == -1)
                    {
                        dos.writeBytes("<h2><b>Rating:</b> No Rating Given</h2>" + System.lineSeparator());
                    }
                    else
                    {
                        dos.writeBytes("<h2><b>Rating:</b> " + person.getMyRating() + "</h2>" + System.lineSeparator());
                    }
                    if (person.getMyComments() == null || person.getMyComments().equals(""))
                    {
                        dos.writeBytes("<h2><b>Comment:</b> No Comment Given</h2>" + System.lineSeparator());
                    }
                    else
                    {
                        dos.writeBytes("<h2><b>Comment:</b> " + person.getMyComments() + "</h2>" + System.lineSeparator());
                    }
                    dos.writeBytes("</div>" + System.lineSeparator());
                    dos.writeBytes("<hr>" + System.lineSeparator());
                }
                dos.writeBytes("<footer>Shaun Conroy & Luke O Kane -- OOP CA5.</footer>" + System.lineSeparator());
                dos.writeBytes(System.lineSeparator());
                dos.writeBytes("</div>" + System.lineSeparator());
                dos.writeBytes(System.lineSeparator());
                dos.writeBytes("</body>" + System.lineSeparator());
                dos.writeBytes("</html>" + System.lineSeparator());
                dos.close();

                System.out.println("\nThe HTML file was successfully exported.");
                System.out.print("\nView HTML file now?\n1 - Yes\n2 - No\n\nOption choice: ");
                int option = Util.inputNumber();
                if (option == 1)
                {
                    Desktop.getDesktop().browse(f.toURI());
                }
                else
                {
                    System.out.println("\nReturning to main menu...");
                }
            }
            catch (FileNotFoundException fe)
            {
                fe.printStackTrace(System.out);
            }
            catch (IOException e)
            {
                e.printStackTrace(System.out);
            }
            finally
            {
                if (dos != null)
                {
                    try
                    {
                        dos.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace(System.out);
                    }
                }
            }
        }
        else
        {
            System.out.println("\nThere are no search results to display.");
            System.out.println("Please perform a search from the main menu...");
        }
    }

    private File createHTMLFile()
    {
        System.out.print("\nEnter a name for the HTML file: ");
        String fileName = Util.inputString() + ".html";
        File f = new File(fileName);
        boolean allowOverwrite = false;
        while (f.exists() && !allowOverwrite)
        {
            System.out.println("\nHTML file already exists, do you wish to overwrite it?\n1 - Yes\n2 - No\n\nOption choice: ");
            int option = Util.inputNumber();
            switch (option)
            {
                case 1:
                    allowOverwrite = true;
                    break;
                case 2:
                    System.out.print("Enter a name for the new HTML file: ");
                    fileName = Util.inputString();
                    f = new File(fileName + ".html");
                    break;
                default:
                    System.out.println("Invalid option entered.");
            }
        }
        return f;
    }

}
