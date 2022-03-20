package com.safaldeepsingh.musicapp.adapter

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.safaldeepsingh.musicapp.AlbumDetailActivity
import com.safaldeepsingh.musicapp.R
import com.safaldeepsingh.musicapp.db.AlbumTable
import com.safaldeepsingh.musicapp.entities.Album
import com.squareup.picasso.Picasso

class AlbumAdapter(val showContextMenu: Boolean = false,val context: Context) : RecyclerView.Adapter<AlbumAdapter.AlbumItemViewHolder>() {
    private val dataSet = mutableListOf<Album>()
    companion object{
        const val EXTRA_ALBUM = "extrasAlbum"
    }

    class AlbumItemViewHolder(private val parentAdapter: AlbumAdapter, private val containerView: View) : RecyclerView.ViewHolder(containerView) {
        var Album: Album? = null
        val albumCard: CardView = containerView.findViewById(R.id.listAlbum_cardView)
        val coverImage: ImageView = containerView.findViewById(R.id.listAlbum_albumImage)
        val title: TextView = containerView.findViewById(R.id.listAlbum_title)
        val genres: TextView = containerView.findViewById(R.id.listAlbum_genre)
        init {
            containerView.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
//                menuInflater.inflate(R.menu.context_menu, contextMenu)
                val details: MenuItem = contextMenu.add(Menu.NONE, 1, 1, "Details")
                val delete: MenuItem = contextMenu.add(Menu.NONE, 2, 2, "Delete")
                val deleteAll: MenuItem = contextMenu.add(Menu.NONE, 3, 3, "Delete All")
                val albumTable = AlbumTable(parentAdapter.context)
                details.setOnMenuItemClickListener {
                    val intent = Intent(parentAdapter.context,AlbumDetailActivity::class.java )
                    intent.putExtra(EXTRA_ALBUM,Album)
                    parentAdapter.context.startActivity(intent)
                    true
                }
                delete.setOnMenuItemClickListener {
                    val name = Album?.name
                    if(albumTable.delete(Album as Album))
                        parentAdapter.removeData(Album as Album)

                    Toast.makeText(parentAdapter.context,"${name} Removed from Favourites", Toast.LENGTH_LONG)
                    true
                }
                deleteAll.setOnMenuItemClickListener {

                    if(albumTable.deleteAll())
                        parentAdapter.setData(mutableListOf<Album>())
                    Toast.makeText(parentAdapter.context,"All Removed from Favourites", Toast.LENGTH_LONG)
                    true
                }
            }
            //Click Listeners and whatever else to manage View
            albumCard.setOnClickListener{
                val context = containerView.context
                val intent = Intent(context, AlbumDetailActivity::class.java)
                intent.putExtra(EXTRA_ALBUM,Album)
                context.startActivity(intent)
            }
        }
    }

    public fun setData(dataSet: List<Album>) {
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        notifyDataSetChanged()
    }

    public fun addData(Album: Album){
        dataSet.add(Album)
        notifyDataSetChanged()
    }

    public fun removeData(Album: Album){
        dataSet.remove(Album)
        notifyDataSetChanged()
    }

    //inflate the customView
    override fun onCreateViewHolder(parent: ViewGroup, customViewType: Int): AlbumItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_list,parent, false) //false because the recycler will add to the view hierarchy when it is time
        return AlbumItemViewHolder(this, view)
    }

    //Called by the layoutManager to replace the content(data) of the CustomView
    override fun onBindViewHolder(holder: AlbumItemViewHolder, positionInDataSet: Int) {
        val currentData = dataSet[positionInDataSet]
        holder.Album = currentData
        holder.title.text = currentData.name
        holder.genres.text = currentData.genres.joinToString(",")
        if(currentData.coverImage!=null && currentData.coverImage!="")
            Picasso.get().load(currentData.coverImage).into(holder.coverImage)
        else
            holder.coverImage.setImageResource(R.drawable.placeholder)
    }

    override fun getItemCount() = dataSet.size

}











