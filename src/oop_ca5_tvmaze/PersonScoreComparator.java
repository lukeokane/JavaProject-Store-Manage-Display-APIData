package oop_ca5_tvmaze;

import java.util.Comparator;

/**
 *
 * @author Shauns
 */
public class PersonScoreComparator implements Comparator<Person>
{

    @Override
    public int compare(Person p1, Person p2)
    {
        return Double.compare(p1.getScore(), p2.getScore());
    }
}
