package com.grela.clean.profile.addrestaurant

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.SearchManager
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.MediaStore
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.cursoradapter.widget.CursorAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.grela.clean.*
import com.grela.clean.databinding.FragmentAddRestaurantBinding
import java.util.concurrent.TimeUnit


class AddRestaurantFragment : Fragment() {
    private lateinit var binding: FragmentAddRestaurantBinding
    lateinit var token: AutocompleteSessionToken
    lateinit var request: FindAutocompletePredictionsRequest
    lateinit var restaurantModel: RestaurantModel
    lateinit var placesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRestaurantBinding.inflate(layoutInflater)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(150, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addRestaurantTopBar.setNavigationOnClickListener { findNavController().popBackStack() }
        token = AutocompleteSessionToken.newInstance()
        placesClient = Places.createClient(requireContext())
        configureSearch()
        binding.addRestaurantButton.setSingleClickListener {
            moveSearchView()
        }
        binding.editRestaurantLogoImage.setSingleClickListener {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 200)
        }

        binding.editRestaurantHeaderImage.setSingleClickListener {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 201)
        }
        binding.createRestaurantButton.setSingleClickListener {

        }

    }

    private fun configureSearch() {
        binding.addRestaurantSearchView.apply {
            val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
            val to = intArrayOf(R.id.searchSuggestionItem)
            val cursorAdapter = SimpleCursorAdapter(
                context,
                R.layout.search_item,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
            )

            suggestionsAdapter = cursorAdapter
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    val cursor =
                        MatrixCursor(
                            arrayOf(
                                BaseColumns._ID,
                                SearchManager.SUGGEST_COLUMN_TEXT_1,
                                SearchManager.SUGGEST_COLUMN_TEXT_2
                            )
                        )

                    request =
                        FindAutocompletePredictionsRequest.builder()
                            .setCountry("es")
                            .setSessionToken(token)
                            .setQuery(query)
                            .build()
                    placesClient.findAutocompletePredictions(request)
                        .addOnSuccessListener { response ->
                            Log.d("Places", response.autocompletePredictions.toString())
                            response.autocompletePredictions.forEachIndexed { index, autocompletePrediction ->
                                cursor.addRow(
                                    arrayOf(
                                        index,
                                        autocompletePrediction.getFullText(null),
                                        autocompletePrediction.placeId
                                    )
                                )
                            }
                            cursorAdapter.changeCursor(cursor)
                        }.addOnFailureListener { exception ->
                            if (exception is ApiException) {
                                val apiException = exception as ApiException
                                Log.d("Places", apiException.localizedMessage.orEmpty())
                            }
                        }
                    return true
                }
            })
            setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return false
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    hideKeyboard()
                    val cursor = suggestionsAdapter.getItem(position) as Cursor
                    val selection =
                        cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                    val placeId =
                        cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2))
                    setQuery(selection, false)

                    moveSearchView()
                    requestPlaceInfo(placeId)


                    return true
                }
            })
        }
    }

    private fun moveSearchView() {
        binding.dataGroup.visible()
        binding.addRestaurantSearchView.gone()
        binding.addRestaurantGoogleImage.gone()
        binding.addRestaurantInstruction.gone()
    }

    private fun requestPlaceInfo(placeId: String) {
        val placeFields: List<Place.Field> =
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.RATING,
                Place.Field.PHONE_NUMBER,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.PHOTO_METADATAS
            )

        val request = FetchPlaceRequest.newInstance(placeId, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener { response ->
            val place = response.place
            val metada = place.photoMetadatas
            if (metada == null || metada.isEmpty()) {
                Log.w(ContentValues.TAG, "No photo metadata.")
                return@addOnSuccessListener
            }
            requestPictures(metada)
            binding.addRestaurantName.text = place.name.orEmpty()
            binding.addRestaurantAddress.text = place.address.orEmpty()
            binding.addRestaurantPhone.text = place.phoneNumber.orEmpty()
            restaurantModel = RestaurantModel(
                place.name.orEmpty(),
                place.address.orEmpty(),
                place.latLng?.latitude?.toFloat() ?: 0f,
                place.latLng?.longitude?.toFloat() ?: 0f,
                place.rating?.toFloat() ?: 0f,
                place.phoneNumber.orEmpty()
            )
            Log.d("restaurant", restaurantModel.toString())
        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e(ContentValues.TAG, "Place not found: " + exception.localizedMessage)
                val statusCode = exception.statusCode
            }
        }
    }

    private fun requestPictures(metada: List<PhotoMetadata>) {
        val photoRequest = FetchPhotoRequest.builder(metada.first())
            .build()
        placesClient.fetchPhoto(photoRequest)
            .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                val bitmap = fetchPhotoResponse.bitmap
                binding.editRestaurantHeaderImage.setImageBitmap(bitmap)
            }
        binding.editRestaurantLogoImage.setImageResource(R.drawable.ic_baseline_add_a_photo_24)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            if (selectedImage != null) {
                val cursor: Cursor? = requireActivity().contentResolver.query(
                    selectedImage,
                    filePathColumn, null, null, null
                )
                if (cursor != null) {
                    cursor.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    val picturePath = cursor.getString(columnIndex)
                    val picture = BitmapFactory.decodeFile(
                        picturePath
                    )
                    if (requestCode == 1) {
                        binding.editRestaurantLogoImage.setImageBitmap(picture)
                    } else {
                        binding.editRestaurantHeaderImage.setImageBitmap(picture)
                    }
                    cursor.close()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && permissions.contentEquals(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, 1)
        } else if (requestCode == 201 && permissions.contentEquals(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, 2)
        }
    }
}