package com.example.xavfsizbolajon.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.databinding.FragmentNotificationsBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String,Object> nestedData = new HashMap<>();
    public List<String> activityllist = new ArrayList<>();
    EditText edit_short_id;
    Button add_button;
    Spinner spinner;
    String DocName, videoId, url ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        spinner = view.findViewById(R.id.spinner);
        edit_short_id = view.findViewById(R.id.edit_short_id);
        add_button = view.findViewById(R.id.add_button);

        Collection();
        AddButton();




        return view;
    }

    private void Collection() {
        db.collection("Shorts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("demo28", "Error : " + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Log.d("demo46", "users doc "  + doc.getDocument().getId());
                        activityllist.add(doc.getDocument().getId());

                    }

                }
                Spinner();
            }});
    }


    public void Spinner() {
        // Массивни адаптерга юклаш
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),  android.R.layout.simple_spinner_item , activityllist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.d("demo46", "activityllist "  + activityllist);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Танланган элементни олиш
                String selectedOption = parentView.getItemAtPosition(position).toString();
                DocName = selectedOption;
                Toast.makeText(getActivity(), "Tanlangan: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Хеч нарса танланмаса (ўрнатилган ҳолат)
            }
        });
    }

    public void UrlLink() {
        // видео IDсини олиш
        url = edit_short_id.getText().toString();
        String url_app_longV= url.substring(0, url.indexOf("/youtu.be/") + 10);
        String url_app_shorts = url.substring(0, url.indexOf("/shorts/") + 8);
        String url_pk_longV = url.substring(0, url.indexOf("v=") + 2);
        String url_pk_shorts = url.substring(0, url.indexOf("/shorts/") + 8);

        if (url_app_longV.equals("https://youtu.be/")) {
             videoId = url.substring(url.indexOf("/youtu.be/") + 10, url.indexOf("?"));
        } else if (url_app_shorts.equals("https://youtube.com/shorts/")) {
             videoId = url.substring(url.indexOf("/shorts/") + 8, url.indexOf("?"));
        } else if (url_pk_longV.equals("https://www.youtube.com/watch?v=")) {
             videoId = url.substring(url.indexOf("v=") + 2);
        } else if (url_pk_shorts.equals("https://www.youtube.com/shorts/")) {
             videoId = url.substring(url.lastIndexOf("/") + 1);
        } else {
            Toast.makeText(requireActivity(), "Нотаниш линк киритилаяпти", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddButton() {
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Видео линкларини саралаш
                UrlLink();

                // Map яратиш
                nestedData.put("url", edit_short_id.getText().toString());
                nestedData.put("id",   videoId);

                // arrey орқали сақлаш
                DocumentReference Data = db.collection("Shorts").document(DocName);
                Data.update("key", FieldValue.arrayUnion(nestedData));
                edit_short_id.setText("");

                Toast.makeText(requireActivity(), "Видео сақланди " + DocName, Toast.LENGTH_SHORT).show();
            }
        });



    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}