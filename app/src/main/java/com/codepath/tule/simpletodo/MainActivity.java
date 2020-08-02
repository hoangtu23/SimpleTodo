package com.codepath.tule.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String BLANK = "";

    List<String> items;
    AppCompatButton btnAdd;
    AppCompatEditText edtItem;
    RecyclerView rvItem;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initModels();
        initLayout();
        initData();
    }

    private void initLayout() {
        btnAdd = findViewById(R.id.btnAdd);
        edtItem = findViewById(R.id.edtTodoName);
        rvItem = findViewById(R.id.rvItem);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void initData() {
        loadItems();
        itemAdapter = new ItemAdapter(items, new ItemAdapter.OnLongClickLister() {
            @Override
            public void onItemLongClicked(int pos) {
                if (items != null && items.size() > pos) {
                    items.remove(pos);
                    itemAdapter.notifyItemRemoved(pos);
                    Toast.makeText(getApplicationContext(), "Item removed.", Toast.LENGTH_SHORT).show();
                    saveItems();
                }
            }
        });
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initModels() {
        items = new ArrayList<>();
        items.add("Buy Milk");
        items.add("Go to gym");
        items.add("Have fun!");
    }

    private void addItem() {
        String todoItem = edtItem.getText().toString();
        // add item to model
        items.add(todoItem);

        // notify adapter
        itemAdapter.notifyItemInserted(items.size() - 1);
//        itemAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), "Item added.", Toast.LENGTH_SHORT).show();

        edtItem.setText(BLANK);

        saveItems();
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    /**
     * read file to load items
     */
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    /**
     * save items
     */
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
        }
    }
}
