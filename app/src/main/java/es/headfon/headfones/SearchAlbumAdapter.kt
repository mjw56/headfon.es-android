package es.headfon.headfones

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*


class SearchAlbumAdapter(private val mContext: Context, list: ArrayList<SearchAlbumListing>) : ArrayAdapter<SearchAlbumListing>(mContext, 0, list) {
    private val albumsList: List<SearchAlbumListing>

    init {
        albumsList = list
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.search_results_album, parent, false)

        val album = albumsList[position]

        val image = listItem!!.findViewById<ImageView>(R.id.imageView_albumCover)
        Glide.with(mContext).load(album.albumCover).into(image)

        val name = listItem.findViewById<TextView>(R.id.textView_albumName)
        name.text = album.albumName

        return listItem
    }
}