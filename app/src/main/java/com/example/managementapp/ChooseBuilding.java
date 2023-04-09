package com.example.managementapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.widget.ArrayAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ChooseBuilding extends AppCompatActivity {
    private ListView lvBuildings;
    //List<String> buildings = new ArrayList<String>();
    private static final String SERVER_URL = "http://192.168.1.108:5000/users/get_buildings_by_user?email=%s";
    List<String> buildings = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.trying);
        lvBuildings = findViewById(R.id.my_listview);
        String my_email = getIntent().getExtras().get("email").toString();
        try {
            try_to_get_building(my_email);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        lvBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);
                Intent intent = new Intent(ChooseBuilding.this, MainMenu.class);
                intent.putExtra("building", selectedItem);
                startActivity(intent);
            }
        });

        FloatingActionButton openDialog = findViewById(R.id.addBuildingBtn);
        openDialog.setOnClickListener(v -> {
            showCustomDialog();
        });

//        ListView lvContacts = findViewById(R.id.ContactList);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
//        lvContacts.setAdapter(adapter);
//
//
//        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
//            Contact chat = contacts.remove(i);
//            contactDao.delete(chat);
//            adapter.notifyDataSetChanged();
//            return true;
//        });
//
//        lvContacts.setOnItemClickListener((adapterView, view, i, l) -> {
//            Intent intent = new Intent(this, ChatScreen.class);
//            intent.putExtra("contact_id", contacts.get(i).getId());
//            intent.putExtra("connectedUsername", UsernameID);
//            startActivity(intent);
//        });

    }
    public void try_to_get_building(String email) throws MalformedURLException {
        URL url = new URL(String.format(SERVER_URL, email));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<String>>(){}.getType();
                    List<String> buildingList = gson.fromJson(jsonResponse, listType);
                    ArrayList<String> list = new ArrayList<>(buildingList);
                    buildings = list;
                    adapter = new ArrayAdapter<>(ChooseBuilding.this, android.R.layout.simple_list_item_1);
                    lvBuildings.setAdapter(adapter);
                    adapter.addAll(buildings);

                } else {
                    // Handle unsuccessful response
                    Toast.makeText(ChooseBuilding.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
    }

    public static Map<String, String> jsonToMap (String json) {
        Gson gson = new Gson();
        Type type = Map.class.getTypeParameters()[1];
        Map<String, String> map = gson.fromJson(json, type);

        return map;
    }

    void showCustomDialog(){
        final Dialog dialog = new Dialog(ChooseBuilding.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_add_building);

        final EditText contact_username = dialog.findViewById(R.id.contact_username);
        //final EditText contact_name = dialog.findViewById(R.id.contact_nickname);
        //final EditText contact_server = dialog.findViewById(R.id.contact_server);
        Button add_contact_submitBtn = dialog.findViewById(R.id.add_contact_submitBtn);
        add_contact_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = contact_username.getText().toString();
                //String nickname = contact_name.getText().toString();
                //String server = contact_server.getText().toString();
                if(username.isEmpty() ){
                    Toast.makeText(ChooseBuilding.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    System.out.print("Hello");
                }

                }
        });
        dialog.show();
    }
    protected void onResume() {
        super.onResume();
        buildings.clear();
        //buildings.addAll(contactDao.index());
        //adapter.notifyDataSetChanged();
    }
    public void get_buildings(ArrayList<Building> buildings) {
        for (Building building:buildings) {

        }
//        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
//        Call<List<Contact>> call = webServiceAPI.getcontacts(username);
//        call.enqueue(new Callback<List<Contact>>() {
//            @Override
//            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
//                List<Contact> contacts = response.body();
//                AppDB.clearRoomDB();
//                for (Contact contact:contacts) {
//                    contactDao.insert(contact);
//                    Call<List<Message>> call2 = webServiceAPI.getmessages(contact.getId(), username);
//                    call2.enqueue(new Callback<List<Message>>() {
//                        @Override
//                        public void onResponse(Call<List<Message>> call2, Response<List<Message>> response2) {
//                            List<Message> messages = response2.body();
//                            for (Message message:messages) {
//                                message.setContactID(contact.getId());
//                                messageDao.insert(message);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Message>> call2, Throwable t) {
//                            Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                onResume();
//            }
//
//            @Override
//            public void onFailure(Call<List<Contact>> call, Throwable t) {
//                Toast.makeText(ContactList.this, "Failed to contact with the server", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
