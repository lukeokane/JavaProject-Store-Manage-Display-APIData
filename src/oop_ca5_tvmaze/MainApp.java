package oop_ca5_tvmaze;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Luke & Shaun
 */
public class MainApp
{

    public static void main(String[] args)
    {
        MainApp theApp = new MainApp();
        theApp.start();
    }

    private void start()
    {
        PersonStore personStore = new PersonStore();
        personStore.loadPersonStore();
        LinkedList<Person> searchResults = new LinkedList<>();
        int option = -1;
        while (option != 0)
        {
            displayMainMenuOptions();
            option = Util.inputNumber();
            switch (option)
            {
                case 0:
                    personStore.writePersonData("persons.dat");
                    System.out.println("\nExiting...");
                    break;
                case 1:
                    System.out.println("\nEnter a name to search our database:");
                    String searchName = Util.inputName();
                    searchResults = personStore.searchLocalRecords(searchName);
                    if (searchResults.isEmpty())
                    {
                        System.out.println("\nThat person was not found locally.\nSearching TVMaze API...");
                        searchResults = personStore.searchAPI(searchName);
                        displayAPISearchSorted(personStore, searchResults);
                        personStore.writePersonData("persons.dat"); // save data to file
                    }
                    else
                    {
                        System.out.println("\nAll matches found in the local store:");
                        personStore.listPersons(searchResults);
                    }
                    break;
                case 2:
                    System.out.println("\nAll persons sorted by name:");
                    personStore.listPersonsByName();
                    break;
                case 3:
                    System.out.println("\nAll persons sorted by ID:");
                    personStore.listPersonsByID();
                    break;
                case 4:
                    System.out.println("\nAll persons sorted by rating:");
                    personStore.listPersonsByRating();
                    break;
                case 5:
                    System.out.println("\nEnter the full name of the person you wish to edit:");
                    personStore.editPerson(Util.inputName());
                    break;
                case 6:
                    personStore.exportToHMTL(searchResults);
                    break;
                case 7:
                    displayLocalHTMLFilesMenu();
                    break;
                case 8:
                    displayViewProfilesMenuOptions(personStore);
                    break;
                default:
                    System.out.println("\nInvalid option entered.");
            }
        }
    }

    private void displayMenuHeader(String menuType)
    {
        System.out.println("\n******************************************");
        System.out.println(String.format("%-8s%-33s%-24s", "*", "TV Maze API -- " + menuType, "*"));
        System.out.println("******************************************");
    }

    private void displayMainMenuOptions()
    {
        displayMenuHeader("Main Menu");
        System.out.println("\nSelect one of the following options:");
        System.out.println("0 - Exit application");
        System.out.println("1 - Search for a person");
        System.out.println("2 - Display all persons sorted by name");
        System.out.println("3 - Display all persons sorted by ID");
        System.out.println("4 - Display all persons sorted by rating");
        System.out.println("5 - Edit person");
        System.out.println("6 - Export search results to HTML file");
        System.out.println("7 - View local HTML files");
        System.out.println("8 - View Profiles");
        System.out.print("\nOption choice: ");
    }

    private void displayHTMLFileMenuOptions(File[] files)
    {
        displayMenuHeader("HTML Files");
        System.out.println("\nSelect one of the following options:");
        System.out.println("0 - Exit to main menu");
        for (int i = 0; i < files.length; i++)
        {
            System.out.println((i + 1) + " - View " + files[i].getName());
        }
        System.out.print("\nOption choice: ");
    }

    private void displayLocalHTMLFilesMenu()
    {
        File dir = new File("."); // "." means current directory
        File[] fList = dir.listFiles((File f) -> f.getName().endsWith(".html")); // overrides the File.accept()
        if (fList.length != 0)
        {
            int option = -1;
            while (option != 0)
            {
                displayHTMLFileMenuOptions(fList);
                option = Util.inputNumber();
                switch (option)
                {
                    case 0:
                        System.out.println("\nExiting to Main Menu...");
                        break;
                    default:
                        try
                        {
                            if (option > 0 && option <= fList.length)
                            {
                                File temp = new File(fList[option - 1].getName());
                                Desktop.getDesktop().browse(temp.toURI());
                            }
                            else
                            {
                                System.out.println("\nInvalid option entered.");
                            }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace(System.out);
                        }
                        break;
                }
            }
        }
        else
        {
            System.out.println("\nThere are no HTML files to display.");
        }
    }

    private void displayViewProfilesMenu(LinkedList<Person> persons)
    {
        displayMenuHeader("Profiles");
        System.out.println("\nSelect one of the following options:");
        System.out.println("0 - Exit to main menu");
        for (int i = 0; i < persons.size(); i++)
        {
            System.out.println((i + 1) + " - View Profile - " + persons.get(i).getName());
        }
        System.out.print("\nOption choice: ");
    }

    private void displayViewProfilesMenuOptions(PersonStore personStore)
    {
        LinkedList<Person> persons = new LinkedList(personStore.getPersons().values());
        if (!persons.isEmpty())
        {
            int option = -1;
            while (option != 0)
            {
                displayViewProfilesMenu(persons);
                option = Util.inputNumber();
                switch (option)
                {
                    case 0:
                        System.out.println("\nExiting to Main Menu...");
                        break;
                    default:
                        try
                        {
                            if (option > 0 && option <= persons.size())
                            {
                                String temp = persons.get(option - 1).getPersonLink();
                                Desktop.getDesktop().browse(new URL(temp).toURI());
                            }
                            else
                            {
                                System.out.println("\nInvalid option entered.");
                            }
                        }
                        catch (IOException | URISyntaxException e)
                        {
                            e.printStackTrace(System.out);
                        }
                        break;
                }
            }
        }
        else
        {
            System.out.println("\nThere are no profiles to display.");
        }
    }

    private void displayAPISearchSorted(PersonStore personStore, LinkedList<Person> searchResults)
    {
        Collections.sort(searchResults, new PersonScoreComparator());
        System.out.println("\nAll matches found on TVMaze API: ");
        personStore.listPersons(searchResults);
    }

}

