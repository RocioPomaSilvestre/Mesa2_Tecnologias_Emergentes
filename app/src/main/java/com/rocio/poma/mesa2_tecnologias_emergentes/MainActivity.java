package com.rocio.poma.mesa2_tecnologias_emergentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NotificationBadge notificationBadgeN, notificationBadgeM ;
    Button btn, btnNotificar, btnMensajes;
    int countN=0, countM=0, count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseDatabase db=FirebaseDatabase.getInstance();
        //Capturamos el nodo mensajes de la base de Datos
        DatabaseReference dbRefM=db.getReference("mensajes");
        DatabaseReference dbRef=db.getReference("notificaciones");

        notificationBadgeM=findViewById(R.id.badge);
        List<Mensajes> mensajesList=new ArrayList<>();
        ArrayAdapter<Mensajes> adapterM= new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,mensajesList);

        dbRefM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapterM.clear();
                countM=0;
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Mensajes e =snap.getValue(Mensajes.class);
                    countM++;
                    adapterM.add(e);
                }
                Toast.makeText(getApplicationContext(),"Se actualizo Mensajes: "+countM,Toast.LENGTH_SHORT).show();
                notificationBadgeM.setNumber(countM);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        notificationBadgeN=findViewById(R.id.badge2);
        List<Notificaciones> notificList=new ArrayList<>();
        ArrayAdapter<Notificaciones> adapter= new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,notificList);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear();
                countN=0;
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Notificaciones e =snap.getValue(Notificaciones.class);
                    countN++;
                    adapter.add(e);
                }
                Toast.makeText(getApplicationContext(),"Se actualizo Notificaciones: "+countN,Toast.LENGTH_SHORT).show();
                notificationBadgeN.setNumber(countN);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pm=new PopupMenu(MainActivity.this,btn);
                pm.getMenuInflater().inflate(R.menu.panel_emergente,pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
                pm.show();
            }
        });

        /*
        notificationBadgeN=findViewById(R.id.badge2);
        btnNotificar=findViewById(R.id.btnNotificar);

        btnNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationBadgeN.setNumber(countN++);
            }
        });

        notificationBadgeM=findViewById(R.id.badge);
        btnMensajes=findViewById(R.id.btnMensajes);

        btnMensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationBadgeM.setNumber(countM++);
            }
        });
         */



        ////////////////////////////////////////////////////////////////////////////////////////////
        Fragment fragmentInicio, fragmentBuscar,fragmentBiblioteca;
        fragmentInicio=new FragmentMensajes();
        fragmentBuscar=new FragmentBuscar();
        fragmentBiblioteca=new FragmentNotificaciones();

        BottomNavigationView.OnNavigationItemSelectedListener listener= new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem itemSel) {

                if (itemSel.getItemId()==R.id.itemMensaje){
                    //mostrar fragment inicio
                    FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contenedorPrincipal,fragmentInicio);
                    transaction.commit();
                    return true;
                }else if (itemSel.getItemId()==R.id.itemBuscar){
                    //mostrar fragment Buscar
                    FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contenedorPrincipal,fragmentBuscar);
                    transaction.commit();
                    return true;
                }else if (itemSel.getItemId()==R.id.itemNotificacion){
                    //Button itemN=findViewById(R.id.itemNotificacion);
                    //mostrar fragment

                    return false;
                }
                return false;
            }
        };

        BottomNavigationView navigationView=findViewById(R.id.navBotton);
        navigationView.setOnNavigationItemSelectedListener(listener);
    }
}