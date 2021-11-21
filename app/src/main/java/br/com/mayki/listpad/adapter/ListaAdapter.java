package br.com.mayki.listpad.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.com.mayki.listpad.R;
import br.com.mayki.listpad.dataBase.dao.relacionamentos.ListaCategoria;
import br.com.mayki.listpad.dataBase.entity.Lista;
import br.com.mayki.listpad.databinding.LayoutListaAdapterBinding;


public class ListaAdapter extends ArrayAdapter<ListaCategoria> {

    private Context context;
    private List<ListaCategoria> objects;

    public ListaAdapter(@NonNull Context context, @NonNull List<ListaCategoria> objects) {
        super(context, R.layout.layout_lista_adapter, objects);

        this.context = context;
        this.objects = objects;
        Log.i("Adapter", "ListaAdapter: Criando adapter com construtor completo");
    }

    public ListaAdapter(@NonNull Context context) {
        super(context, R.layout.layout_lista_adapter);
        this.context = context;
        Log.i("Adapter", "ListaAdapter: Criando adapter com construtor simples");
    }


    @Override
    public int getCount() {
        //permite que o adapter saiba a quantidade de elementos da lista
        return objects.size();
    }

    @Nullable
    @Override
    public ListaCategoria getItem(int position) {
        //permite que o adapter saiba qual é o elemento em uma determinada possição
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        //permite que o adapter saiba o id de um determinado elemento
        return objects.get(position).lista.getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Esse método é chamado em duas situaçãoes. A primeira é quando o a view aindo não existe e a segunda é quando a view deve ser reciclada.
        // Desta forma é nescessario fazer uma verificação para definir se será criada uma nova view ou se um view já existente será reciclada.

        View viewLista = null;

        if(convertView != null){ // caso view já exista
            viewLista = convertView;
        }else{ //view precissa ser inflada
            LayoutListaAdapterBinding layoutListaAdapterBinding = LayoutListaAdapterBinding.inflate(
                    ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)),
                    parent,
                    false
            );
            viewLista = layoutListaAdapterBinding.getRoot();
        }

        ListaCategoria itemLista = objects.get(position);
        ((TextView)viewLista.findViewById(R.id.layout_lista_tv_titulo)).setText(itemLista.lista.getNome());
        ((TextView)viewLista.findViewById(R.id.layout_lista_tv_categoria)).setText(itemLista.categoria.getDescricao());
        ((TextView)viewLista.findViewById(R.id.layout_lista_tv_descricao)).setText(itemLista.lista.getDescricao());
        switch (itemLista.lista.getUrgencia()){
            case URGENTE:
                ((ImageView)viewLista.findViewById(R.id.layout_lista_iv_urgencia)).setImageResource(R.drawable.ic_bola_vermelha);
                break;
            case MODERADO:
                ((ImageView)viewLista.findViewById(R.id.layout_lista_iv_urgencia)).setImageResource(R.drawable.ic_bola_amarela);
                break;
            default:
                ((ImageView)viewLista.findViewById(R.id.layout_lista_iv_urgencia)).setImageResource(R.drawable.ic_bola_verde);
        }



        return viewLista;
    }
}





