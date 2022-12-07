package com.destiny.origin.data.graph;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-12-07 4:25 PM
 */
public class Town implements Comparable<Town>{


    private String name;
    private Town templateTown;

    /**
     * A default constructor of town
     * @param name The name of the town
     */
    public Town(String name) {
        this.name = name;
    }

    /**
     * Copy
     * @param templateTown an instance of town
     */
    public Town(Town templateTown) {
        this.templateTown = templateTown;
    }

    /**
     * @return town's name
     */
    public String getName() {
        return name;

    }

    /**
     * This method specified by compareTo in interface java.lang.Comparable<Town>
     * @return This method returns 0 if names are equal, a positive or negative number if the names are not equal
     */
    @Override
    public int compareTo(Town o) {

        if(name.equals(o.name))
            return 0;
        else if(name.compareTo(o.name)>0)
            return 1;
        else
            return -1;
    }

    /**
     * @return This method returns the town name
     */
    public String toString(){

        return name;
    }

    /**
     * This method calculates and returns the hashCode for the name of the town
     * @return This method returns the hashCode for the name of the town
     */
    @Override
    public int hashCode() {
        int hash = 0;
        int n = name.length();
        for (int i = 0; i < n; i++) {
            hash = 31 * hash + name.charAt(i);
        }
        return hash;
    }

    /**
     * This method determines if the town names are equal
     * @return this method returns true if the town names are equal, false if not
     */
    public boolean equals(Town obj) {
        if(obj.name.equals(name))
            return true;
        else
            return false;
    }
}
