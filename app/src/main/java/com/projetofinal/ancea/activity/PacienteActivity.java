package com.projetofinal.ancea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.adapter.OcorrenciasAdapter;
import com.projetofinal.ancea.data.model.Ocorrencias;
import com.projetofinal.ancea.data.model.Usuario;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;
import com.projetofinal.ancea.helper.RecyclerItemClickListener;
import com.projetofinal.ancea.helper.UsuarioFirebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PacienteActivity extends AppCompatActivity {

    private Usuario medico;
    private Usuario paciente;
    private DatabaseReference databaseReference;
    private FirebaseAuth autenticacao;
    private RecyclerView recyclerView;
    private List<Ocorrencias> ocorrencias = new ArrayList<>();
    private OcorrenciasAdapter oc;



    private TextView textBusca;
    private ProgressBar busca;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        paciente = UsuarioFirebase.getDadosUsuarioLogado();
        paciente.setOcorrencias(ocorrencias);
        recyclerView = findViewById(R.id.recyclerOcorrencias);
        oc = new OcorrenciasAdapter(ocorrencias,getApplicationContext(),paciente);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(oc);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        textBusca = findViewById(R.id.textBusca);
        busca = findViewById(R.id.progressOcorrencias);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sair){
            autenticacao.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public void novaOcorrencia(View v){
        startActivity(new Intent(this, OcorrenciaActivity.class));
    }

    public void salvarOcorrencia(View v){
        FirebaseUser user = UsuarioFirebase.getUsuarioAtual();
        if (user != null){
            Ocorrencias ocs = new Ocorrencias();
            DatabaseReference db = ConfiguracaoFirebase.getFirebaseDatabase();
            DatabaseReference oco = db.child("ocorrencias").child(user.getUid());
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss", Locale.ROOT);
            String dateTime = simpleDateFormat.format(calendar.getTime());
            ocs.setData(dateTime);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("data", ocs.getData());
            map.put("tipoCrise",ocs.getTipoCrise());
            map.put("tempoSono",ocs.getTempoSono());
            map.put("estadoEmocional",ocs.getEstadoEmocional());
            map.put("exposicaoDispositivos", ocs.getExposicaoDispositivos());
            map.put("observacoesExtras", ocs.getObservacoesExtras());
            oco.push().setValue(map);
            ocorrencias.add(ocs);
        }
    }

    public void cancelar(View v){
        setContentView(R.layout.activity_paciente);
    }


    public void cliqueOcorrencia(View v){
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Ocorrencias oco = ocorrencias.get(position);
                        TextView ocorrencia = findViewById(R.id.textData);
                        TextView crise = findViewById(R.id.textCrise);
                        TextView sono = findViewById(R.id.textSono);
                        TextView estadoEmocional = findViewById(R.id.textEmocional);
                        TextView exposicao = findViewById(R.id.textEletronicos);
                        TextView observacoes = findViewById(R.id.textObservacoes);
                        ocorrencia.setText(oco.getData());
                        crise.setText(oco.getTipoCrise());
                        sono.setText(oco.getTempoSono());
                        estadoEmocional.setText(oco.getEstadoEmocional());
                        exposicao.setText(oco.getExposicaoDispositivos());
                        observacoes.setText(oco.getObservacoesExtras());
                        setContentView(R.layout.description_ocorrencia);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));
    }

    public void listarOcorrencias(){
        DatabaseReference ocor = databaseReference.child("ocorrencias").child("uid");
        FirebaseUser user = UsuarioFirebase.getUsuarioAtual();
        Query pesquisa = ocor.orderByChild(user.getUid());
        pesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0){
                    textBusca.setVisibility(View.GONE);
                    busca.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    textBusca.setVisibility(View.VISIBLE);
                    busca.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                ocorrencias.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Ocorrencias o = ds.getValue(Ocorrencias.class);
                    ocorrencias.add(o);
                }

                oc.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
