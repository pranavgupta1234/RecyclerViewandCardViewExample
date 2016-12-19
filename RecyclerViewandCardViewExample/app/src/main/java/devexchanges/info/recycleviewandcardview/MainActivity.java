package devexchanges.info.recycleviewandcardview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<Friend> friendArrayList;
    private FloatingActionButton fab;
    private boolean gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        friendArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyle_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setRecyclerViewData(); //adding data to array list
        adapter = new RecyclerAdapter(this, friendArrayList);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(onAddingListener());
    }

    private void setRecyclerViewData() {
        friendArrayList.add(new Friend("Phan Thanh", false, "Cashier"));
        friendArrayList.add(new Friend("Nguyen Tuan", true, "Developer"));
        friendArrayList.add(new Friend("Tran Van Minh", true, "Designer"));
        friendArrayList.add(new Friend("Pham Mai Anh", true, "architect"));
        friendArrayList.add(new Friend("Nguyen Quynh Trang", false, "Doctor"));
        friendArrayList.add(new Friend("Hoang Dinh Cuong", false, "artist"));
        friendArrayList.add(new Friend("Tran Cong Bach", true, "Student"));
        friendArrayList.add(new Friend("Vu Van Duong", false, "Teacher"));
    }

    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_add); //layout for dialog
                dialog.setTitle("Add a new friend");
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                EditText name = (EditText) dialog.findViewById(R.id.name);
                EditText job = (EditText) dialog.findViewById(R.id.job);
                Spinner spnGender = (Spinner) dialog.findViewById(R.id.gender);
                View btnAdd = dialog.findViewById(R.id.btn_ok);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                //set spinner adapter
                ArrayList<String> gendersList = new ArrayList<>();
                gendersList.add("Male");
                gendersList.add("Female");
                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line, gendersList);
                spnGender.setAdapter(spnAdapter);

                //set handling event for 2 buttons and spinner
                spnGender.setOnItemSelectedListener(onItemSelectedListener());
                btnAdd.setOnClickListener(onConfirmListener(name, job, dialog));
                btnCancel.setOnClickListener(onCancelListener(dialog));

                dialog.show();
            }
        };
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gender = true;
                } else {
                    gender = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private View.OnClickListener onConfirmListener(final EditText name, final EditText job, final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = new Friend(name.getText().toString().trim(), gender, job.getText().toString().trim());

                //adding new object to arraylist
                friendArrayList.add(friend);

                //notify data set changed in RecyclerView adapter
                adapter.notifyDataSetChanged();

                //close dialog after all
                dialog.dismiss();
            }
        };
    }

    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }
}