package br.com.mayki.listpad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import br.com.mayki.listpad.databinding.ActivitySobreBinding;

public class SobreActivity extends AppCompatActivity {

    ActivitySobreBinding activitySobreBinding;
    final String NOME_PAGINA = "Sobre";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySobreBinding = ActivitySobreBinding.inflate(getLayoutInflater());
        setContentView(activitySobreBinding.getRoot());

        Toolbar toolbar = activitySobreBinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(NOME_PAGINA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}