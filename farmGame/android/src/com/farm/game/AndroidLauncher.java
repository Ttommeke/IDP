package com.farm.game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Json;
import com.farm.game.DataClasses.User;
import com.farm.game.states.FarmState;
import com.farm.game.states.GameStateManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class AndroidLauncher extends AndroidApplication implements AndroidEnvironmentCallback {
    private HTTPConnection httpConnection;

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FarmGameMain(this), config);
	}

    @Override
    public void startMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        String json = prefs.getString("savedResources");
        intent.putExtra("savedResources", json);
        this.startActivity(intent);
    }

    @Override
    public void login(String username, String password, final GameStateManager gsm) {
        httpConnection = new HTTPConnection(getContext());
        httpConnection.login(username, password, new Callback(){
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

                            gsm.set(new FarmState(gsm));
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

    @Override
    public void setToken(String token) {
        httpConnection.setTokenHeader(token);
    }

    @Override
    public void saveState() {
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

    @Override
    public void loadState() {
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
                        System.out.println("Successfully loaded state");
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
