package sihab.burma.n01659348;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import sihab.burma.n01659348.R;

public class SihFragment extends Fragment {

    private TextView sihNameTextView, sihClockTextView;
    private Handler handler = new Handler();
    private Runnable updateTimeRunnable;
    private static int exitCounter = 0; // Counter for tab switches

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_sihab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        sihNameTextView = view.findViewById(R.id.sihTextView);
        sihClockTextView = view.findViewById(R.id.sihClockTextView);

        // Start updating the clock every second
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateClock();
                handler.postDelayed(this, 1000); // Update every second
            }
        };
        handler.post(updateTimeRunnable);
    }

    // Method to update clock in 12-hour format with AM/PM and GMT timezone
    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a, MMM dd yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // Set GMT time zone
        String formattedTime = sdf.format(new Date());
        sihClockTextView.setText(formattedTime);
    }

    @Override
    public void onPause() {
        super.onPause();
        exitCounter++; // Increment counter when leaving this fragment
        Toast.makeText(requireContext(), "Mohammad Sihab Burma - Visited " + exitCounter + " times", Toast.LENGTH_SHORT).show();
    }
}
