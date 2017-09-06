package oop_ca5_tvmaze;

import java.util.Comparator;

/**
 *
 * @author Shauns
 */
public class PersonRatingComparator implements Comparator<Person>
{

    @Override
    public int compare(Person p1, Person p2)
    {
        return p2.getMyRating() - p1.getMyRating(); // sort by highest rating descending
    }
}
