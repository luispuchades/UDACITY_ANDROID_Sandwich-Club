package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /* Variable for Sandwich class*/
    private Sandwich sandwich;

    /* Creation of variables for TextViews to capture */
    private TextView sandwichMainName;
    private TextView sandwichAlsoKnownAs;
    private TextView sandwichPlaceOfOrigin;
    private TextView sandwichDescription;
    private TextView sandwichIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());



    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        /* Capture of Views from activity_detail.xml */
        sandwichMainName = findViewById(R.id.sandwich_main_name_tv);
        sandwichAlsoKnownAs = findViewById(R.id.also_known_tv);
        sandwichPlaceOfOrigin = findViewById(R.id.origin_tv);
        sandwichDescription = findViewById(R.id.description_tv);
        sandwichIngredients = findViewById(R.id.ingredients_tv);

        /* Set text from JSON to TextViews*/
        sandwichMainName.setText(checkData(sandwich.getMainName()));
        sandwichPlaceOfOrigin.setText(checkData(sandwich.getPlaceOfOrigin()));
        sandwichDescription.setText(checkData(sandwich.getDescription()));

        /* Capture of array list for other names for this sandwich */
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        for ( int i = 0; i < alsoKnownAsList.size(); i++) {
            if( i > 0 ) {
                sandwichAlsoKnownAs.append(", ");
            }
            sandwichAlsoKnownAs.append(alsoKnownAsList.get(i));
        }

        /* Capture of array list for ingredients */
        List<String> ingredientsList = sandwich.getIngredients();
        for ( int i = 0; i < ingredientsList.size(); i++) {
            if( i > 0) {
                sandwichIngredients.append(", ");
            }
            sandwichIngredients.append(ingredientsList.get(i));
        }
    }


    private String checkData(String string) {
        if (string.equals("")){
            return getString(R.string.no_data);
        } else {
            return string;
        }
    }
}
