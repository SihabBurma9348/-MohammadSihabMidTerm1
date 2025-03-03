package sihab.burma.n01659348;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import sihab.burma.n01659348.R;

public class BurFragment extends Fragment {

    private TextView burTextView;
    private RatingBar burRatingBar;
    private Button burSubmitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_burma, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Views
        burTextView = view.findViewById(R.id.burTextView);
        burRatingBar = view.findViewById(R.id.burRatingBar);
        burSubmitButton = view.findViewById(R.id.burSubmitButton);

        // Retrieve Data Passed from N11111Fragment (Third Fragment)
        Bundle args = getArguments();
        if (args != null && args.containsKey("capital") && args.containsKey("country") && args.containsKey("index")) {
            String capital = args.getString("capital");
            String country = args.getString("country");
            int index = args.getInt("index");

            // Display the passed data
            burTextView.setText("Capital: " + capital + ", Country: " + country + ", Index: " + index);
        } else {
            // Show "NO DATA PASSED" if no data was received
            burTextView.setText("NO DATA PASSED");
            burTextView.setTextSize(View.TEXT_ALIGNMENT_TEXT_END); // Italic text
        }

        // Handle Rating Bar Button Click
        burSubmitButton.setOnClickListener(v -> {
            float rating = burRatingBar.getRating(); // Get selected rating
            Snackbar.make(view, "Selected Rating: " + rating + " stars", Snackbar.LENGTH_SHORT).show();
        });
    }
}
