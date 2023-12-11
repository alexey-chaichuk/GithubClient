package ru.chay.githubclient.ui.userdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.chay.githubclient.R
import ru.chay.githubclient.domain.model.Repository

class ReposListAdapter(

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var repos = listOf<Repository>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoDataViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_repository, parent, false)
        )
    }

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RepoDataViewHolder -> {
                val repo = repos[position]
                holder.onBind(repo)
            }
        }
    }

    fun bindRepos(newRepos: List<Repository>) {
        repos = newRepos
        notifyDataSetChanged()
    }
}

private class RepoDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val tvRepoName: TextView = itemView.findViewById(R.id.tv_repo_name)
    private val tvRepoDesc: TextView = itemView.findViewById(R.id.tv_repo_desc)
    private val tvRepoUpdated: TextView = itemView.findViewById(R.id.tv_repo_updated)
    private val tvRepoStars: TextView = itemView.findViewById(R.id.tv_repo_stars)
    private val tvRepoForks: TextView = itemView.findViewById(R.id.tv_repo_forks)
    private val tvRepoDefaultBranch: TextView = itemView.findViewById(R.id.tv_repo_default_branch)
    private val tvRepoLanguage: TextView = itemView.findViewById(R.id.tv_repo_lang)

    fun onBind(repo: Repository) {
        tvRepoName.text = repo.name
        tvRepoDesc.text = repo.description
        tvRepoUpdated.text = repo.lastCommitDate
        tvRepoStars.text = repo.starsCount
        tvRepoForks.text = repo.forksCount
        tvRepoDefaultBranch.text = repo.defaultBranch
        tvRepoLanguage.text = repo.language
    }
}