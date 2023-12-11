package ru.chay.githubclient.ui.userslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.chay.githubclient.R
import ru.chay.githubclient.domain.model.User

class UserListAdapter(

): ListAdapter<User, UserDataViewHolder>(UserItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        return UserDataViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        val user = currentList[position]
        holder.onBind(user)
        holder.itemView.setOnClickListener {
            val action = UsersListFragmentDirections
                .actionUsersListFragmentToUserDetailsFragment(user)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(
        holder: UserDataViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if(payloads[0] == true) {
                holder.bindFollowers(currentList[position])
            }
        }
    }
}

class UserDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val tvUsername: TextView = itemView.findViewById(R.id.user_name)
    private val tvFullName: TextView = itemView.findViewById(R.id.user_full_name)
    private val tvFollowers: TextView = itemView.findViewById(R.id.user_followers)
    private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_user_avatar)

    fun onBind(user: User) {
        tvUsername.text = user.name
        user.fullName?.let { tvFullName.text = it }
        user.followersText?.let { tvFollowers.text = it }
        ivAvatar.load(user.avatarUrl) {
            transformations(RoundedCornersTransformation(32f))
        }
    }

    fun bindFollowers(user: User) {
        user.fullName?.let { tvFullName.text = it }
        user.followersText?.let { tvFollowers.text = it }
    }
}

class UserItemDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: User, newItem: User): Any? {
        return if (oldItem.followersText != newItem.followersText) true else null
    }
}