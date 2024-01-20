package it.polito.musaapp.Frontend

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

@Composable
fun TaskReference() {
    val storageRef = FirebaseStorage.getInstance().getReference("ReferenceTask1Arte")

    val httpsReference = storageRef.child("arte-astratta-geometrica-marmorizzata-colorata.jpg")
    
    

}