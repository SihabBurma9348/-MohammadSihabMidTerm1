package sihab.burma.n01659348;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SihActivity1 extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        viewPager = findViewById(R.id.sihViewPager);
        tabLayout = findViewById(R.id.sihTabLayout);

        // Set up ViewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            String[] tabTitles = {
                    getString(R.string.sihab_s_tab),      // First Tab
                    getString(R.string.burma_s_tab),      // Second Tab
                    getString(R.string.n01659348_s_tab)   // Third Tab
            };
            tab.setText(tabTitles[position]); // Set Tab Titles
        }).attach();
    }
}
