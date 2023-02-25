package com.project.farmingapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.farmingapp.adapter.EcommerceAdapter
import kotlinx.android.synthetic.main.fragment_ecommerce.*

class EcommViewModel : ViewModel() {
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    var ecommLiveData = MutableLiveData<List<DocumentSnapshot>>()
    var specificCategoryItems = MutableLiveData<List<DocumentSnapshot>>()
    var specificItem = MutableLiveData<DocumentSnapshot>()
    fun loadAllEcommItems(): MutableLiveData<List<DocumentSnapshot>> {

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore!!.collection("products").get()
            .addOnSuccessListener {
//                Log.d("discommode", it.documents[1].getString("title").toString())
                ecommLiveData.value = it.documents
                Log.d("discommode", it.documents.toString())

            }
            .addOnFailureListener {
                it.message?.let { it1 -> Log.d("discommode", it1) }
            }
        return ecommLiveData
    }

    fun loadSpecificTypeEcomItem(itemType: String): MutableLiveData<List<DocumentSnapshot>> {
        firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore.collection("products")
            .whereEqualTo("type", itemType)
            .get()
            .addOnSuccessListener {
                Log.d("Ecommviewmodel", it.documents[0].getString("title").toString())
                ecommLiveData.value = it.documents
                Log.d("discommode", it.documents.toString())

            }
            .addOnFailureListener {
                Log.d("discommode", it.message)
            }
        return ecommLiveData

    }

    fun getSpecificCategoryItems(itemType: String): MutableLiveData<List<DocumentSnapshot>> {
        firebaseFireStore.collection("products")
            .whereEqualTo("type", itemType)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    specificCategoryItems.value = it.result!!.documents
                    Log.d("Eco-mmViewModel", it.result!!.documents.toString())
                }
            }
            .addOnFailureListener {
                Log.e("Eco-mmViewModel", "Error Loading Specific Category Items")
            }
        return specificCategoryItems
    }

    fun getSpecificItem(itemID: String): MutableLiveData<DocumentSnapshot> {
        firebaseFireStore.collection("products")
            .document(itemID)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("Eco-mmViewModel", it.result!!.data.toString())
                    specificItem.value = it.result
                } else {
                    Log.e("Eco-mmViewModel", "Failed Getting Data")
                }
            }.addOnFailureListener {
                Log.e("Eco-mmViewModel", "Failed Getting Data")
            }
        return specificItem
    }
}