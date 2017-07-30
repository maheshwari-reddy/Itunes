package com.developer.maheshwari.itunes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.maheshwari.itunes.R;
import com.developer.maheshwari.itunes.beanClass.onSingleListItemClicked;
import com.developer.maheshwari.itunes.response.ResponseClass;
import com.developer.maheshwari.itunes.utils.ImageTrans_CircleTransform;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter
{
    Context con;
    ArrayList<ResponseClass.results> list = new ArrayList<>();
    onSingleListItemClicked OnSingleListItemClicked;

    public ListViewAdapter(Context con, ArrayList<ResponseClass.results> list, onSingleListItemClicked OnSingleListItemClicked)
    {
        this.con = con;
        this.list = list;
        this.OnSingleListItemClicked = OnSingleListItemClicked;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_single_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.artistName.setText(list.get(position).getArtistName());
        holder.price.setText(list.get(position).getTrackPrice() +" "+ list.get(position).getCurrency());
        holder.trackName.setText(list.get(position).getTrackName());
        holder.CollectionCensoredName.setText(list.get(position).getCollectionCensoredName());
        holder.genere.setText(list.get(position).getPrimaryGenreName());
        Picasso.with(con)
                .load(list.get(position).getArtworkUrl100())
                .transform(new ImageTrans_CircleTransform())
                .resize(100, 100)
                .centerCrop()
                .into(holder.thumb_img);

        holder.root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                System.out.println("ListViewAdapter--"+"root layout clicked");
                OnSingleListItemClicked.OnSingleListItemClicked(ListViewAdapter.this,list,position);
            }
        });
        return convertView;
    }

    private static class ViewHolder
    {
        TextView artistName,price,CollectionCensoredName,genere,trackName;
        ImageView thumb_img;
        LinearLayout root;

        public ViewHolder(View v)
        {
            artistName = (TextView) v.findViewById(R.id.artistName);
            artistName.setSelected(true);
            price = (TextView) v.findViewById(R.id.price);
            CollectionCensoredName = (TextView) v.findViewById(R.id.CollectionCensoredName);
            CollectionCensoredName.setSelected(true);
            genere = (TextView) v.findViewById(R.id.genere);
            genere.setSelected(true);
            trackName = (TextView) v.findViewById(R.id.trackName);
            thumb_img = (ImageView) v.findViewById(R.id.thumb_img);
            root = (LinearLayout)v.findViewById(R.id.root);
        }
    }
}
