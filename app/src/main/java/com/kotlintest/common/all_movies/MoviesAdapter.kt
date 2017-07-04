package com.kotlintest.common.all_movies

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.kotlintest.IMAGE_ENDPOINT
import com.kotlintest.Movie
import com.kotlintest.R
import com.kotlintest.common.all_movies.MoviesAdapter.ViewHolder
import com.squareup.picasso.Picasso

/**
 * Created by alex on 6/29/17.
 */
class MoviesAdapter(var context: Context?) : RecyclerView.Adapter<ViewHolder>() {

    var onLoadMoreListener: MoviesAdapter.OnLoadMoreListener? = null
    var onItemClickListener: MoviesAdapter.OnItemClickListener? = null
    var moviesList = ArrayList<Movie>()

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_movie_list_item, parent, false)
        return ViewHolder(view)
    }

    fun getItem(position: Int): Movie {
        return moviesList[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]
        val movieHolder = holder
        movieHolder.tvMovieName.text = movie.title
        Picasso.with(context).load(IMAGE_ENDPOINT + movie.poster_path).fit().centerCrop().into(movieHolder.imgMovie)
        holder.mainContainer.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
        if (position == moviesList.size - 1) {
            onLoadMoreListener?.onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMovie: ImageView
        val tvMovieName: TextView
        val mainContainer: RelativeLayout

        init {
            imgMovie = itemView.findViewById(R.id.imgMovie) as ImageView
            tvMovieName = itemView.findViewById(R.id.tvMovieName) as TextView
            mainContainer = itemView.findViewById(R.id.mainContainer) as RelativeLayout
        }
    }

    fun setContent(moviesList: List<Movie>?) {
        this.moviesList.clear()
        this.moviesList.addAll(moviesList as ArrayList<Movie>)
        notifyDataSetChanged()
    }

    fun setMoreContent(moviesList: List<Movie>?) {
        this.moviesList.addAll(moviesList as ArrayList<Movie>)
        notifyDataSetChanged()
    }

}