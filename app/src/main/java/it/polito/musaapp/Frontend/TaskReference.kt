package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import it.polito.musaapp.Backend.MusaViewModel


fun GetReferenceTask(vm: MusaViewModel){
    val listUrl: MutableList<String> = mutableListOf()
    val myRef= FirebaseDatabase.getInstance().getReference("Reference").child(vm.category.value!!)
        .child(vm.level.value!!).child("Task${vm.taskCounter.value}")
    Log.d("REFERENCE", "Task${vm.taskCounter.value}")
    myRef.get().addOnSuccessListener {
        Log.d("REFERENCE", "${it.value}")
        for(i in it.children){
            Log.d("Single REFERENCE", "${i.value!!}")
            listUrl.add(i.value!!.toString())
        }
        Log.d("REFERENCE1", "$listUrl")
        vm.setReferenceListUrl(listUrl)
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
}
@Composable
fun TaskReference(navController: NavController, vm:MusaViewModel) {
    //val storageRef = FirebaseStorage.getInstance().getReference("ReferenceTask1Arte")
    val list by vm.referenceListUrl.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Text(
                text = "Top bar con back arrow e logo",
                style = MaterialTheme.typography.headlineMedium,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        )
        {
            Row {
                Text(
                    text = "REFERENCES",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            /*TODO() Aggiungere dropdown per filtrare le reference?*/

            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
            ) {
                list?.forEachIndexed { index, imageUrl ->
                    item(index) {
                        ImageWithHeart(imageUrl = imageUrl)
                    }
                }
            }
        }
    }


}
@Composable
fun ImageWithHeart(imageUrl: String) {
    var isLiked by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    )
    {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
            )
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable {
                    isLiked = !isLiked
                }
                .align(Alignment.TopEnd)
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}




