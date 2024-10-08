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
    Spinner spinner, spinner_young ;
    String DocName,youngNumber, videoId, url ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        spinner_young = view.findViewById(R.id.spinner_young);
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

        // spinner_young ga tegishli
        String[] spinner_young_list = {"1", "2", "3", "4", "5"};

        ArrayAdapter<String> adapter_young = new ArrayAdapter<>(getActivity(),  android.R.layout.simple_spinner_item , spinner_young_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_young.setAdapter(adapter_young);
        Log.d("demo46", "activityllist "  + activityllist);
        spinner_young.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Танланган элементни олиш
                String selectedOption = parentView.getItemAtPosition(position).toString();
                youngNumber = selectedOption;
                Toast.makeText(getActivity(), "Tanlangan: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Хеч нарса танланмаса (ўрнатилган ҳолат)
            }
        });




    }


    public void AddButton() {
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Киритилаётган Видео линки
                url = edit_short_id.getText().toString();

                // Линкни аниқлаш олиш учун
                String url_app_longV = "https://youtu.be/";
                String url_app_shorts = "https://youtube.com/shorts/";
                String url_pk_longV = "https://www.youtube.com/watch?v=";
                String url_pk_shorts = "https://www.youtube.com/shorts/";

                // Линкни филтирлаш учун
                if (url.startsWith(url_app_longV)) {
                    videoId = url.substring(url.indexOf("/youtu.be/") + 10, url.indexOf("?"));
                } else if (url.startsWith(url_app_shorts)) {
                    videoId = url.substring(url.indexOf("/shorts/") + 8, url.indexOf("?"));
                } else if (url.startsWith(url_pk_longV)) {

                    videoId = url.substring(url.indexOf("v=") + 2, url.indexOf("&") == -1 ? url.length() : url.indexOf("&"));
                    if (url.length() == url.indexOf("v=") + 2 + videoId.length()) {
                        // IDдан кейин бошқа белгилар йўқ
                        videoId = url.substring(url.indexOf("v=") + 2);
                    } else {
                        // IDдан кейин белгилар мавжуд
                        videoId = url.substring(url.indexOf("v=") + 2, url.indexOf("&"));
                    }
                } else if (url.startsWith(url_pk_shorts)) {
                    videoId = url.substring(url.lastIndexOf("/") + 1);
                } else {
                    Toast.makeText(requireActivity(), "Фақат YouTube линкини киритиш зарур", Toast.LENGTH_SHORT).show();
                    return; // Функцияни тугатади
                }

                // Map яратиш
                nestedData.put("url", edit_short_id.getText().toString());
                nestedData.put("id",   videoId);
                nestedData.put("youngNumber",  youngNumber);

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