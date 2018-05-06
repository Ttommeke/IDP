package com.farm.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectResourceDialog extends AppCompatActivity {

    private boolean canCollect = false;
    private ResourceType resourceType;
    private Button btnCollect;
    private TextView txtInfo;
    private ImageView imgResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_collect_resource);

        Intent intent = getIntent();
        canCollect = intent.getBooleanExtra("canCollect", false);
        resourceType = ResourceType.getType(intent.getIntExtra("resourceType", 0));

        this.loadElements();

        if(!canCollect){
            this.txtInfo.setText("Je moet dichter zijn om dit te verzamelen!");
            this.btnCollect.setEnabled(false);
            this.btnCollect.setBackgroundColor(Color.GRAY);
        } else {
            this.btnCollect.setEnabled(true);
            this.btnCollect.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            this.txtInfo.setText("Verzamel");
        }
    }

    private void loadElements(){
        this.btnCollect = (Button)this.findViewById(R.id.btnCollect);
        this.txtInfo = (TextView)this.findViewById(R.id.txtInfo);
        this.imgResource = (ImageView)this.findViewById(R.id.imgResource);
        imgResource.setBackgroundDrawable(ContextCompat.getDrawable(this, getResources().getIdentifier(resourceType.getIcon(),"drawable",CollectResourceDialog.this.getPackageName())));
    }

    public void btnCollectOnClick(View view){
        switch(resourceType){
            case GRAIN:
                FarmGameMain.inventory.addGrain(1);
                break;
            case CARROT:
                FarmGameMain.inventory.addCarrot(1);
                break;
            case POTATO:
                FarmGameMain.inventory.addPotato(1);
                break;
            case STRAWBERRY:
                FarmGameMain.inventory.addStrawberry(1);
                break;
            case EGGPLANT:
                FarmGameMain.inventory.addEggplant(1);
                break;
            case APPLE:
                FarmGameMain.inventory.addApples(1);
                break;
            case RASPBERRY:
                FarmGameMain.inventory.addRaspberries(1);
                break;
            case FERTILIZER:
                FarmGameMain.inventory.findFertilizer();
                break;
            case SUPERGRAIN:
                FarmGameMain.inventory.findSuperGrain();
                break;
            case MILK:
                FarmGameMain.inventory.addMilk(1);
                break;
            case EGG:
                FarmGameMain.inventory.addEgg(1);
                break;
        }
        setResult(Activity.RESULT_OK);
        finish();
    }
}
