package com.EDA397.Navigator.Navigator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.example.test.R;

import java.io.IOException;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    private SharedPreferences current;
    private SharedPreferences.Editor currEdit;
    private GitHubClient client;
    RepositoryService service;
    Set<String> repoSet;
    ListView r_listview;
    String[] repos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current = getSharedPreferences("CurrentAccount", MODE_PRIVATE);
        currEdit = current.edit();
        client = new GitHubClient();
        client.setCredentials(current.getString("name", ""), current.getString("pw", ""));
        service = new RepositoryService();

        try {
            for (Repository repo : service.getRepositories(client.getUser()))
                repoSet.add(repo.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        r_listview = (ListView) findViewById(R.id.repo_list);
        repoSet.toArray(repos);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, repos);

        r_listview.setAdapter(adapter);
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
        if (id == R.id.action_logout) {
            currEdit.clear();
            currEdit.commit();
            startActivity(new Intent("com.EDA397.Navigator.Navigator.LoginActivity"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        currEdit.clear();
        currEdit.commit();
        super.onBackPressed();
    }
}
