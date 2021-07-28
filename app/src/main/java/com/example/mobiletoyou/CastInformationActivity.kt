package com.example.mobiletoyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mobiletoyou.model.PersonalInformation
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CastInformationActivity : AppCompatActivity() {
    private var personDetails: PersonalInformation? = null
    private lateinit var personPicture: ImageView
    private lateinit var personName: TextView
    private lateinit var personBirthday: TextView
    private lateinit var personPlace: TextView
    private lateinit var personOccupation: TextView
    private lateinit var personBiography: TextView
    private lateinit var personPopularity: TextView
    private lateinit var starLiked: ImageView
    private lateinit var starUnliked: ImageView
    private var personClicked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_information)
        starLiked = findViewById(R.id.star_liked)
        starUnliked = findViewById(R.id.star_unliked)

        starLiked.visibility = View.INVISIBLE

        personPicture = findViewById(R.id.person_picture)
        personName = findViewById(R.id.person_name)
        personBirthday = findViewById(R.id.person_birthday_text)
        personPlace = findViewById(R.id.person_place_text)
        personOccupation = findViewById(R.id.person_occupation)
        personBiography = findViewById(R.id.biography)
        personPopularity = findViewById(R.id.popularity)

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
                    Picasso.get().load(MOVIE_URL + personDetails?.profilePath).into(personPicture)
                    personName.text = personDetails?.personName
                    personBirthday.text = personDetails?.birthday
                    personPlace.text = personDetails?.placeOfBirth
                    personOccupation.text = personDetails?.career
                    personBiography.text = personDetails?.biography
                    personPopularity.text = personDetails?.popularity
                    starUnliked.setOnClickListener {
                        starUnliked.visibility = View.INVISIBLE; starLiked.visibility = View.VISIBLE
                    }
                    starLiked.setOnClickListener {
                        starLiked.visibility = View.INVISIBLE; starUnliked.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<PersonalInformation>, t: Throwable) {
                    Toast.makeText(this@CastInformationActivity, ERROR_MSG, Toast.LENGTH_SHORT).show()
                }
            })
    }
}