
package com.sparkpost.resources;

import com.sparkpost.exception.SparkPostException;
import com.sparkpost.model.SuppressionList;
import com.sparkpost.model.SuppressionListEntry;
import com.sparkpost.model.responses.Response;
import com.sparkpost.transport.IRestConnection;

/**
 * Resource collection that is a 1-to-1 match to the Suppression List SparkPost
 * API.<br>
 * <br>
 * See <a href="https://www.sparkpost.com/api#/reference/suppression-list/">
 * Suppression List API</a>
 *
 * @author grava
 */
public class ResourceSuppressionList {

    public static Response insertOrUpdate(IRestConnection conn, String email, final SuppressionListEntry entry) throws SparkPostException {

        // we need a copy because we don't want the email field in the json:
        SuppressionListEntry copy = new SuppressionListEntry(entry);
        copy.setEmail(null);

        String json = copy.toJson();
        Endpoint ep = new Endpoint("suppression-list/" + email);
        Response response = conn.put(ep, json);
        return response;
    }

    public static Response insertOrUpdateBulk(IRestConnection conn, SuppressionList suppressionList) throws SparkPostException {

        String json = suppressionList.toJson();
        Endpoint ep = new Endpoint("suppression-list/");
        return conn.put(ep, json);
    }

    public static Response search(IRestConnection conn, String to, String from, String types, String limit) throws SparkPostException {

        Endpoint ep = new Endpoint("suppression-list");
        ep.addParam("to", to);
        ep.addParam("from", from);
        ep.addParam("types", types);
        ep.addParam("limit", limit);
        Response response = conn.get(ep);
        return response;
    }

    public static Response check(IRestConnection conn, String email) throws SparkPostException {

        Endpoint ep = new Endpoint("suppression-list/" + email);
        Response response = conn.get(ep);
        return response;
    }

    public static Response remove(IRestConnection conn, String email) throws SparkPostException {
        Endpoint ep = new Endpoint("suppression-list/" + email);
        Response response = conn.delete(ep);
        return response;
    }
}
