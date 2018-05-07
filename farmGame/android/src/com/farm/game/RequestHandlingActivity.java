package com.farm.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.farm.game.DataClasses.User;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestHandlingActivity extends FragmentActivity {
    private HTTPConnection httpConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.httpConnection = new HTTPConnection(this);

        Intent intent = getIntent();
        String action = intent.getStringExtra("action");

        switch (action) {
            case "login":
                String username = intent.getStringExtra("username");
                String password = intent.getStringExtra("password");
                login(username, password);
                break;
            case "saveState":
                saveState();
                break;
            case "loadState":
                loadState();
                break;
        }
        this.finish();
    }

    private void login(String username, String password) {
        this.httpConnection.login(username, password, new Callback(){
            @Override
            public void taskCompleted(int status_code, JSONObject json) {
                System.out.println(status_code);
                switch(status_code){
                    case 0:
                        Toast.makeText(getApplicationContext(), "Fout bij het maken van verbinding met de server.", Toast.LENGTH_SHORT).show();
                        break;
                    case 401: //Unauthorized
                        Toast.makeText(getApplicationContext(), "Onjuiste gebruikersnaam of wachtwoord.", Toast.LENGTH_SHORT).show();
                        break;
                    case 200:
                        System.out.println("login successfull");
                        User user = new User();
                        try{
                            String token = json.getString("jwt");
                            JWT jwt = new JWT(token);

                            Claim claim = jwt.getClaim("id");
                            user.setId(claim.asString());
                            claim = jwt.getClaim("firstName");
                            user.setFirstName(claim.asString());
                            claim = jwt.getClaim("lastName");
                            user.setLastName(claim.asString());
                            claim = jwt.getClaim("email");
                            user.setEmail(claim.asString());

                            FarmGameMain.settings.setUser(user, token);
                            FarmGameMain.settings.load();
                            httpConnection.setTokenHeader(token);

                            FarmGameMain.justLoggedIn();
                        }catch(Exception ex){
                            //Fout bij decoderen van user jwt token
                            System.out.print(ex.getMessage());
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Onbekende fout.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveState() {
        httpConnection.saveState(new Callback(){
            @Override
            public void taskCompleted(int status_code, JSONObject json) {
                switch(status_code){
                    case 0:
                        System.out.println("Connection to the server refused");
                        break;
                    case 401: //Unauthorized
                        System.out.println("Unauthorized");
                        break;
                    case 200:
                        System.out.println("Successfully saved state");
                        break;
                    default:
                        System.out.println("An error occurred");
                }
            }
        });
    }

    private void loadState() {
        httpConnection.loadState(new Callback(){
            @Override
            public void taskCompleted(int status_code, JSONObject json) {
                switch(status_code){
                    case 0:
                        System.out.println("Connection to the server refused");
                        break;
                    case 401: //Unauthorized
                        System.out.println("Unauthorized");
                        break;
                    case 200:
                        System.out.println("Successfully loaded data");
                        try{
                            Json js = new Json();
                            JSONObject result = json.getJSONObject("result");
                            JSONArray saveGame = result.getJSONArray("savegame");

                            // Load Landscape
                            String jsonFarmLandscapeString = saveGame.getString(0);
                            if(jsonFarmLandscapeString  == null || jsonFarmLandscapeString .equals("") || jsonFarmLandscapeString .equals("null")) {
                                FarmGameMain.landscape.defaultGrid();
                            } else {
                                Grid grid = js.fromJson(Grid.class, jsonFarmLandscapeString );
                                FarmGameMain.landscape.setGrid(grid);
                            }

                            // Load inventory
                            String jsonInventoryString = saveGame.getString(1);
                            if(jsonInventoryString  == null || jsonInventoryString .equals("") || jsonInventoryString .equals("null")) {
                                FarmGameMain.inventory.defaultInventory();
                            } else {
                                FarmGameMain.inventory = js.fromJson(Inventory.class, jsonInventoryString );
                            }

                            // Load savedResources
                            Preferences prefs = Gdx.app.getPreferences("My Preferences");
                            prefs.putString("savedResources", saveGame.getString(2));
                        }catch(Exception ex){
                            System.out.print(ex.getMessage());
                        }
                        break;
                    default:
                        System.out.println("An error occurred");
                }
            }
        });
    }
}
