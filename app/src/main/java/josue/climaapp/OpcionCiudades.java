package josue.climaapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by josue on 4/25/17.
 */

public class OpcionCiudades extends Fragment {
    public OpcionCiudades(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_ciudades, container, false);
    }
}
