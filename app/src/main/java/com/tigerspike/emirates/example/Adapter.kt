package com.tigerspike.emirates.example

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tigerspike.emirates.R


class ReposAdapter(
        private var repos: List<Repo>
) : RecyclerView.Adapter<ReposViewHolder>() {

    constructor(owner: LifecycleOwner, repos: MutableLiveData<List<Repo>>) : this(emptyList()) {
        repos.observe(owner, Observer {
            this.repos = it ?: emptyList()
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder = newInstance(parent)

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) = holder.refresh(repos[position])

}

class ReposViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {

    private val title: TextView by bind(R.id.title)
    private val description: TextView by bind(R.id.description)
    private val forks: TextView by bind(R.id.forks)
    private val stars: TextView by bind(R.id.stars)

    fun refresh(repo: Repo) {
        title.text = repo.name
        description.text = repo.description
        forks.text = repo.forks_count.toString()
        stars.text = repo.stargazers_count.toString()
    }
}

private fun <V : View> ReposViewHolder.bind(@IdRes id: Int): Lazy<V> = lazy {
    itemView.findViewById<V>(id)
}

private fun newInstance(container: ViewGroup): ReposViewHolder {
    val inflater = LayoutInflater.from(container.context)
    val layout = inflater.inflate(R.layout.row_repo, container, false)
    return ReposViewHolder(layout)
}
