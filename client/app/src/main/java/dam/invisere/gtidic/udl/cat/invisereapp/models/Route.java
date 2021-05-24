package dam.invisere.gtidic.udl.cat.invisereapp.models;

import com.google.gson.annotations.SerializedName;

public class Route {

    @SerializedName("name")
    private String name;
    @SerializedName("distance")
    private Float distance;
    @SerializedName("difficulty")
    private String difficulty;
    @SerializedName("points")
    private Place[] points;

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Place[] getPoints() {
        return points;
    }

    public void setPoints(Place[] points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }


    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Route)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Route r = (Route) o;

        // Compare the data members and return accordingly
        return this.name == r.getName()
                && this.getDistance() == r.getDistance()
                && this.difficulty == r.getDifficulty();
    }
}
