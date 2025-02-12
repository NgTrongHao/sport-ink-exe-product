package rubberduck.org.sportinksystemalt.shared.common.util;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;

public class GeohashUtil {
    /**
     * Encodes the given latitude and longitude into a geohash string.
     *
     * @param latitude  the latitude to encode
     * @param longitude the longitude to encode
     * @return the geohash string
     */
    public static String encode(double latitude, double longitude) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, 12);
        return geoHash.toBase32();
    }

    /**
     * Decodes the given geohash string into a WGS84Point.
     *
     * @param geohash the geohash string to decode
     * @return the decoded WGS84Point
     */
    public static WGS84Point decode(String geohash) {
        GeoHash geoHash = GeoHash.fromGeohashString(geohash);
        return geoHash.getBoundingBoxCenter();
    }
}
