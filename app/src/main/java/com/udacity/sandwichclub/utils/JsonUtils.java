package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    /* Get the JSON class simple name in order to have better information under exceptions thrown */
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /*
     * Defining variables from fields to parse with JSON. These fields are extracted from strings
     * .xml within the label <string-array>
     **/
    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {

        try {

            /* Defining the JSON Object */
            JSONObject sandwichJson = new JSONObject(json);

            /* Defining the JSON first level element "name" */
            JSONObject jsonObjectName = sandwichJson.getJSONObject(SANDWICH_NAME);

            /* Creating a String for "mainName" and extracting it from "name"*/
            String jsonObjectMainName = jsonObjectName.getString(SANDWICH_MAIN_NAME);

            /* Creating a JSON Array for "alsoKnownAs" array - STEP 1 */
            JSONArray jsonArrayAlsoKnownAs = jsonObjectName.getJSONArray(SANDWICH_ALSO_KNOWN_AS);

            /* Creating a String ArrayList to store the array list values - STEP 2*/
            ArrayList<String> jsonaArrayAlsoKnownAsList = new ArrayList<>();

            /* Extracting alsoKnownAs values to alsoKnownAs List " array from "name" - STEP 3 -
            Recorrer el array para extraer datos */
            /*Check if the alsoKnownAs field is not null*/
            if (jsonArrayAlsoKnownAs != null) {
                for( int i = 0; i < jsonArrayAlsoKnownAs.length(); i++ ) {
                    try {
                        jsonaArrayAlsoKnownAsList.add(jsonArrayAlsoKnownAs.getString(i));
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "Error parsing alsoKnownAs array", e);
                    }
                }
            }

            /* Creating a String for "placeOfOrigin" and extracting it from "name" main element */
            String jsonObjectPlaceOfOrigin = jsonObjectName.getString(SANDWICH_PLACE_OF_ORIGIN);

            /* Creating a String for "description" and extracting it from "name"*/
            String jsonObjectDescription = jsonObjectName.getString(SANDWICH_DESCRIPTION);

            /* Creating a String for "images" link and extracting it from "name"*/
            String jsonObjectImage = jsonObjectName.getString(SANDWICH_IMAGE);

            /* Creating a JSON Array for "ingredients" array - STEP 1*/
            JSONArray jsonArrayIngredients = jsonObjectName.getJSONArray(SANDWICH_INGREDIENTS);

            /* Creating a String ArrayList to stroe the array list values - STEP 2 */
            ArrayList<String> jsonArrayIngredientsList = new ArrayList<>();

            /* Extracting ingredients values to ingredients list - STEP 3*/
            /* Check if ingredients field is not null */
            if ( jsonArrayIngredients != null ) {
                for (int i = 0; i < jsonArrayIngredients.length(); i++) {
                    try {
                        jsonArrayIngredientsList.add(jsonArrayIngredients.getString(i));
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "Error parsing ingredients array", e);
                    }
                }
            }

            /* return new Sandwich*/
            return new Sandwich(jsonObjectMainName, jsonaArrayAlsoKnownAsList,
                    jsonObjectPlaceOfOrigin, jsonObjectDescription, jsonObjectImage,
                    jsonArrayIngredientsList);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON file", e);
        }
        return null;
    }
}
