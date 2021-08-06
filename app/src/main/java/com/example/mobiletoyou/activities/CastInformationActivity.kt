package com.example.mobiletoyou.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mobiletoyou.Constants.GET_PERSONAL_ID
import com.example.mobiletoyou.Constants.MOVIE_URL
import com.example.mobiletoyou.databinding.ActivityCastInformationBinding
import com.example.mobiletoyou.model.PersonalInformation
import com.example.mobiletoyou.network.MovieRepository
import com.squareup.picasso.Picasso

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
        repository.getPersonalInformation(
            personId = personId,
            object : MovieRepository.OnSuccess<PersonalInformation> {
                override fun onResponseSuccess(success: PersonalInformation) {
                    setupInformationScreen(castInformation = success)
                }
            })
    }

    private fun setupInformationScreen(castInformation: PersonalInformation) {
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
}