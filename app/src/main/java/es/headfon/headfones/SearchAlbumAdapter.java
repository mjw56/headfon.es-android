package es.headfon.headfones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class SearchAlbumAdapter extends ArrayAdapter<SearchAlbumListing> {

    private Context mContext;
    private List<SearchAlbumListing> albumsList;

    public SearchAlbumAdapter(@NonNull Context context, ArrayList<SearchAlbumListing> list) {
        super(context, 0 , list);
        mContext = context;
        albumsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.search_results_album,parent,false);

        SearchAlbumListing album = albumsList.get(position);

        ImageView image = listItem.findViewById(R.id.imageView_albumCover);
        Glide.with(mContext).load(album.getAlbumCover()).into(image);

        TextView name = listItem.findViewById(R.id.textView_albumName);
        name.setText(album.getAlbumName());

        return listItem;
    }
}