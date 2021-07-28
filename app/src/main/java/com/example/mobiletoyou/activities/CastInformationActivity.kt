package com.example.mobiletoyou.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mobiletoyou.*
import com.example.mobiletoyou.databinding.ActivityCastInformationBinding
import com.example.mobiletoyou.model.PersonalInformation
import com.example.mobiletoyou.network.RetrofitInitializer
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CastInformationActivity : AppCompatActivity() {
    private var personDetails: PersonalInformation? = null

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
        RetrofitInitializer().service.getPersonalInformation(personId = personId, apiKey = API_KEY)
            .enqueue(object : Callback<PersonalInformation>{
                override fun onResponse(
                    call: Call<PersonalInformation>,
                    response: Response<PersonalInformation>
                ) {
                    personDetails = response.body()
                    binding.apply {
                        Picasso.get().load(MOVIE_URL + personDetails?.profilePath).into(personPicture)
                        personName.text = personDetails?.personName
                        personBirthdayText.text = personDetails?.birthday
                        personPlaceText.text = personDetails?.placeOfBirth
                        personOccupation.text = personDetails?.career
                        biography.text = personDetails?.biography
                        popularity.text = personDetails?.popularity
                        starUnliked.setOnClickListener {
                            starUnliked.visibility = View.INVISIBLE; starLiked.visibility = View.VISIBLE
                        }
                        starLiked.setOnClickListener {
                            starLiked.visibility = View.INVISIBLE; starUnliked.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onFailure(call: Call<PersonalInformation>, t: Throwable) {
                    Toast.makeText(this@CastInformationActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }
}