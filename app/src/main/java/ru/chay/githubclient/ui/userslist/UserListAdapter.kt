package ru.chay.githubclient.ui.userslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.chay.githubclient.R
import ru.chay.githubclient.domain.model.User

class UserListAdapter(

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var users = listOf<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserDataViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is UserDataViewHolder -> {
                val user = users[position]
                holder.onBind(user)
                holder.itemView.setOnClickListener {
                    val action = UsersListFragmentDirections
                        .actionUsersListFragmentToUserDetailsFragment(user)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    fun bindUsers(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}

private class UserDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
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
}