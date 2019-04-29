package com.github.dormesica.mapcontroller;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment subclass that renders {@link MapView} onto the screen.
 * <p>
 * An activity that contains {@code MapFragment} must implement {@link OnMapReadyListener} to be notified when the map
 * id ready to be interacted with.
 *
 * @since 1.0.0
 */
public class MapFragment extends Fragment {
    private static final String ARG_OPTIONS = "options";

    private MapView mMapView;
    private OnMapReadyListener listener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new {@code MapFragment} with default options.
     *
     * @return A new MapFragment instance.
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    /**
     * Creates a new {@code MapFragment} with the given options.
     *
     * @param options options
     * @return A new MapFragment instance.
     */
    public static MapFragment newInstance(MapOptions options) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_OPTIONS, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnMapReadyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnMapReadyListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = layout.findViewById(R.id.map_fragment_map_view);
        mMapView.setOnMapReadyListener(listener::onMapReady);

        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities the contain the {@link MapFragment} to allow interaction with
     * the {@link MapView} this fragment contains and to allow interaction to be communicated to the activity and other
     * fragments contained in the activity.
     */
    public interface OnMapReadyListener {
        /**
         * Called when the map view has been initialized and is ready to be interacted with.
         *
         * @param map The map view
         */
        void onMapReady(@NonNull MapView map);
    }
}
