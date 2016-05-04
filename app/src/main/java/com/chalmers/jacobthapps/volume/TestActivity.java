package com.chalmers.jacobthapps.volume;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.PolygonArea;
import net.sf.geographiclib.PolygonResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestActivity extends AppCompatActivity implements LocationListener {

    private ListView menuList;
    private TextView latitudeField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    private ImageButton addButton;
    private double latitude;
    private double longitude;
    private List<Coordinate> coordinates = new ArrayList<>();
    private Button areaButton;
    private ListView posList;
    private CustomListAdapter adapter;
    private ArrayList<String> arrayList;
    private ArrayList<Integer> imgList;
    private TabHost tabHost;
    private int currentTab = 0;
    private Button saveButton;
    private Database database;
    private String volumeSave;
    private String areaSave;
    private String weightSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        setupMenu();
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position == 0) {
                    onRestart();
                }
                else {
                    Intent i = new Intent(TestActivity.this, SaveActivity.class);
                    startActivity(i);
                }
            }
        });

        database = new Database(this);

        latitudeField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.poslayout);
        tabSpec.setIndicator("Area");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.vollayout);
        tabSpec.setIndicator("Values");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.resultlayout);
        tabSpec.setIndicator("Result");
        tabHost.addTab(tabSpec);

        setTabColor(tabHost);

        currentTab = tabHost.getCurrentTab();

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Animations a = new Animations();
                View currentView = tabHost.getCurrentView();
                if (tabHost.getCurrentTab() > currentTab)
                {
                    currentView.setAnimation(a.inFromRightAnimation());
                }
                else
                {
                    currentView.setAnimation(a.outToRightAnimation());
                }

                currentTab = tabHost.getCurrentTab();
            }
        });

        arrayList = new ArrayList<>();
        imgList = new ArrayList<>();

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }

        addButton = (ImageButton) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                coordinates.add(new Coordinate(latitude,longitude));
                arrayList.add(latitude + ", " + longitude);
                imgList.add(R.mipmap.posic);
                adapter.notifyDataSetChanged();
            }
        });

        areaButton = (Button) findViewById(R.id.volumeb);
        areaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PolygonArea p = new PolygonArea(Geodesic.WGS84, false);
                for(int i = 0; i < coordinates.size(); i++) {
                    p.AddPoint(coordinates.get(i).getLat(), coordinates.get(i).getLng());
                }

                PolygonResult pr = p.Compute();
                double area = Math.abs(pr.area);

                Results r = getResults(area);

                TextView volumeText = (TextView)findViewById(R.id.volumeResult);
                TextView areaText = (TextView)findViewById(R.id.areaResult);
                TextView weightText = (TextView)findViewById(R.id.weightResult);

                volumeText.setText(r.getVolume()+ " m^3");
                areaText.setText(r.getArea() + " m^2");
                weightText.setText(r.getWeight() + " kg");

                volumeSave = r.getVolume() + "";
                areaSave = r.getArea() + "";
                weightSave = r.getWeight() + "";

                tabHost.setCurrentTabByTag("tag3");
            }
        });

        adapter=new CustomListAdapter(this, arrayList, imgList);
        posList=(ListView)findViewById(R.id.listView);
        posList.setAdapter(adapter);

        posList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                arrayList.remove(position);
                coordinates.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDialog();
            }
        });

        setNumeric();
    }

    private void setupMenu() {
        List<String> arrayList = new ArrayList<>();
        List<Integer> imgList = new ArrayList<>();

        arrayList.add("New");
        arrayList.add("Saved Data");
        imgList = new ArrayList<>();
        imgList.add(R.mipmap.posic);
        imgList.add(R.mipmap.saveic);

        CustomListAdapter adapter=new CustomListAdapter(this, arrayList, imgList);
        menuList=(ListView)findViewById(R.id.menuItems);
        menuList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude =  location.getLatitude();
        longitude = location.getLongitude();
        latitudeField.setText(String.valueOf(latitude));
        longitudeField.setText(String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    private void setTabColor(TabHost tabHost) {
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            tv.setAllCaps(false);
        }
    }

    private void setNumeric() {
        EditText heightEdit = (EditText)findViewById(R.id.editHeight);
        EditText densityEdit = (EditText)findViewById(R.id.editDensity);
        EditText angleEdit = (EditText)findViewById(R.id.editAngle);

        heightEdit.setRawInputType(Configuration.KEYBOARD_QWERTY);
        densityEdit.setRawInputType(Configuration.KEYBOARD_QWERTY);
        angleEdit.setRawInputType(Configuration.KEYBOARD_QWERTY);
    }

    private Results getResults(double area) {
        EditText heightEdit = (EditText)findViewById(R.id.editHeight);
        EditText densityEdit = (EditText)findViewById(R.id.editDensity);
        EditText angleEdit = (EditText)findViewById(R.id.editAngle);

        double height = 0, density = 0, angle = 0;

        try {
            height = Double.parseDouble(heightEdit.getText().toString());
            density = Double.parseDouble(densityEdit.getText().toString());
            angle = Double.parseDouble(angleEdit.getText().toString());

        }catch (NumberFormatException e) {}

        return new Results(area, height, density, angle);
    }

    private void handleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save data");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        input.setHint("Name");
        builder.setView(input);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                if(saveData(name)) {
                    Toast.makeText(TestActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private boolean saveData(String name) {
        Date date=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);

        return database.insertData(name, formattedDate, volumeSave, areaSave, weightSave);
    }
}
