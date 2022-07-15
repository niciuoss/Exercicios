package com.example.avaliaofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.avaliaofinal.Modelo.Itens;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText edt_Nome, edt_Cor, edt_Quantidade;
    ListView list_Itens;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Itens> arrayList = new ArrayList<>();
    ArrayAdapter<Itens> arrayAdapter;
    Itens itemSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_Nome = findViewById(R.id.edtNome);
        edt_Cor = findViewById(R.id.edtCor);
        edt_Quantidade = findViewById(R.id.edtQuantidade);
        list_Itens = findViewById(R.id.listViewItens);

        inicializarFirebase();
        eventDatabase();

        list_Itens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                itemSelecionado = (Itens)adapterView.getItemAtPosition(position);
                edt_Nome.setText(itemSelecionado.getNome());
                edt_Cor.setText(itemSelecionado.getCor());
                edt_Quantidade.setText(itemSelecionado.getQuantidade());
            }
        });
    }

    private void eventDatabase() {
        databaseReference.child("Itens").addValueEventListener(new ValueEventListener() {
            //busca do firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot objetoSnapshot:snapshot.getChildren()){
                    Itens itens = objetoSnapshot.getValue(Itens.class);
                    arrayList.add(itens);
                }
                arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                list_Itens.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(MainActivity.this, "" + item.getItemId(), Toast.LENGTH_SHORT).show();
        int id = item.getItemId();
            if(id == R.id.add){
                Itens itens = new Itens();
                itens.setUid(UUID.randomUUID().toString());
                itens.setNome(edt_Nome.getText().toString());
                itens.setCor(edt_Cor.getText().toString());
                itens.setQuantidade(edt_Quantidade.getText().toString());
                databaseReference.child("Itens").child(itens.getUid()).setValue(itens);
                limparCampos();
            }else if(id == R.id.update){
                Itens itens = new Itens();
                itens.setUid(itemSelecionado.getUid());
                itens.setNome(edt_Nome.getText().toString().trim());
                itens.setCor(edt_Cor.getText().toString().trim());
                itens.setQuantidade(edt_Quantidade.getText().toString().trim());
                databaseReference.child("Itens").child(itens.getUid()).setValue(itens);
                limparCampos();
            }else if(id == R.id.remove) {
                Itens itens = new Itens();
                itens.setUid(itemSelecionado.getUid());
                databaseReference.child("Itens").child(itens.getUid()).removeValue();
                limparCampos();
        }
        return true;
    }

    private void limparCampos() {
        edt_Nome.setText("");
        edt_Cor.setText("");
        edt_Quantidade.setText("");
    }
}