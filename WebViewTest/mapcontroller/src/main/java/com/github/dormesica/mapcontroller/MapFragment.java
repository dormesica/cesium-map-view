package com.github.dormesica.mapcontroller;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.dormesica.mapcontroller.event.OnMapReadyListener;

/**
 * A fragment subclass that renders {@link MapView} onto the screen.
 * <p>
 * This component can be added to an activity thought it's layout XML:
 * <pre>
 *     &#60;fragment
 *          class="com.github.dormesica.mapcontroller.MapFragment"
 *          android:layout_width="match_parent"
 *          android:layout_height="match_parent" /&#62;
 * </pre>
 * or in the activity's code:
 * <pre>
 *     FragmentManager manager = getFragmentManager();
 *     FragmentTransaction transaction = manager.beginTransaction();
 *
 *     transaction.add(R.id.containing_view, MapFragment.newInstance());
 *     transaction.commit();
 * </pre>
 * <p>
 * An activity that contains {@code MapFragment} can either implement {@link OnMapReadyListener} to be notified when
 * the map is ready to be interacted with, or it can register a mListener using the
 * {@link #setOnMapReadyListener(OnMapReadyListener)} method. Providing such a listener is the only way to gain access
 * to the {@link MapView} instance and have it change later on in the application.
 *
 * @since 1.0.0
 */
public class MapFragment extends Fragment {
    private static final String ARG_OPTIONS = "options";
    private static final String TAG = "MapFragment";

    private MapView mMapView;
    private OnMapReadyListener mListener;

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
            mListener = (OnMapReadyListener) context;
        } catch (ClassCastException e) {
            Log.w(TAG, context.toString() + " does not implement OnMapReadyListener");
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
        if (mListener != null) {
            mMapView.setOnMapReadyListener(mListener);
        }

        return layout;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Registers a callback to be invoked when the map is ready and can be interacted with.
     *
     * @param listener The callback that will run.
     */
    public void setOnMapReadyListener(@NonNull OnMapReadyListener listener) {
        mListener = listener;
        mMapView.setOnMapReadyListener(mListener);
    }
}
