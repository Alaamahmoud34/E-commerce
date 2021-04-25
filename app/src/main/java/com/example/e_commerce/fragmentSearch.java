package com.example.e_commerce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Locale;

public class fragmentSearch extends Fragment {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static final int REQUEST_CODE_CAMERA_INPUT = 49374;
    View view;
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    SearchView searchView;
    ImageButton mic,pic;
    DB_Helper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_search,container,false);

        searchView = view.findViewById(R.id.searchView);
        listView = view.findViewById(R.id.myList);
        mic=view.findViewById(R.id.micBtn);
        pic=view.findViewById(R.id.picBtn);

        arrayAdapter=new ArrayAdapter<>(this.getContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        db=new DB_Helper(this.getContext());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.clear();
                Cursor cursor=db.search(newText);
                while (!cursor.isAfterLast()) {
                    arrayAdapter.add(cursor.getString(1));
                    cursor.moveToNext();
                }
                return true;
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speake();
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                fragmentProducts products=new fragmentProducts();
                TextView txt=(TextView)v;
                Bundle bundle=new Bundle();
                bundle.putString("ProdName",txt.getText().toString());
                products.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,products).addToBackStack(null).commit();
            }
        });

        return view;
    }

    protected void scanCode() {
        IntentIntegrator integrator=new IntentIntegrator(this.getActivity());
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning code");
        integrator.forSupportFragment(this).initiateScan();
    }

    private void speake() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"What do you fragmentSearch for?");

        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception ex)
        {
            Toast.makeText(this.getContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == -1 && null != data) {
                    ArrayList<String> prod = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchView.setQuery(prod.get(0), true);
                }
                break;
            }
            case REQUEST_CODE_CAMERA_INPUT:
            {
                IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
                try {
                      if(result.getContents()!=null){
                          searchView.setQuery(result.getContents(),true);
                      }
                      else
                          Toast.makeText(this.getContext(),"No data",Toast.LENGTH_LONG);
                }catch (Exception ex) {
                    Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }
    }
}
