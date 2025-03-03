package sihab.burma.n01659348;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_TABS = 3; // Number of tabs

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SihabFragment();  // First tab fragment
            case 1:
                return new BurmaFragment();  // Second tab fragment
            case 2:
                return new N01659348Fragment();  // Third tab fragment
            default:
                return new SihabFragment(); // Fallback to default fragment
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
