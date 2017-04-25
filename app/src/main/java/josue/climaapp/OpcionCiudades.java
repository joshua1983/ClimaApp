package josue.climaapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import josue.climaapp.adaptadores.ListaCiudadesAdapter;
import josue.climaapp.modelos.Ciudad;

/**
 * Created by josue on 4/25/17.
 */

public class OpcionCiudades extends Fragment {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    ArrayList<Ciudad> datos = new ArrayList<Ciudad>();

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    RecyclerView recView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;

    public OpcionCiudades(){

    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        cargarCiudades();
    }

    public void cargarCiudades(){
        for (int i=0; i<10; i++){
            datos.add(new Ciudad("ciudad "+i, "temperatura "+i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View componentes = inflater.inflate(R.layout.fragment_ciudades, container, false);
        recView = (RecyclerView) componentes.findViewById(R.id.recycler_lista_ciudades);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        /*
        Cambiar cuando sea landscape a grid layout
         */

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        recView.setHasFixedSize(true);

        final ListaCiudadesAdapter adaptador_ciudades = new ListaCiudadesAdapter(datos);
        recView.setAdapter(adaptador_ciudades);
        recView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

        return componentes;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (recView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recView.setLayoutManager(mLayoutManager);
        recView.scrollToPosition(scrollPosition);

    }

}
