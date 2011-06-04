package com.spacetrader.android.starter;

import android.app.Activity;
import android.os.Bundle;

import com.spacetrader.service.shield.ShieldException;
import com.spacetrader.service.ship.Hawk;
import com.spacetrader.service.ship.ProbabilityOutOfBoundsException;
import com.spacetrader.service.ship.exception.NoWeaponsException;

public class AndroidTrading extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hawk ship = new Hawk();
        try {
			ship.fireWeapons(ship);
		} catch (NoWeaponsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProbabilityOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
        setContentView(R.layout.main);

//		TextView tv = (TextView) findViewById(R.id.my_textView);
		
//		tv.setText("this is set somewhere else");    
    }
}