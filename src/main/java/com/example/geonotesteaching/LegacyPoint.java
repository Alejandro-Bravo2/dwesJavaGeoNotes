package com.example.geonotesteaching;

public class LegacyPoint {
    double lat;
    double lon;

    public LegacyPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj instanceof LegacyPoint) {
                if (((LegacyPoint) obj).lat == this.lat && ((LegacyPoint) obj).lon == this.lon) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        long lat = Double.doubleToLongBits(this.lat);
        long lon = Double.doubleToLongBits(this.lon);
        String latAndLon = lat + "" + lon;

        int result = 1;

        int latHash = Long.hashCode(lat);
        result = 4 * result + latHash;

        int lonHash = Long.hashCode(lon);
        result = 4 * result + lonHash;


        return result;
    }


    @Override
    public String toString() {
        return "LegacyPoint[lat=" + this.lat + ", lon=" + this.lon + "]";
    }
}
