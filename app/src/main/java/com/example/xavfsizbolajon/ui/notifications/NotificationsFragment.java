package com.example.xavfsizbolajon.ui.notifications;

import android.os.Bundle;
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

import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.databinding.FragmentNotificationsBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        spinner = view.findViewById(R.id.spinner);
        edit_short_id = view.findViewById(R.id.edit_short_id);
        add_button = view.findViewById(R.id.add_button);

        AddButton();
        spinner();


        return view;
    }

    public void spinner() {

        String sss = "ssss";
        // Массивни адаптерга юклаш
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.options_array, android.R.layout.simple_spinner_item);

// Тушириладиган рўйхатнинг услубини белгилаш
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Spinner'га адаптерни ўрнатиш
        spinner.setAdapter(adapter);

// Танланган элементни кузатиш
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Танланган элементни олиш
                String selectedOption = parentView.getItemAtPosition(position).toString();
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
                // видео IDсини олиш
                String url = edit_short_id.getText().toString();
                String videoId = url.substring(url.indexOf("/shorts/") + 8, url.indexOf("?"));

                // Map яратиш
                nestedData.put("url", edit_short_id.getText().toString());
                nestedData.put("id",   videoId);

                // arrey орқали сақлаш
                DocumentReference Data = db.document("Shorts/boshqalar");
                Data.update("key", FieldValue.arrayUnion(nestedData));
                edit_short_id.setText("");

                Toast.makeText(requireActivity(), "Видео сақланди", Toast.LENGTH_SHORT).show();
            }
        });



    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}