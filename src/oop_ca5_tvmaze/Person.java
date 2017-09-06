package oop_ca5_tvmaze;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 *
 * @author Luke & Shaun
 */
public class Person
{

    private double score;
    private String queryName, name;
    private int id;
    private String imageUrl, personLink;
    private int myRating;
    private String myComments;

    public Person(double score, String queryName, String name, int id, String imageUrl, String personLink, int myRating, String myComments)
    {
        this.setScore(score);
        this.setQueryName(queryName);
        this.setName(name);
        this.setId(id);
        this.setImageUrl(imageUrl);
        this.setPersonLink(personLink);
        this.setMyRating(myRating);
        this.setMyComments(myComments);
    }
    
    public void display()
    {
        DecimalFormat round = new DecimalFormat("#.00");
        String boldStart = "\033[0;1m";
        String boldEnd = "\033[0m";
        System.out.println(boldStart + this.getName() + boldEnd);
        System.out.println(String.format("%-16s%-16s", "ID", this.getId()));
        System.out.println(String.format("%-16s%-16s", "Score", round.format(this.getScore())));
        System.out.println(String.format("%-16s%-16s", "Query Name", this.getQueryName()));
        System.out.println(String.format("%-16s%-16s", "Image URL", this.getImageUrl()));
        System.out.println(String.format("%-16s%-16s", "Bio Link", this.getPersonLink()));
        System.out.println(String.format("%-16s%-16s", "Rating", this.getMyRating()));
        System.out.println(String.format("%-16s%-16s", "Comments", this.getMyComments()));
        System.out.println();
    }
    
    private String validateString(String data, String defaultValue, int minLength, int maxLength)
    {
        // if data is too short OR too long then return defaultValue, else return data
        return (data.length() < minLength || data.length() > maxLength) ? defaultValue : data;
    }
    
    private int validateNumber(int data, int defaultValue, int minValue, int maxValue)
    {
        // if data is less than minValue OR more than max value then return defaultValue, else return data
        return (data < minValue || data > maxValue) ? defaultValue : data;
    }
    
    private double validateNumber(double data, double defaultValue, double minValue, double maxValue)
    {
        return (data < minValue || data > maxValue) ? defaultValue : data;
    }

    public double getScore()
    {
        return score;
    }

    private void setScore(double score)
    {
        this.score = validateNumber(score, -1, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public int getId()
    {
        return id;
    }

    private void setId(int id)
    {
        this.id = validateNumber(id, -1, 1, Integer.MAX_VALUE);
    }

    public int getMyRating()
    {
        return myRating;
    }

    private void setMyRating(int myRating)
    {
        this.myRating = validateNumber(myRating, -1, 1, 5);
    }

    public void editRating(int myRating)
    {
        setMyRating(myRating);
    }

    public String getQueryName()
    {
        return queryName;
    }

    private void setQueryName(String queryName)
    {
        this.queryName = validateString(queryName, "default query name", 3, 40);
    }

    public String getName()
    {
        return name;
    }

    private void setName(String name)
    {
        this.name = validateString(name, "default name", 4, 40);
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    private void setImageUrl(String imageUrl)
    {
        this.imageUrl = validateString(imageUrl, "defaultProfile.png", 4, 100);
    }

    public String getPersonLink()
    {
        return personLink;
    }

    private void setPersonLink(String personLink)
    {
        this.personLink = validateString(personLink, "http://www.tvmaze.com/people", 4, 100);
    }

    public String getMyComments()
    {
        return myComments;
    }

    private void setMyComments(String myComments)
    {
        this.myComments = validateString(myComments, "default comments", 4, 100);
    }

    public void editComments(String myComments)
    {
        setMyComments(myComments);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.score) ^ (Double.doubleToLongBits(this.score) >>> 32));
        hash = 47 * hash + this.id;
        hash = 47 * hash + this.myRating;
        hash = 47 * hash + Objects.hashCode(this.queryName);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.imageUrl);
        hash = 47 * hash + Objects.hashCode(this.personLink);
        hash = 47 * hash + Objects.hashCode(this.myComments);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Person other = (Person) obj;
        if (Double.doubleToLongBits(this.score) != Double.doubleToLongBits(other.score))
        {
            return false;
        }
        if (this.id != other.id)
        {
            return false;
        }
        if (this.myRating != other.myRating)
        {
            return false;
        }
        if (!Objects.equals(this.queryName, other.queryName))
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl))
        {
            return false;
        }
        if (!Objects.equals(this.personLink, other.personLink))
        {
            return false;
        }
        if (!Objects.equals(this.myComments, other.myComments))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Person{" + "score=" + score + ", queryName=" + queryName + ", name=" + name + ", id=" + id + ", imageUrl=" + imageUrl + ", personLink=" + personLink + ", myRating=" + myRating + ", myComments=" + myComments + '}';
    }

}
