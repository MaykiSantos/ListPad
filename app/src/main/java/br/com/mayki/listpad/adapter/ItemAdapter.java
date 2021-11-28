package br.com.mayki.listpad.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.com.mayki.listpad.R;
import br.com.mayki.listpad.dataBase.RoomListPad;
import br.com.mayki.listpad.dataBase.dao.ItemDAO;
import br.com.mayki.listpad.dataBase.entity.Item;
import br.com.mayki.listpad.databinding.LayoutItemAdapterBinding;
import br.com.mayki.listpad.ui.Constantes;
import br.com.mayki.listpad.ui.DetalheListaActivity;
import br.com.mayki.listpad.ui.NovoItemActivity;

public class ItemAdapter extends ArrayAdapter<Item> {

    LayoutItemAdapterBinding layoutItemAdapterBinding;
    ItemDAO itemDTO;
    Integer lista;

    CheckBox checkBox;
    ImageButton btnExcluir;
    ImageButton btnEditar;


    private Context context;
    private List<Item> objects;


    public ItemAdapter(@NonNull Context context, @NonNull List<Item> objects) {
        super(context, R.layout.layout_item_adapter, objects);
        this.context = context;
        this.objects = objects;

        lista = objects.size() >0 ? objects.get(0).getIdLista(): null;
        itemDTO = RoomListPad.getInstance(context).getItemDAO();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View viewLista = null;

        if(convertView != null){ // caso view já exista
            viewLista = convertView;
        }else{ //view precissa ser inflada
            layoutItemAdapterBinding = LayoutItemAdapterBinding.inflate(
                    ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)),
                    parent,
                    false
            );
            viewLista = layoutItemAdapterBinding.getRoot();
        }
        
        carragaCampos();
        defineEventoDeExclusaoDeItem(position, parent);
        defineEventoParaEditarItem(position);
        marcaDesmarcaItem(position);

        Item itemLista = objects.get(position);
        checkBox.setText(itemLista.getDescricao());
        checkBox.setChecked(itemLista.getFeito());

        return viewLista;

    }

    private void marcaDesmarcaItem(int position) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Item itemClicado = ItemAdapter.this.getItem(position);
                itemClicado.setFeito(!itemClicado.getFeito());
                Log.i("LOG-APP", "onClick: "+ itemClicado.getDescricao());
                Log.i("LOG-APP", "onClick: "+ itemClicado.getFeito());

                itemDTO.atualizar(itemClicado);
            }
        });
    }



    private void defineEventoParaEditarItem(int position) {
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NovoItemActivity.class);
                intent.putExtra(Constantes.ITEM, ItemAdapter.this.getItem(position));
                //context.startActivity(intent);
                getContext().startActivity(intent);
            }
        });
    }

    private void defineEventoDeExclusaoDeItem(int position, ViewGroup parent) {
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDTO.deletar(ItemAdapter.this.getItem(position));
                Toast.makeText(context, "item excluido"+ getItem(position).getDescricao(), Toast.LENGTH_SHORT).show();
                atualizaAdapter(parent);

            }
        });
    }



    public void atualizaAdapter(ViewGroup parent) {
        Log.i("LOG-ADAPTER", "atualizaAdapter");
        this.clear();
        this.addAll(itemDTO.buscarPorLista(lista));
        this.notifyDataSetChanged();
        ((ListView)parent).setAdapter(this);
    }

    private void carragaCampos() {
        this.btnExcluir = layoutItemAdapterBinding.layoutItemAdapterBtExcluir;
        this.btnEditar = layoutItemAdapterBinding.layoutItemAdapterBtEditar;
        this.checkBox = layoutItemAdapterBinding.layoutItemAdapterCheckBox;
    }
}
