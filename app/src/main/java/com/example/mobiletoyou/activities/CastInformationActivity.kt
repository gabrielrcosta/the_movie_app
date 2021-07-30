package com.example.mobiletoyou.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mobiletoyou.*
import com.example.mobiletoyou.databinding.ActivityCastInformationBinding
import com.example.mobiletoyou.model.PersonalInformation
import com.example.mobiletoyou.network.MovieRepository
import com.example.mobiletoyou.network.RetrofitInitializer
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CastInformationActivity : AppCompatActivity() {
    private val repository: MovieRepository by lazy {
        MovieRepository()
    }
    private var personClicked = 0
    private lateinit var binding: ActivityCastInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCastInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personClicked = intent.getIntExtra(GET_PERSONAL_ID, 0)

        getPersonalInformation(personId = personClicked)
    }

    private fun getPersonalInformation(personId: Int) {
        repository.getPersonalInformation(personId = personId, object : MovieRepository.OnCastInformationSuccess {
            override fun onCastInformationResponseSuccess(castInformation: PersonalInformation) {
                binding.apply {
                    Picasso.get().load(MOVIE_URL + castInformation.profilePath).into(personPicture)
                    personName.text = castInformation.personName
                    personBirthdayText.text = castInformation.birthday
                    personPlaceText.text = castInformation.placeOfBirth
                    personOccupation.text = castInformation.career
                    biography.text = castInformation.biography
                    popularity.text = castInformation.popularity
                    starUnliked.setOnClickListener {
                        starUnliked.visibility = View.INVISIBLE; starLiked.visibility = View.VISIBLE
                    }
                    starLiked.setOnClickListener {
                        starLiked.visibility = View.INVISIBLE; starUnliked.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}