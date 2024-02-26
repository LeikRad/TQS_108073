/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocoding;

import connection.ISimpleHttpClient;
import org.apache.http.ParseException;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * calls external api to perform the reverse geocode
 *
 * @author ico
 */
public class AddressResolverService {

    private final ISimpleHttpClient httpClient;

    public AddressResolverService(ISimpleHttpClient httpClient) {

        this.httpClient = httpClient;
    }


    public Optional<Address> findAddressForLocation(double latitude, double longitude) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {

        String endpointUri = prepareUriForRemoteEndpoint(latitude, longitude);

        String response = httpClient.doHttpGet(endpointUri);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, response);

        // get parts from response till reaching the address
        JSONObject obj = (JSONObject) new JSONParser().parse(response);
        obj = (JSONObject) ((JSONArray) obj.get("results")).get(0);
        if (((JSONArray) obj.get("locations")).isEmpty()) {
            return Optional.empty();
        } else {
            JSONObject address = (JSONObject) ((JSONArray) obj.get("locations")).get(0);
            String road = (String) address.get("street");
            String city = (String) address.get("adminArea5");
            String zip = (String) address.get("postalCode");
            return Optional.of(new Address(road, city, zip, ""));
        }
    }

    private static String prepareUriForRemoteEndpoint(double latitude, double longitude) throws URISyntaxException {

        //TODO: set the API key in the resources/config.properties file
        String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

        URIBuilder uriBuilder = new URIBuilder("https://www.mapquestapi.com/geocoding/v1/reverse");
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.5f,%.5f", latitude, longitude).toString());
        uriBuilder.addParameter("outFormat", "json");
        uriBuilder.addParameter("thumbMaps", "false");
        return uriBuilder.build().toString();
    }
}
