package sihab.burma.n01659348;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class N01659348Fragment extends Fragment {

    private AutoCompleteTextView autoCompleteCity;
    private Button submitButton;
    private ProgressBar progressBar;
    private String[] capitalCities;  // Cities array
    private String[] countries;  // Countries array

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_n01659348, container, false);

        // Initialize Views
        autoCompleteCity = view.findViewById(R.id.autoCompleteCity);
        submitButton = view.findViewById(R.id.submitButton);
        progressBar = view.findViewById(R.id.progressBar);

        // Load Cities Array from strings.xml
        capitalCities = getResources().getStringArray(R.array.sihab);  // First Name Array
        countries = getResources().getStringArray(R.array.burma);  // Last Name Array

        // Set Up AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, capitalCities);
        autoCompleteCity.setAdapter(adapter);

        // Submit Button Click Listener
        submitButton.setOnClickListener(v -> validateInput());

        return view;
    }

    private void validateInput() {
        String userInput = autoCompleteCity.getText().toString().trim();
        if (userInput.isEmpty()) {
            autoCompleteCity.setError("Cannot be empty");
            return;
        }

        // Check if input is in the cities array
        int index = -1;
        for (int i = 0; i < capitalCities.length; i++) {
            if (capitalCities[i].equalsIgnoreCase(userInput)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            autoCompleteCity.setError("Mohammad Sihab Burma, " + userInput + " is not a valid capital");
        } else {
            // Pass index to next screen (if needed)
            Toast.makeText(getContext(), "Valid! Index: " + index, Toast.LENGTH_SHORT).show();
            autoCompleteCity.setText("");  // Clear input
        }
    }
}
