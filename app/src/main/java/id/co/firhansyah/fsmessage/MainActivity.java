package id.co.firhansyah.fsmessage;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import id.co.firhansyah.fsmessage.fragments.InboxFragment;
import id.co.firhansyah.fsmessage.fragments.SentFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout flMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSendSMS = new Intent(MainActivity.this, SendSMSActivity.class);
                startActivity(iSendSMS);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        flMain = (FrameLayout) findViewById(R.id.flMain);

        initPermission();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            initFragment("inbox");
        } else if (id == R.id.nav_sent) {
            initFragment("sent");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200 : {
                boolean isGranted = true;
                int lenghtPermissions = permissions.length;
                if (lenghtPermissions > 0) {
                    for (int i = 0; i < lenghtPermissions; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                            isGranted = true;
                        else {
                            isGranted = false;
                            break;
                        }
                    }
                }

                if (isGranted)
                    initFragment("inbox");
                else
                    initPermission();

            }
        }
    }

    public void initPermission() {
        int pReadContact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int pSendSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int pReceiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int pReadSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        List<String> lPermissions = new ArrayList<>();
        if (pReadContact != PackageManager.PERMISSION_GRANTED)
            lPermissions.add(Manifest.permission.READ_CONTACTS);
        if (pSendSMS != PackageManager.PERMISSION_GRANTED)
            lPermissions.add(Manifest.permission.SEND_SMS);
        if (pReceiveSMS != PackageManager.PERMISSION_GRANTED)
            lPermissions.add(Manifest.permission.RECEIVE_SMS);
        if (pReadSMS != PackageManager.PERMISSION_GRANTED)
            lPermissions.add(Manifest.permission.READ_SMS);

        if (!lPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, lPermissions.toArray(new String[lPermissions.size()]),200);
        } else {
            initFragment("inbox");
        }
    }

    public void initFragment(String name) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch (name) {
            case "inbox":
                InboxFragment inboxFragment = new InboxFragment();
                fragmentTransaction.replace(flMain.getId(), inboxFragment);
                break;
            case "sent":
                SentFragment sentFragment = new SentFragment();
                fragmentTransaction.replace(flMain.getId(), sentFragment);
                break;
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void initFragmentInbox() {
        InboxFragment fragment = new InboxFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (ft != null && !ft.isEmpty()) {
            ft.replace(flMain.getId(), fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            ft.add(flMain.getId(), fragment).commit();
        }
    }

    public void initFragmentSent() {
        SentFragment fragment = new SentFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (ft != null && !ft.isEmpty()) {
            ft.replace(flMain.getId(), fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            ft.addToBackStack(null);
            ft.add(flMain.getId(), fragment).commit();
        }
    }
}
