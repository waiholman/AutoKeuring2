package marek.autokeuring;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int CurrentPage = 1;
    private int CurrentCategory = 1;

    public void onNextButtonClick(View v)
    {

        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);

        CurrentPage++;

        Content con = db.findProduct(CurrentCategory, CurrentPage);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, APKFragment.newInstance(con._category, con._page, con._maxPage, con._content, con._categoryName))
                .commit();
    }

    public void onPreviousClick(View v)
    {
        if (CurrentPage == 1)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(2))
                    .commit();

            return;
        }

        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);

        CurrentPage--;

        Content con = db.findProduct(CurrentCategory, CurrentPage);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, APKFragment.newInstance(con._category, con._page, con._maxPage, con._content, con._categoryName))
                .commit();
    }

    public void onCategoryClick(View v)
    {
        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);
        if (v == findViewById(R.id.lblCategoryBanden))
        {
            CurrentCategory = 1;
            CurrentPage = 1;
        }else if (v == findViewById(R.id.lblCategorySchade))
        {
            CurrentCategory = 2;
            CurrentPage = 1;
        }else if (v == findViewById(R.id.lblCategoryVerlichting))
        {
            CurrentCategory = 3;
            CurrentPage = 1;
        }else if (v == findViewById(R.id.lblCategoryDeuren))
        {
            CurrentCategory = 4;
            CurrentPage = 1;
        }else if (v == findViewById(R.id.lblCategorymotorkap))
        {
            CurrentCategory = 5;
            CurrentPage = 1;
        }else if (v == findViewById(R.id.lblCategoryUitlaat))
        {
            CurrentCategory = 6;
            CurrentPage = 1;
        }else if (v == findViewById(R.id.lblCategoryInAuto))
        {
            CurrentCategory = 7;
            CurrentPage = 1;
        }else if (v == findViewById(R.id.lblCategoryHandrem))
        {
            CurrentCategory = 8;
            CurrentPage = 1;
        }

        Content con = db.findProduct(CurrentCategory, CurrentPage);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, APKFragment.newInstance(con._category, con._page, con._maxPage, con._content, con._categoryName))
                .commit();
    }

    public void onTestClick(View v)
    {
        DatabaseHelper db = new DatabaseHelper(this, null, null, 1);

        Content b = db.findProduct(1, 3);
        TextView ab = (TextView)findViewById(R.id.result);
        ab.setText(b._content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            int sectionnumber = getArguments().getInt(ARG_SECTION_NUMBER);

            getViewFromSectionID(sectionnumber);

            View rootView = inflater.inflate(getViewFromSectionID(sectionnumber), container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        private int getViewFromSectionID(int sectionnumber)
        {
            if(sectionnumber == 1)
            {
                return R.layout.welcomescreen;
            }else if (sectionnumber == 2)
            {
                return R.layout.apkstartscreen;
            }else if (sectionnumber == 3)
            {
                return R.layout.appinfoscreen;
            }else
            {
                return R.layout.unknownscreen;
            }

        }

    }

    public static class APKFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_CONTENT_TEXT = "content_text";
        private static final String ARG_PAGE = "page";
        private static final String ARG_MAX_PAGE = "max_page";
        private static final String ARG_CATEGORY_NAME = "category_name";
        private static final String ARG_CATEGORY = "category";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static APKFragment newInstance(int category, int page, int max_page, String content, String category_name) {
            APKFragment fragment = new APKFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            args.putInt(ARG_CATEGORY, category);
            args.putInt(ARG_MAX_PAGE, max_page);
            args.putString(ARG_CONTENT_TEXT, content);
            args.putString(ARG_CATEGORY_NAME, category_name);
            fragment.setArguments(args);
            return fragment;
        }

        public APKFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            int sectionnumber = getArguments().getInt(ARG_SECTION_NUMBER);

            int page = getArguments().getInt(ARG_PAGE);
            int category = getArguments().getInt(ARG_CATEGORY);
            int max_page = getArguments().getInt(ARG_MAX_PAGE);
            String content = getArguments().getString(ARG_CONTENT_TEXT);
            String categoryName = getArguments().getString(ARG_CATEGORY_NAME);

            String pageString = Integer.toString(page) + "/" + Integer.toString(max_page);
            View rootView = inflater.inflate(R.layout.apkcheckstep, container, false);

            TextView categorycontent = (TextView)rootView.findViewById(R.id.txtApkCheckStep_Information);
            TextView txtCategoryName = (TextView)rootView.findViewById(R.id.txtCategory);
            TextView step = (TextView)rootView.findViewById(R.id.txtStep);

            Button prevButton = (Button)rootView.findViewById(R.id.btnPrevious);
            Button nextButton = (Button)rootView.findViewById(R.id.btnNext);

            if(page == max_page)
            {
                nextButton.setEnabled(false);
            }

            LinearLayout lin = (LinearLayout)rootView.findViewById(R.id.gallery);

            if(category == 1 && page == 1)
            {
                ImageView image1 = getImageview(rootView.getResources().getDrawable(R.drawable.banden_04));
                ImageView image2 = getImageview(rootView.getResources().getDrawable(R.drawable.banden_05));
                ImageView image3 = getImageview(rootView.getResources().getDrawable(R.drawable.banden_06));

                lin.addView(image1);
                lin.addView(image2);
                lin.addView(image3);
            }else if (category == 1 && page == 2)
            {
                ImageView image1 = getImageview(rootView.getResources().getDrawable(R.drawable.banden_01));
                ImageView image2 = getImageview(rootView.getResources().getDrawable(R.drawable.banden_02));

                lin.addView(image1);
                lin.addView(image2);
            }else if (category == 1 && page == 3)
            {
                ImageView image1 = getImageview(rootView.getResources().getDrawable(R.drawable.banden_03));

                lin.addView(image1);
            }else if (category == 3 && page == 1)
            {
                ImageView image1 = getImageview(rootView.getResources().getDrawable(R.drawable.schade));

                lin.addView(image1);
            }else if (category == 7 && page == 1)
            {
                ImageView image1 = getImageview(rootView.getResources().getDrawable(R.drawable.gordel));
                ImageView image2 = getImageview(rootView.getResources().getDrawable(R.drawable.gordel_02));


                lin.addView(image1);
                lin.addView(image2);
            }else if (category == 7 && page == 2)
            {
                ImageView image1 = getImageview(rootView.getResources().getDrawable(R.drawable.voorruit));

                lin.addView(image1);
            }

            txtCategoryName.setText(categoryName);
            categorycontent.setText(content);
            step.setText(pageString);
            return rootView;
        }

        private ImageView getImageview(Drawable draw)
        {
            ImageView imageView = new ImageView(getActivity());
            //imageView.setMaxHeight(50);
            imageView.setImageDrawable(draw);
            return imageView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        private int getViewFromSectionID(int sectionnumber)
        {
            if(sectionnumber == 1)
            {
                return R.layout.welcomescreen;
            }else if (sectionnumber == 2)
            {
                return R.layout.apkstartscreen;
            }else if (sectionnumber == 3)
            {
                return R.layout.appinfoscreen;
            }else
            {
                return R.layout.unknownscreen;
            }

        }

    }

}
