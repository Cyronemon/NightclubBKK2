package com.example.cyronemon.nightclubbkk2;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListActivity extends AppCompatActivity {

    private DatabaseReference nightclubDatabase;


//    String[] itemname = {
//            "Safari",
//            "Camera",
//            "Global",
//            "FireFox",
//            "UC Browser",
//            "Android Folder",
//            "VLC Player",
//            "Cold War"
//    };
//
//    Integer[] imgid = {
//            R.drawable.pic1,
//            R.drawable.pic2,
//            R.drawable.pic3,
//            R.drawable.pic4,
//            R.drawable.pic5,
//            R.drawable.pic6,
//            R.drawable.pic7,
//            R.drawable.pic7
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        nightclubDatabase = FirebaseDatabase.getInstance().getReference("nightclub");
        // instance from firebase --> parameter of database

//        ValueEventListener listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("Hello", dataSnapshot.toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };

        ListView clubView = (ListView) findViewById(R.id.list);

//        FirebaseListAdapter<Nightclub> adapter = new FirebaseDatabase<Nightclub>(this, Nightclub.class, R.layout.mylist, nightclubDatabase) {
//
//        }
//Setting up FirebaseListAdapter
        FirebaseListAdapter<Nightclub> adapter = new FirebaseListAdapter<Nightclub>(this, Nightclub.class, R.layout.mylist, nightclubDatabase) {
            @Override
            protected void populateView(View v, Nightclub model, int position) {
                // Choosing which elements will be used from mylist.xml
                TextView nameText = (TextView) v.findViewById(R.id.item);
                TextView nameText1 = (TextView) v.findViewById(R.id.textView1);
                ImageView viewImage = (ImageView) v.findViewById(R.id.icon);

                nameText.setText(model.getName());
                nameText1.setText(model.getDescriptionShort());
                //Finding resources ID by name in drawable
                int resourceID = getResources().getIdentifier(model.getImage(), "drawable", getPackageName()); // THIS IS WHERE WE GET IMAGES
                viewImage.setImageResource(resourceID);
                //icon, item, textview1
            }

        };
        clubView.setAdapter(adapter);

        clubView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListActivity.this, MapActivity.class);
                intent.putExtra("clubID", position);
                startActivity(intent);

            }
        });

//        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
//        ListView list=(ListView)findViewById(R.id.list);
//        list.setAdapter(adapter);

//        this.setListAdapter(new ArrayAdapter<String>(
//                this, R.layout.mylist,
//                R.id.Itemname,itemname));


    }
}